import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
//서버에 데이터를 보내고 exit면 종료하는 프로그램
public class Quiz04 {
    public static void main(String[] args) throws IOException {
        String host = "180.210.81.192";
        int port = 12345;

        if (args.length > 0) {
            host = args[0];
        }
        try {
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException ignore) {
            System.err.println("Port가 올바르지 않습니다.");
            System.exit(1);
        }

        try {
            Socket socket = new Socket(host, port);
            System.out.println("서버에 연결되었습니다.");


            OutputStream output = socket.getOutputStream(); // socke에서 Output stream 얻기
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                String line = reader.readLine();
                if (line.equals("exit")) {
                    break;
                }

                output.write(line.getBytes()); // console에서 입력받은 문자열을 전송
                output.write("\n".getBytes()); // 문자열 끝을 위한 newline 전송
                output.flush(); // buffer에 남아 있는 데이터까지 완전히 전송

            }
            socket.close();

        } catch (ConnectException e) {
            System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}