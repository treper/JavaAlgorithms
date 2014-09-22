package ataosky.example.concurrency;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tangning on 14-8-1.
 */
public class ConcurrentMapPerformance {

    private static final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<String, Integer>();
    private static final ConcurrentSkipListMap<String, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<String, Integer>();

    private static CountDownLatch done1 = new CountDownLatch(4);
    private static CountDownLatch done2 = new CountDownLatch(4);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 50000; j++) {
                        String key = UUID.randomUUID().toString();
                        Integer value = new Random().nextInt();
                        concurrentHashMap.put(key, value);
                    }
                    done1.countDown();
                    System.out.println(Thread.currentThread().getName()+" done");


                }
            }).start();

        }
        try {
            done1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("cocurrentHashMap cost time:"+(endTime-startTime ));
        startTime = System.currentTimeMillis();

        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 50000; j++) {
                        String key = UUID.randomUUID().toString();
                        Integer value = new Random().nextInt();
                        concurrentSkipListMap.put(key, value);
                    }
                    done2.countDown();
                    System.out.println(Thread.currentThread().getName()+" done");
                }
            }).start();

        }
        try {
            done2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        System.out.println("cocurrentSkipListMap cost time:"+(endTime-startTime ));
    }
}
