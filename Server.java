
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	                        
	public static void main(String Args[]){
            
            ServerSocket ss = null;
            Socket s = null;            
            LinkedList msgs = new LinkedList(); 
            String msg;
            DataInputStream in;
		try{
			ss = new ServerSocket(2000,20);                           
                        System.out.println("Server running on port: "+ss.getLocalPort());                                                
                        while(true){                            
                            s = ss.accept();              
                            
                            if(s.isConnected()){
                                in = new DataInputStream(s.getInputStream());
                                System.out.println("msg from: "+s.getInetAddress());
                                msg = in.readUTF();                                
                                if(msg.charAt(0)=='?'){                                
                                    
                                }else{
                                    msgs.add(msg);
                                }
                                (new Hilo(s,msgs)).start();                                
                            }
                        }

		}catch(Exception e){
                    System.out.println(e.toString());
            }

	}                        

}

class Hilo extends Thread{        
        private Socket s = null;
        
        private DataOutputStream out;
        private LinkedList msgs;            
        private StringTokenizer tk;
        String msg;
    public Hilo(Socket s,LinkedList msgs){        
        this.s = s;        
        this.msgs = msgs;
        
    }
    
    @Override
    public void run(){                
        try{            
            
            out = new DataOutputStream(s.getOutputStream());
            this.msg = "";
            
            if(msgs.size()==0){
                msg= "NO hay mensajes";
            }
            for(int i = 0; i < msgs.size();i++){
                this.msg += msgs.get(i)+"\n";    
            }                    
            out.writeUTF(msg);  
            this.msg = "";
            
            s.close();            
            out.close();            
        }catch(Exception e){
            System.out.println(e.toString());                        
        }        
        return;
    }
}
