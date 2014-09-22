package ataosky.example.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProxyVerifier implements Runnable {

    public static final Log log = LogFactory.getLog(ProxyVerifier.class);
    private static String TARGET_SITE = "http://www.tudou.com";
    private static String TEST_STR = "http://www.tudou.com/my/program/publish.html";
    private HttpClient httpclient;
    private Connection conn;
    private LinkedBlockingQueue<HttpProxy> proxyList;
    private Set<String> cities = new HashSet<String>();

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    public ProxyVerifier(Connection conn) {
        this.conn = conn;
        this.httpclient = new DefaultHttpClient();
    }

    public LinkedBlockingQueue<HttpProxy> getProxyList() {
        return proxyList;
    }

    public void setProxyList(LinkedBlockingQueue<HttpProxy> proxyList) {
        this.proxyList = proxyList;
    }

    public String getLocation(HttpProxy httpProxy){
        String location = null;
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
        HttpGet httpget = new HttpGet("http://ip38.com/ip.php?ip="+httpProxy.getHost());
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
            log.info(html);
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
        if(elements.size()>=3)
        {
            element = elements.get(2);
        }

        location = element.text();

        for(String city:cities)
        {
//            log.info(city);
            if(location.contains(city)) {
                log.info(location +" contains " +city);
                location = city;
                break;
            }

        }
        log.info("get location: "+location);
        return location;

    }

    public boolean verifyProxy(HttpProxy httpProxy) {
        try {

            HttpHost proxy = new HttpHost(httpProxy.getHost(), httpProxy.getPort());

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            HttpGet httpget = new HttpGet(TARGET_SITE);
            HttpResponse response = httpclient.execute(httpget);
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

    @Override
    public void run() {


        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = null;
        HttpProxy httpProxy = null;
        while (!proxyList.isEmpty()) {

            try {
                if (proxyList.isEmpty())
                    break;
                httpProxy = proxyList.poll(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            try {


                if (!verifyProxy(httpProxy)) {
                    sql = "delete from http_proxy where id=?";
                    statement = conn.prepareStatement(sql);
                    statement.setInt(1, httpProxy.getId());
//                    statement.executeUpdate();
                    log.info("delete " + httpProxy);
                } else {
                    String location = getLocation(httpProxy);
                    if (null != location && !location.isEmpty()) {
                        sql = "update http_proxy set location=? where id=?";
                        statement = conn.prepareStatement(sql);
                        statement.setString(1, location);
                        statement.setInt(2, httpProxy.getId());
                        statement.executeUpdate();
                        log.info("can use" + httpProxy + " from " + location);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbclose(null, statement, rs);

//            	dbclose(conn,statement,rs);
            }

        }
        log.info("queue empty");
    }


}