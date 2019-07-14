package ru.tilman.metaspace;

/**
 * Пример построен на основе источника:
 *
 * @author http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html
 * и соответствующего видео https://www.youtube.com/watch?v=Mv11WDpLupA
 * <p>
 * В отличии от примера с переполением Permanent Generetion,
 * для переполнения Metaspace необходио запускать с параметром -XX:MaxMetaspaceSize,
 * иначе Metaspace будет расширяться динамически и съест всю доступную память
 */
public class ExampleMetaspaceOutOfMemoryError {

    public void run(Class clazz) throws Exception {
        try {
            byte[] buffer = ClassLoaderUtil.loadByteCode(clazz, clazz.getName());

            MyClassLoader loader = new MyClassLoader();
            for (long index = 0; index < Long.MAX_VALUE; index++) {
                String newClassName =
                        "_" + String.format("%0"
                                + (clazz.getSimpleName().length() - 1) + "d", index);
                byte[] newClassData = new String(buffer, "latin1")
                        .replaceAll(clazz.getSimpleName(), newClassName)
                        .getBytes("latin1");
//                loader = new MyClassLoader();
                loader._defineClass(
                        clazz.getName()
                                .replace(clazz.getSimpleName(), newClassName),
                        newClassData);
            }
        } catch (OutOfMemoryError e) {
            System.out.println(e.getMessage());
        }

    }
}