package ataosky.example.schedule;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tangning on 14-7-24.
 */
public class BomberTest {

    public static int count = 0;

    private static class Bomber extends TimerTask {
        @Override
        public void run() {
            count = (count + 1) % 2;
            System.out.println("bombing...");
            new Timer().schedule(new Bomber(), 2000 * (1 + count));
        }
    }

    public static void main(String[] args) {

        new Timer().schedule(new Bomber(), 2000);
        while(true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new Date().getSeconds());
        }
    }
}
