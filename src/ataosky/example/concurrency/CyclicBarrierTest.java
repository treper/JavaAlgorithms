package ataosky.example.concurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/*reference:http://my.oschina.net/lifany/blog/207995
**/
public class CyclicBarrierTest {
	static AtomicInteger count=new AtomicInteger(1);
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		//CyclicBarrier构造函数第二个为释放后运行的Runnable
		final  CyclicBarrier cb = new CyclicBarrier(3,new Runnable() {
			public void run() {
			  System.out.println(Thread.currentThread().getName()+"第"+count.getAndIncrement()+"个轮回");
			}
		});
		//构造函数 CyclicBarrier(int parties, Runnable barrierAction) 
		//barrierAction表示“parties个线程到达barrier时，会执行的动作”。
		
		//构造方法里的数字标识有几个线程到达集合地点开始进行下一步工作
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						cb.await();
						
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						cb.await();	
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						cb.await();						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
			
		}
		service.shutdown();
	}
	
	
	
}
