package ru.ncedu.java.tasks;

import java.util.*;
import java.lang.reflect.*;

public class ReflectionsImpl implements Reflections {
    @Override
    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        if (object == null || fieldName == null) {
            throw new NullPointerException();
        }
        try {
            Class c = object.getClass();
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(object);
        }
        catch (IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public Set<String> getProtectedMethodNames(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        
        Set<Method> methods = new HashSet<>();
        Collections.addAll(methods, clazz.getDeclaredMethods());

        Set<String> protectedMethods = new HashSet<>();
        for (Method m : methods) {
            if (Modifier.isProtected(m.getModifiers())) {
                protectedMethods.add(m.getName());
            }
        }
        return protectedMethods;
    }

    @Override
    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        
        Set<Method> allMethods = new HashSet<>();
        Collections.addAll(allMethods, clazz.getDeclaredMethods());

        for (Class c : getExtendsHierarchy(clazz)) {
            Collections.addAll(allMethods, c.getDeclaredMethods());
        }

        return allMethods;
    }

    @Override
    public List<Class> getExtendsHierarchy(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        
        List<Class> superClasses = new LinkedList<>();
        
        Class superClass = clazz.getSuperclass();
        while (superClass != null) {
            superClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }
        
        return superClasses;
    }

    @Override
    public Set<Class> getImplementedInterfaces(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        Set<Class> interfaces = new HashSet<>();
        Collections.addAll(interfaces, clazz.getInterfaces());
        return interfaces;
    }

    @Override
    public List<Class> getThrownExceptions(Method method) {
        if (method == null) {
            throw new NullPointerException();
        }
        List<Class> exceptions = new LinkedList<>();
        Collections.addAll(exceptions, method.getExceptionTypes());
        return exceptions;
    }

    @Override
    public String getFooFunctionResultForDefaultConstructedClass() {
        try {         
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            
            Method foo = SecretClass.class.getDeclaredMethod("foo");
            foo.setAccessible(true);
            
            SecretClass secretObject = constructor.newInstance();
            return (String)foo.invoke(secretObject);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer... integers) {
        try {         
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            
            Method foo = SecretClass.class.getDeclaredMethod("foo", String.class, Integer[].class);
            foo.setAccessible(true);
            
            SecretClass secretObject = constructor.newInstance(constructorParameter);
            return (String)foo.invoke(secretObject, string, integers);
        }
        catch (Exception e) {
            return null;
        }
    }
}
