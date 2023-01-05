package org.fpm.di;

import javax.inject.Singleton;
import java.util.*;

import static org.fpm.di.Creater.createInstance;

public class DummyBinder implements Binder {
    List<Class<?>> prototypeList = new ArrayList<>();
    Map<Class<?>, Class<?>> dependenciesMap = new HashMap<>();
    Map<Class<?>, Object> singletonMap = new HashMap<>();


    public DummyBinder(Configuration configuration){
        configuration.configure(this);
    }
    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            singletonMap.put(clazz, createInstance(clazz));
        } else {
            prototypeList.add(clazz);
        }
    }


    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        dependenciesMap.put(clazz,implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        singletonMap.put(clazz,instance);
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("prototypeList",prototypeList);
        data.put("dependenciesMap",dependenciesMap);
        data.put("singletonMap", singletonMap);
        return data;
    }
}
