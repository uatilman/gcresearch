package ru.tilman;

import ru.tilman.metaspace.ExampleMetaspaceOutOfMemoryError;

import javax.management.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * В настоящей консольной утилите собраны примеры которые при прочтении статей авторов, приведенных ниже,
 * позволяют лучше разобаться в работе JVM с памятью и в механизмах сбора мусора.
 * <p>
 * Примеры в т.ч. заимствованы с:
 *
 * @author https://redstack.wordpress.com/2011/01/06/visualising-garbage-collection-in-the-jvm/ - в т.ч. данная статья
 * навела на идею консольной утилиты для изучения работы с памятью. Руссоязычная версия статьи https://habr.com/ru/post/112676/
 * @author Курс "Разработчик Java" на https://otus.ru/
 * @author https://www.blogger.com/profile/17934541017925930523,
 * http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html,
 * Golovach Courses: https://www.youtube.com/channel/UCuIctN7x71qam9K_ZxS1W2A
 */
// TODO: 04.07.19 добавить на гит без комментариев в pom.xml, добавить запуск через параметры VM в pom.xml, добавить readme.md
public class Main {

    private static List objects = new ArrayList();
    private static boolean cont = true;
    private static String input;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        System.out.println("Pid: " + ManagementFactory.getRuntimeMXBean().getName());

        System.out.println("Welcome to Memory Tool!");

        while (cont) {
            System.out.println(
                    "\n\nI have " + objects.size() + " objects in use, about " +
                            (objects.size() * 10) + " MB." +
                            "\nWhat would you like me to do?\n" +
                            "1. Create some objects\n" +
                            "2. Remove some objects\n" +
                            "3. Started created objects loop (" +
                            "from MBeans:\n" +
                            "\tset 'loop' variable as true to break;\n" +
                            "\tdouble variable 'size' to OutOfMemoryError: GC overhead limit exceeded).\n" +
                            "4. Fast OutOfMemoryError example: Requested array size exceeds VM limit\n" +
                            "5. StackOverflowError example (stack size) \n" +
                            "6. MetaspaceOutOfMemoryError example\n" +
                            "0. Quit");
            input = in.readLine();
            if ((input != null) && (input.length() >= 1)) {
                if (input.startsWith("0")) cont = false;
                if (input.startsWith("1")) createObjects();
                if (input.startsWith("2")) removeObjects();
                if (input.startsWith("3")) createBenchmark();
                if (input.startsWith("4")) createBigArray();
                if (input.startsWith("5")) new ExampleStackOverflowError().run();
                if (input.startsWith("6"))
                    new ExampleMetaspaceOutOfMemoryError().run(ExampleMetaspaceOutOfMemoryError.class);
            }
        }

        System.out.println("Bye!");

    }

    /**
     * @author https://www.youtube.com/watch?v=AxL5LgoQyNs&t
     */
    private static void createBigArray() {
        try {
            Long[] l = new Long[Integer.MAX_VALUE];
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * @author https://redstack.wordpress.com/2011/01/06/visualising-garbage-collection-in-the-jvm/
     */
    private static void createObjects() {
        System.out.println("Creating objects...");
        for (int i = 0; i < 2; i++) {
            objects.add(new byte[10 * 1024 * 1024]);
        }
    }

    /**
     * @author https://redstack.wordpress.com/2011/01/06/visualising-garbage-collection-in-the-jvm/
     */
    private static void removeObjects() {
        System.out.println("Removing objects...");
        int start = objects.size() - 1;
        int end = start - 2;
        for (int i = start; ((i >= 0) && (i > end)); i--) {
            objects.remove(i);
        }
    }

    /**
     * @author https://otus.ru/
     *
     */
    private static void createBenchmark() throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        int size = 5 * 1000 * 1000;

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("ru.tilman:type=ru.tilman.Benchmark");
        Benchmark benchmark = new Benchmark();
        server.registerMBean(benchmark, objectName);

        benchmark.setSize(size);
        benchmark.run();
    }
}
