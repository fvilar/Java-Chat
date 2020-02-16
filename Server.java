
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	    
            
        
	public static void main(String Args[]){
            ServerSocket ss = null;
            Socket s = null;
            DataInputStream in;
		try{
			ss = new ServerSocket(2000,20);   
                        System.out.println("Server running on port: "+ss.getLocalPort());                        
                        while(true){                            
                            s = ss.accept();
                            if(s.isConnected()){
                                System.out.println("msg from: "+s.getInetAddress());
                                (new Hilo(s)).start();
                            }
                        }

		}catch(Exception e){
                    System.out.println(e.toString());
            }

	}                        

}

class Hilo extends Thread{        
        private Socket s = null;
        private DataInputStream in;
        private DataOutputStream out;
    public Hilo(Socket s){        
        this.s = s;        
    }
    
    @Override
    public void run(){        
        while(s.isConnected()){
        try{            
            in = new DataInputStream(s.getInputStream());
            System.out.println(in.readUTF());
        }catch(Exception e){
            System.out.println(e.toString());
            try{
                s.close();
            }catch(Exception e2){System.out.println(e2.toString());}
            break;
        }             
    }
    }

}
