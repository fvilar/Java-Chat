



import java.net.*;
import java.util.*;
import java.io.*;


public class Server extends Thread{
	        
        private static LinkedList msgs;
        private static LinkedList clients;
        private static OperacionesDB db;        
        
        public Server(){
            db = new OperacionesDB("base.db");
        }        
	public static void main(String Args[]){    
            BufferedReader rf = new BufferedReader(new InputStreamReader(System.in));
            String comm = "";
            StringTokenizer lk;
            Server h = new Server();
            h.start();            
            while(true){                
                try{
                    comm=rf.readLine();
                    lk = new StringTokenizer(comm," ");
                    if(!comm.equals("")){
                        if(lk.countTokens()==1){
                            if(comm.equals("cls")){
                               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            }
                            else{System.out.println("Comando desconocido");}
                        }
                        else if(lk.countTokens()==4){
                            if(lk.nextToken().equals("create")&&lk.nextToken().equals("user")){                                
                                System.out.println(db.CreateUser(lk.nextToken(),lk.nextToken()));
                            }
                            else if(lk.nextToken().equals("delete")&&lk.nextToken().equals("user")){}
                            else System.out.println("Comando desconocido");
                        }
                        else{System.out.println("Comando desconocido");}
                    }
                    }catch(Exception e){System.out.println(e.toString());}
            }
	}                
        
        @Override
        public void run(){                     
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
                                (new Hilo(s,msgs,clients,db)).start();                                
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
        private LinkedList clients;            
        private StringTokenizer tk;
        private static OperacionesDB db;
        String msg;
    public Hilo(Socket s,LinkedList msgs,LinkedList clients,OperacionesDB db){        
        this.s = s;        
        this.msgs = msgs;
        this.clients = clients;      
        this.db = db;
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
                tk = new StringTokenizer(msg,"$");                
                msg = tk.nextToken();                
                if(db.CheckUser(msg,tk.nextToken())){                                                        
                    
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
                    msg="Usuario o contraseña incorrectos";
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
