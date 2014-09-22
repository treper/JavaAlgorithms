package ataosky.example.network;

import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ataosky on 14-8-19.
 */
public class TestUrl {
    @Test
    public  void testUrl() throws IOException {
        URL url = new URL("http://10.5.16.89:8080/HttpProxyServer/proxyservlet?city=香港");

        System.out.println(url.getPath());
        System.out.println(url.getQuery());

        URLConnection urlConnection = url.openConnection();
        System.out.println(urlConnection);
        InputStream in = urlConnection.getInputStream();

        byte[] buffer = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream("testURL.txt");

        int len =0;
        while((len=in.read(buffer))!=-1)
        {
            fileOutputStream.write(buffer,0,len);
        }

        in.close();
        fileOutputStream.close();

    }
}
