package ataosky.practice.concurrency;
 

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TeamGroup implements Runnable {

	private final CyclicBarrier barrier;

	private int groupNumber;

	/**
	 * @param barrier
	 * @param groupNumber
	 */
	public TeamGroup(CyclicBarrier barrier, int groupNumber) {
		this.barrier = barrier;
		this.groupNumber = groupNumber;
	}

	public void run() {

		try {
			print();
			barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void print() {
		System.out.println(String.format("第%d组完成该地景点浏览，并回到集合点", groupNumber));
	}

}