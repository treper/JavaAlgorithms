package ataosky.example.network;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ataosky on 14-8-19.
 */
public class SocketTest {
    @Test
    public void testSocket() throws IOException {
//        InetAddress host = InetAddress.getLocalHost();
        InetAddress host = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(host, 8686);
        System.out.println(socket);

        InputStream in = socket.getInputStream();

        OutputStream out = new FileOutputStream("D:\\abcout.jpg");

        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        socket.close();
    }

    @Test
    public void testServerSocket() throws InterruptedException, IOException {
        System.out.println(InetAddress.getLocalHost());
        ServerSocket serverSocket = new ServerSocket(8686);
        Socket socket = serverSocket.accept();
        String ip = socket.getInetAddress().getHostAddress();
        System.out.println("client "+ip+" connected");

        OutputStream out = socket.getOutputStream();

        InputStream in = null;
        try {
            in = new FileInputStream("abc.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024];
        int len = 0;
        System.out.println("writing jpg...");
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        out.close();
        in.close();

        socket.close();
        serverSocket.close();

    }
}
