package ru.tilman;

/**
 * @author https://otus.ru/
 */

public class Benchmark implements BenchmarkMBean {
    private volatile int size = 0;
    private volatile boolean isLoop = true;

    void run() {
        System.out.println("Starting the loop");

        while (isLoop) {
            int local = size;
            Object[] arr = new Object[local];
            System.out.printf("Array  of size %d created ", local);
            try {
                for (int i = 0; i < local; i++) {
                    arr[i] = new String(new char[0]);
                }
                System.out.printf("... created %d objects \n", local);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }


        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }
}
