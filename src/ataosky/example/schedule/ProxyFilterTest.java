package ataosky.example.schedule;



import junit.framework.TestCase;
import org.junit.Test;

public class ProxyFilterTest extends TestCase {



    @Test
    public void testParallelVerifyProxy() throws Exception {
        ProxyFilter proxyFilter = new ProxyFilter();
        proxyFilter.parallelVerifyProxy();

    }

}