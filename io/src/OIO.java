
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

/**
 * @ClassName OIO
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-30 17:35
 * @Version V1.0
 **/
public class OIO {

    public static void main(String[] args) {
        try {
            OIO.serve(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);     //1
        try {
            for (; ; ) {
                final Socket clientSocket = socket.accept();    //2
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(new Runnable() {                        //3
                    @Override
                    public void run() {
                        OutputStream out;
                        byte[] buf = new byte[1024];
                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                            // 处理客户端数据

                            System.out.println("客户端发过来的内容:" + clientInputStr);
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                            out.flush();
                            clientSocket.close();                //5

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }
                    }
                }).start();                                        //6
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
