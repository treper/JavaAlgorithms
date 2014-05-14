package ataosky.example.concurrency;

public class LockSummary_SubMain {
	public static void main(String args[]) {
		LockSummary_SubMain tr = new LockSummary_SubMain();
		tr.test();
	}

	private void test() {
		final Business business = new Business();
		new Thread() {
			public void run() {
				for (int i = 1; i <= 50; i++)
					business.sub(i);
			}
		}.start();

		/*new Thread() {
			public void run() {
				for (int i = 1; i <= 50; i++)
					business.main(i);
			}
		}.start();
		*/
		
		for (int i = 1; i <= 50; i++)
			business.main(i);
	}
}

class Business {
	private boolean isShouldTrue = true;

	public synchronized void sub(int seq) {
		if (!isShouldTrue) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 10; i++)
			System.out.println("seq " + seq + " sub loop  " + i);
		isShouldTrue = false;
		this.notify();
	}

	public synchronized void main(int seq) {
		if (isShouldTrue) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 20; i++) {
			System.out.println("seq " + seq + " main loop " + i);
		}
		isShouldTrue = true;
		this.notify();
	}
}
