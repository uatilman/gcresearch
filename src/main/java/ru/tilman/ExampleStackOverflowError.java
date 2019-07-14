package ru.tilman;

public class ExampleStackOverflowError {

    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m";

    private int counter = 0;

    public void run() {
        try {
            testOLocalVariables();
        } catch (StackOverflowError e0) {
            System.out.printf("method testOLocalVariables() overflowed the stack in %s%d%s iterations\n", COLOR_RED, counter, COLOR_RESET);
            counter = 0;
            try {
                test1OLocalVariables();
            } catch (StackOverflowError e10) {
                System.out.printf("method test1OLocalVariables() overflowed the stack in %s%d%s iterations\n", COLOR_RED, counter, COLOR_RESET);
            }
        }
    }

    public void testOLocalVariables() {
        counter++;
        testOLocalVariables();
    }

    public void test1OLocalVariables() {
        long l0 = 0;
        long l1 = 0;
        long l2 = 0;
        long l3 = 0;
        long l4 = 0;
        long l5 = 0;
        long l6 = 0;
        long l7 = 0;
        long l8 = 0;
        long l9 = 0;
        counter++;
        test1OLocalVariables();
    }

}
