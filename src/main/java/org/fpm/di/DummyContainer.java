package org.fpm.di;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class DummyContainer implements Container {
    List<Class<?>> prototypeList;
    Map<Class<?>, Class<?>> dependenciesMap;
    Map<Class<?>, Object> singletonMap;
    public DummyContainer(DummyBinder dummyBinder) {
        Map<String, Object> data = dummyBinder.getData();
        prototypeList = (List<Class<?>>) data.get("prototypeList");
        dependenciesMap = (Map<Class<?>, Class<?>>) data.get("dependenciesMap");
        singletonMap = (Map<Class<?>, Object>) data.get("singletonMap");
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (prototypeList.contains(clazz)){
            if (check(clazz)!= null) return check(clazz);
            return Creater.createInstance(clazz);
        }
        if (dependenciesMap.containsKey(clazz))
            return (T) getComponent(dependenciesMap.get(clazz));
        if (singletonMap.containsKey(clazz))
            return (T) singletonMap.get(clazz);
        return null;
    }


    public <T> T check(Class<T> clazz){
        for (Constructor<?> constructor: clazz.getConstructors()){
            if (constructor.isAnnotationPresent(Inject.class)){
                Object[] forReturn = new Object[constructor.getParameterCount()];
                for (int i = 0; i < constructor.getParameterCount(); i++){
                    forReturn[i] = getComponent(constructor.getParameterTypes()[i]);
                }
                try {
                    return (T) constructor.newInstance(forReturn);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
