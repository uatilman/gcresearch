package ru.tilman;

/**
 * @author https://otus.ru/
 */
public interface BenchmarkMBean {
    int getSize();

    void setSize(int size);

    boolean isLoop();

    void setLoop(boolean loop);
}
