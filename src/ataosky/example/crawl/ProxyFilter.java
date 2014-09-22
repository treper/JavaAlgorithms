package ataosky.example.crawl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created by tangning on 14-8-7.
 */
public class ProxyFilter implements Runnable {
    public static String TARGET_SITE = "http://www.tudou.com";
    public static final Log log = LogFactory.getLog(ProxyFilter.class);
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private Connection conn = null;
    //    private LinkedBlockingQueue<HttpProxy> proxyList = new LinkedBlockingQueue<HttpProxy>();
    private List<HttpProxy> proxyList = new ArrayList<HttpProxy>();


    public ProxyFilter() {

        InputStream is = ProxyFilter.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        try {
            this.conn = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //load all proxies
        loadProxies();
    }

    public void loadProxies() {
        String sql = "select id,proxy from proxyplugin";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = this.conn.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String httpProxy = rs.getString(2);
                String host = httpProxy.split(":")[0];
                Integer port = Integer.parseInt(httpProxy.split(":")[1]);
                proxyList.add(new HttpProxy(id, host, port));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbclose(conn, statement, rs);
        }
    }

//    public <T> T get(Class<T> clazz, String sql, Objects... args) {
//        return null;
//    }

    public void parallelVerifyProxy() {


        for (HttpProxy httpProxy : proxyList) {
            pool.submit(new ProxyVerifier(httpProxy));
        }

    }

    public static void dbclose(Connection conn, PreparedStatement statement, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (null != rs) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class HttpProxy {
        private Integer id;
        private String host;
        private Integer port;

        HttpProxy(Integer id, String host, Integer port) {
            this.id = id;
            this.host = host;
            this.port = port;
        }

        public Integer getId() {
            return id;
        }

        public String getHost() {
            return host;
        }

        public Integer getPort() {
            return port;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    public static class ProxyVerifier implements Runnable {

        private HttpProxy httpProxy;

        public ProxyVerifier(HttpProxy httpProxy) {
            this.httpProxy = httpProxy;
        }

        @Override
        public void run() {

            final WebClient webClient = new WebClient(BrowserVersion.CHROME, httpProxy.getHost(), httpProxy.getPort());
            HtmlPage page = null;
            try {
                page = webClient.getPage(TARGET_SITE);
            } catch (IOException e) {

            }
            if (page != null) {
                System.out.println(page.asText());
            }
            webClient.closeAllWindows();
        }
    }


    @Override
    public void run() {
        parallelVerifyProxy();
        log.info("finished verifying all proxies parallelly");
    }

}
