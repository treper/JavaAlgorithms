package ataosky.practice.concurrency;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSummary_Number {

	private Lock lock = new ReentrantLock();

	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	private Condition c3 = lock.newCondition();

	private Map<Integer, Condition> condtionContext = new HashMap<Integer, Condition>();

	public LockSummary_Number() {
		condtionContext.put(Integer.valueOf(0), c1);
		condtionContext.put(Integer.valueOf(1), c2);
		condtionContext.put(Integer.valueOf(2), c3);
	}

	private int count = 0;

	public void print(int id) {

		lock.lock();
		try {
			while (count * 5 < 75) {
				int curID = calcID();// 计算当前应该执行打印的线程
				if (id == curID) {
					System.out.print(Thread.currentThread()+": ");
					for (int i = 1; i <= 5; i++) {
						System.out.print((count * 5 + i) + ",");
					}
					System.out.println();
					count++;
					int nextID = calcID();
					Condition nextConditon = condtionContext.get(Integer
							.valueOf(nextID));
					// 打印完成后，通知下一个线程开始打印
					nextConditon.signal();
				} else {
					Condition condition = condtionContext.get(Integer
							.valueOf(id));
					// 抢了打印顺序，需要等待自己的打印顺序
					condition.await();
				}
			}
			//完成之后，通知线程结束
			for (Condition c : condtionContext.values())
			{
				c.signal();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private int calcID() {
		return count % 3;
	}

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(3);
		final CountDownLatch latch = new CountDownLatch(1);
		final LockSummary_Number printer = new LockSummary_Number();
		for (int i = 0; i < 3; i++) {
			final int id = i;
			executor.submit(new Runnable() {

				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					printer.print(id);
				}
			});
		}
		System.out.println("三个任务开始顺序打印数字。。。。。。");
		latch.countDown();
		executor.shutdown();
		// for (int i = )
		// System.out.println();
	}
}


