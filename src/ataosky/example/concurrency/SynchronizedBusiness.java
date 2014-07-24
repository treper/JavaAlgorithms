package ataosky.example.concurrency;

/**
 * Created by tangning on 14-7-24.
 */
public class SynchronizedBusiness {
    public static void main(String[] args) {
        final Bussiness  bs = new Bussiness();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++)
                {
                    bs.sub(i);
                }
            }
        }).start();

        for(int i=0;i<5;i++)
        {
            bs.main(i);
        }

    }

    public static class Bussiness {
        private boolean flag = true;

        public synchronized void sub(int i) {
            while (!flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < 10; j++) {
                System.out.println("sub loop " + i + " " + j);
            }

            flag = false;
            this.notify();
        }

        public synchronized void main(int i) {
            while (flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < 10; j++) {
                System.out.println("main loop " + i + " " + j);
            }

            flag = true;
            this.notify();
        }
    }
}
