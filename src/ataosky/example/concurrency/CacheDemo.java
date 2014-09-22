package ataosky.example.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by tangning on 14-7-29.
 */
public class CacheDemo {
    private Map<String, Object> map = new HashMap<String, Object>();
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public Object getData(String key) {
        Object value = null;
        rwl.readLock().lock();//获取读锁,其他线程只能读不能写
        try {
            value = map.get(key);
            if (value == null)//如果缓存中没有,则从数据库中获取
            {
                rwl.readLock().unlock();//释放读锁
                rwl.writeLock().lock();//上写锁，其他线程不可读写
                try {
                    if (value == null)//如果没有其他线程改动过
                    {
                        value = "aaa";//queryDB(key) implementation;
                    }
                } finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();//重新获取读锁,其他线程只能读不能写,系统性能提高
            }

        } finally {
            rwl.readLock().unlock();
        }
        return value;

    }

    /**
     * java 1.6 API原始缓存系统
     */
//    class CachedData {
//        Object data;
//        volatile boolean cacheValid;
//        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
//
//        void processCachedData() {
//            rwl.readLock().lock();
//            if (!cacheValid) {
//// Must release read lock before acquiring write lock
//                rwl.readLock().unlock();
//                rwl.writeLock().lock();
//// Recheck state because another thread might have acquired
//// write lock and changed state before we did.
//                if (!cacheValid) {
//                    data =...
//                    cacheValid = true;
//                }
//// Downgrade by acquiring read lock before releasing write lock
//                rwl.readLock().lock();
//                rwl.writeLock().unlock(); // Unlock write, still hold read
//            }
//            use(data);
//            rwl.readLock().unlock();
//        }
//    }

    public static void main(String[] args) {

    }

}
