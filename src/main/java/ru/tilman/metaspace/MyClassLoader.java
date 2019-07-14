package ru.tilman.metaspace;

/**
 * @author http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html
 */
public class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
        super();
    }

    public Class<?> _defineClass(String name, byte[] byteCodes) {
        return super.defineClass(name, byteCodes, 0, byteCodes.length);
    }
}