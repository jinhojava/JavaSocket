import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Quiz07 {
    static class Receiver extends Thread{
        BufferedInputStream input;
        boolean running = false;

        public Receiver(BufferedInputStream input){
            this.input=input;
        }

        public void stop2(){
            running =false;
        }

        @Override
        public void run() {
            byte [] buffer = new byte[256];
            running = true;
           while(running){
               try {
                   System.out.println("wait message");
                   int length = input.read(buffer,0,buffer.length);
                   System.out.println(new String(Arrays.copyOf(buffer,length)));
               }catch (IOException e){
                   System.err.println("Error:" +e.getMessage());
                   stop2();
               }
           }
        }
    }


    public static void main(String[] args) {
        try{
            Socket socket = new Socket("180.210.81.192",12345);
            BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());

        Receiver receiver = new Receiver(input);
        receiver.start();

        for(int i=0; i<5; i++){
            String message = "Hello : "+i;
            System.out.println("Send : "+message);
            output.write(message.getBytes());
            output.flush();
            Thread.sleep(1000);
        }
        socket.close();

        }catch (UnknownHostException e){
            System.out.println("호스트를 알 수 없습니다");
        }catch (IOException e){
            System.out.println("io에러 발생했습니다");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
