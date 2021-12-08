package org.wikiRelationsHeadMaster.core;

public class test extends Thread {

    public void run(int num) throws InterruptedException {

    }
    public static void main(String[] args) throws InterruptedException {
        int num = 0;

        test Test = new test();
        Test.start(num);

        num = 1;
    }

    private void start(int num) throws InterruptedException {
        while (true) {
            System.out.println(num);
            Thread.sleep(4000);
    }}


}
