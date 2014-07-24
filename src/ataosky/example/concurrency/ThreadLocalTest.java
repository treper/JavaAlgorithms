package ataosky.example.concurrency;

import java.util.Random;

/**
 * Created by tangning on 14-7-24.
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " set x to " + data);
                    x.set(data);

                    new A().get();
                    new B().get();
                }
            }).start();
        }


    }

    static class A {
        public void get() {
            System.out.println(Thread.currentThread().getName() + " "+x.get());
        }
    }

    static class B {
        public void get() {
            System.out.println(Thread.currentThread().getName() + " "+x.get());
        }
    }
}
