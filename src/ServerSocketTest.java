import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerSocketTest {
    public static void main(String[] args) throws InterruptedException {

        List<EchoServer> echoServerList = new LinkedList<>();

        try(ServerSocket serverSocket = new ServerSocket(2234)){          //while써서 반복연결 가능하게 만듦

            System.out.println("Server socket created");                       //while안쓰면 반복안됨 그땐 socket.close()해야지

            while(!serverSocket.isClosed()){
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("client connected:  "+ socket.getInetAddress()
                                                        +":"+socket.getPort());

                EchoServer echoServer =new EchoServer(socket.getInputStream(),socket.getOutputStream(),echoServerList);

                echoServer.start();
                //echoServer.join();//기다리기
                echoServerList.add(echoServer);

                }
            for(EchoServer echoServer : echoServerList){
                echoServer.stop2();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
