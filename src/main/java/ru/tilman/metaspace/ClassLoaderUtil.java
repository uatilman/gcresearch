package ru.tilman.metaspace;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html
 */
public class ClassLoaderUtil {

    public static byte[] loadByteCode(Class loader, String className)
            throws IOException {
        String fileName = "/"
                + className.replaceAll("\\.", "/") + ".class";
        InputStream is = loader.getResourceAsStream(fileName);
        ByteArrayOutputStream buffer
                = new ByteArrayOutputStream(4096);
        byte[] buff = new byte[1024];
        int i;
        while ((i = is.read(buff)) >= 0) {
            buffer.write(buff, 0, i);
        }

        return buffer.toByteArray();
    }
}