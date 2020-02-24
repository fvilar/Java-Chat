
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	        
        private static LinkedList msgs;
        private static LinkedList clients;
	public static void main(String Args[]){
            
            ServerSocket ss = null;
            Socket s = null;            
            msgs = new LinkedList(); 
            clients = new LinkedList();            
            String msg;            
		try{
			ss = new ServerSocket(2000);                           
                        System.out.println("Server running on port: "+ss.getLocalPort());                                                
                        while(true){                            
                            s = ss.accept();                                          
                            if(s.isConnected()){                                
                                (new Hilo(s,msgs,clients)).start();                                
                            }
                        }

		}catch(Exception e){
                    System.out.println(e.toString());
            }

	}                        

}

class Hilo extends Thread{        
        private LinkedList db;
        private Socket s = null;        
        private DataOutputStream out;
        private DataInputStream in;
        private LinkedList msgs;            
        private LinkedList clients;            
        private StringTokenizer tk;
        String msg;
    public Hilo(Socket s,LinkedList msgs,LinkedList clients){        
        this.s = s;        
        this.msgs = msgs;
        this.clients = clients;
        this.db = new LinkedList();
        this.db.add("admin$admin");
        this.db.add("Kevin$123");   
        this.db.add("Ileana$1234gaby");
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
            }else if(msg.charAt(0)=='*'){
                this.msg = "";
                for(int i = 0; i < clients.size();i++){
                  this.msg += clients.get(i)+"\n";    
                }
                
            }else if(msg.charAt(0)=='+'){
                tk = new StringTokenizer(msg,",");                            
                tk.nextToken();
                msg = tk.nextToken();
                if(db.indexOf(msg)!=-1){                
                    tk = new StringTokenizer(msg,"$");
                    msg = tk.nextToken();                    
                    if(clients.indexOf(msg)==-1){
                        clients.add(msg);                
                        msg = msg+" se ha conectado desde " + s.getInetAddress();
                        msgs.add(msg);
                        System.out.println(msg);                        
                        msg="OK";                        
                    }else{
                        msg="Ese usuario ya esta conectado";
                    }                    
                }else{
                    msg="Usuario o contrase�a incorrectos";
                }                
                
            }else if(msg.charAt(0)=='-'){
                tk = new StringTokenizer(msg,",");            
                tk.nextToken();                
                msg = tk.nextToken();
                clients.remove(msg);
                msg = msg+" se ha desconectado ";
                System.out.println(msg);
                msgs.add(msg);
                this.msg = "";
                
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
    }
}