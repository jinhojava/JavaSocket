import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientSocketTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("127.0.0.1",2234)){
            BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("Connected");

            output.write("#Xtra\n".getBytes());
            output.flush();
while(socket.isConnected()){
            String message ="   Hello, CNU["+socket.getLocalPort()+"]\n";
            output.write(message.getBytes());
            output.flush();

            byte[] buffer = new byte[256];
            int length = input.read(buffer,0,buffer.length);
            if(length>0){
                String echoMessage = new String(Arrays.copyOf(buffer, length));
                System.out.println("Echo receive:" + echoMessage);
                Thread.sleep(1000);
            }}
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
