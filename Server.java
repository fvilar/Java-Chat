
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	        
        private static LinkedList msgs;
	public static void main(String Args[]){
            
            ServerSocket ss = null;
            Socket s = null;            
            msgs = new LinkedList(); 
            String msg;            
		try{
			ss = new ServerSocket(2000,20);                           
                        System.out.println("Server running on port: "+ss.getLocalPort());                                                
                        while(true){                            
                            s = ss.accept();                                          
                            if(s.isConnected()){                                
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
        private DataInputStream in;
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
            in = new DataInputStream(s.getInputStream());                                
            msg = in.readUTF();                                
            if(msg.charAt(0)=='?'){
                this.msg = "";
                if(msgs.size()==0){
                  msg= "NO hay mensajes";
                }
                for(int i = 0; i < msgs.size();i++){
                  this.msg += msgs.get(i)+"\n";    
                }                    
            }else{
                msgs.add(msg);
                System.out.println(msg+"    ."+s.getInetAddress().toString());
            }
            out = new DataOutputStream(s.getOutputStream());                                    
            out.writeUTF(msg);  
            this.msg = "";
            
            s.close();     
            in.close();
            out.close();            
        }catch(Exception e){
            System.out.println(e.toString());                        
        }        
        return;
    }
}
