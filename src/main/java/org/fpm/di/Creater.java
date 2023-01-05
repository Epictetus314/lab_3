package org.fpm.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Creater {
    public static <T> T createInstance(Class<T> clazz) {
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
