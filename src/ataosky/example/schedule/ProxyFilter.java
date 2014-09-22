package ataosky.example.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;

import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tangning on 14-8-7.
 */
public class ProxyFilter {
    private static final Log log = LogFactory.getLog(ProxyFilter.class);
    private static String TARGET_SITE = "http://www.tudou.com";
    private static String TEST_STR = "http://www.tudou.com/my/program/publish.html";
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private LinkedBlockingQueue<HttpProxy> proxyList = new LinkedBlockingQueue<HttpProxy>();
    private Set<String> cities = new HashSet<String>();
    private Connection conn = null;
    private AtomicInteger tested = new AtomicInteger(0);
    private AtomicInteger terminated = new AtomicInteger(0);


    public ProxyFilter() {

        InputStream is = ProxyFilter.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            conn = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (null != conn) {
            log.info("success get db connection");
        }

        loadCities();

    }


    public void loadCities() {
        if (null != conn) {
            String sql = "select city from cities";
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = conn.prepareStatement(sql);
                rs = statement.executeQuery();
                int count = 0;
                while (rs.next()) {
                    count++;
                    cities.add(rs.getString(1));
                }
                log.info(count + " cities loaded");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.warn("no cities loaded");
        }
    }

    public void loadProxies() {
        proxyList.clear();
        String sql = "select id,proxy from http_proxy";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String httpProxy = rs.getString(2);
                String host = httpProxy.split(":")[0];
//                String host = "http://" + httpProxy.split(":")[0];
                Integer port = Integer.parseInt(httpProxy.split(":")[1]);
                try {
//                    proxyList.offer(new HttpProxy(id, host, port), 5, TimeUnit.MINUTES);
                    proxyList.put(new HttpProxy(id, host, port));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                proxyList.add(new HttpProxy(id, host, port));
                count++;

            }
            log.info(count + " proxies loaded");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            dbclose(conn, statement, rs);
            dbclose(null, statement, rs);
        }
    }

//    public <T> T get(Class<T> clazz, String sql, Objects... args) {
//        return null;
//    }

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

    public void parallelVerifyProxy() {

        pool.submit(new Runnable() {
            @Override
            public void run() {
                loadProxies();
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 24; i++) {
            pool.submit(new ProxyVerifier());
            log.info(i + "th task submitted");
        }


        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


//    @Override
//    public void run() {
//
//
//        parallelVerifyProxy();
//        log.info("finished verifying all proxies parallelly");
//    }


    public class ProxyVerifier implements Runnable {

        private HttpClient httpclient;

        public ProxyVerifier() {

            HttpParams httpParameters = new BasicHttpParams();
            int timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            this.httpclient = new DefaultHttpClient(httpParameters);

        }

        public String getLocation(HttpProxy httpProxy) {
            String location = null;
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
            HttpGet httpget = new HttpGet("http://ip38.com/ip.php?ip=" + httpProxy.getHost());
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            String html = null;
            try {
                html = EntityUtils.toString(entity, "GBK");
//                log.info(html);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //extract location
            //("//ul[@class='ul1']/li/text()")
            /*<div class="cha"><h1><a href="http://ip38.com">ip38.com(ip地址查询)</a></h1>
            <form method="get" action="" name="ipform" onsubmit="return checkIP();" style="padding:20px 0">
            <div>
            <input name="ip" type="text" id="ip" autocomplete="off" onblur="saveurl();" value="114.80.235.143" size="30" url="true">
            <input type="submit" value=" 查询" id="sub" class="form-btn">
            <input type="hidden" name="action" value="2">
            </div>
            </form>

            <font size="3">您查询的IP：<font color="#FF0000">114.80.235.143</font>&nbsp;&nbsp;IP详细地址：<font color="#FF0000">上海市静安区电信</font></font><br>        </div>

            */
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("div.cha font");
            Element element = null;
            if (elements.size() >= 3) {
                element = elements.get(2);
            }

            location = element.text();

            for (String city : cities) {
                if (location.contains(city)) {
//                    log.info(location + " contains " + city);
                    location = city;
                    break;
                }

            }
//            log.info("get location: " + location+" of "+httpProxy.getHost());
            return location;

        }

        public boolean verifyProxy(HttpProxy httpProxy) {
            try {

                HttpHost proxy = new HttpHost(httpProxy.getHost(), httpProxy.getPort());

                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
                HttpGet httpget = new HttpGet(TARGET_SITE);
                HttpResponse response = httpclient.execute(httpget);
//                HttpResponse response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();
                String html = EntityUtils.toString(entity, "UTF8");

                // releaseConnection等同于reset，作用是重置request状态位，为下次使用做好准备。
                // 其实就是用一个HttpGet获取多个页面的情况下有效果；否则可以忽略此方法。
                httpget.releaseConnection();


                if (html.contains(TEST_STR)) {
//                    System.out.println(html);
                    return true;
                }

            } catch (Exception e) {
//                e.printStackTrace();
                return false;
            }
            return false;
        }


        @Override
        public void run() {
            PreparedStatement statement = null;
            ResultSet rs = null;
            String sql = null;
            HttpProxy httpProxy = null;
            while (!proxyList.isEmpty()) {
                if (proxyList.isEmpty())
                    break;
                try {
                    httpProxy = proxyList.poll(10, TimeUnit.SECONDS);
                    tested.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                try {
                    if (!verifyProxy(httpProxy)) {
                        sql = "delete from http_proxy where id=?";
                        statement = conn.prepareStatement(sql);
                        statement.setInt(1, httpProxy.getId());
                        statement.executeUpdate();
                        log.info("delete " + httpProxy);
                    } else {
                        String location = getLocation(httpProxy);
                        if (null != location && !location.isEmpty()) {
                            sql = "update http_proxy set location=? where id=?";
                            statement = conn.prepareStatement(sql);
                            statement.setString(1, location);
                            statement.setInt(2, httpProxy.getId());
                            statement.executeUpdate();
                            log.info("can use " + httpProxy + " from " + location);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dbclose(null, statement, rs);
//            	dbclose(conn,statement,rs);
                }

            }
            terminated.getAndIncrement();
            log.info(Thread.currentThread().getName() + " queue empty and processed " + tested + " terminated " + terminated);
        }
    }

}
