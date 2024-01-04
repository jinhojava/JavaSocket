import java.io.*;
import java.util.Arrays;
import java.util.List;

//Quiz-09. Echo Server를 만들어 보자.
//아래의 요구 사항에 맞는 server를 구현해 보자.
//실행시 서비스를 위한 port를 지정할 수 있다. 별도의 port 지정이 없는 경우, 1234를 기본으로 한다.
//Server는 실행 후 대기 상태를 유지하고, client가 접속되면 client 정보를 출력한다.
//Server에서는 연결된 socket이 끊어지기 전까지 client에서 보내온 데이터를 client로 다시 돌려 보낸다.
//Client 연결이 끊어지면, server socket을 닫고 프로그램을 종료한다.(반복연결 아닌경우에)반복연결시 while로 accept반복시켜야한다.

public class EchoServer extends Thread {
    String id="";

    List<EchoServer> echoServerList;
    boolean running = false;
    byte[] buffer = new byte[256];
    BufferedReader bufferedInput;
    BufferedOutputStream bufferedOutput;

    public EchoServer(InputStream input, OutputStream output, List<EchoServer> echoServerList){

        bufferedInput = new BufferedReader(new InputStreamReader(input));
        bufferedOutput = new BufferedOutputStream(output);
        this.echoServerList = echoServerList;
    }

    @Override
    public void run() {

        running =true;
        while (running){
            try {
                    String message = bufferedInput.readLine();
                    System.out.println("Receive :" + message);

                    if((message.charAt(0)=='#')&& message.length()>1){
                        String id = message.substring(1,message.length());
                        setId(id);
                    }else if(id.length()>0) {

                        message += "from "+ id;
                        System.out.println("Echo :" + message);

                        for (EchoServer echoServer : echoServerList) {
                            echoServer.write(message);
                        }
                    }

            } catch (IOException e) {
                running = false;
            }
        }
    }

    public void stop2() {
        running=false;
    }
    public void write(String message){
        try{
            bufferedOutput.write(message.getBytes());
            bufferedOutput.flush();
        }catch (IOException e){
            stop2();
        }
    }
    public void setId(String id){
        if((id==null)||id.length()==0){
            throw new IllegalArgumentException();
        }
        this.id =id;
        System.out.println("SET ID :"+id);
    }
}
