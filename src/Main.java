import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

//서버로데이터보내기
public class Main {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("180.210.81.192", 12345);
            System.out.println("서버연결되었습니다");

            socket.getOutputStream().write("hoooou".getBytes());

            byte[] buffer = new byte[256];
            int length = socket.getInputStream().read(buffer);
            System.out.println("Received :"+length);
            System.out.println("Data :" + new String(Arrays.copyOf(buffer,length)));
            socket.close();

        }catch (IOException e){
            System.err.println(e);
        }
    }
}