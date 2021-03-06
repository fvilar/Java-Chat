/*
©2020.Todos los derechos reservados.
Autor: Kevin Fondevila
*/



import java.net.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server extends Thread{
	        
        private static LinkedList<String> msgs;
        private static LinkedList<String> clients;
        private static OperacionesDB db;  
        private static ServerSocket ss = null;
        private static Socket s = null;
        
        public Server(){
            db = new OperacionesDB("base.db");
        }        
	public static void main(String Args[]){    
            BufferedReader rf = new BufferedReader(new InputStreamReader(System.in));
            String comm = "";
            StringTokenizer lk;
            LinkedList<String> ls = new LinkedList<>();
            Server h = new Server();
            h.start();            
            while(true){                
                try{
                    comm=rf.readLine();
                    lk = new StringTokenizer(comm," ");
                    if(!comm.equals("")){
                        ls.clear();
                        while(lk.hasMoreTokens()){ls.add(lk.nextToken());}                          
                        if(ls.size()==1){
                            if(comm.equals("cls")){
                               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            }
                            else if(comm.equals("restart")){
                                try{                                    
                                    h.interrupt();
                                    ss.close();                                                                        
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    new ProcessBuilder("cmd", "/c", "Server.bat").inheritIO().start().waitFor();
                                    System.exit(1);
                                    
                                }catch (Exception e) {
                                    System.out.println(e.toString());
                                }
                            }
                            else if(comm.equals("help")){
                                System.out.println("\n\nCommand List\n--------------------------------------------\n");
                                System.out.println("cls: Limpia la pantalla");
                                System.out.println("create user [UserName] [Password]: Crea un nuevo usuario con su contraseña");
                                System.out.println("delete user [UserName]: Elimina a un usuario de la base de datos");
                                System.out.println("delete users: Elimina a todos los usuarios de la base de datos");
                                System.out.println("users list: Optiene una lista de todos los usuarios registrados en la base de datos con su respectiva contraseña");
                                System.out.println("users list -c: Optiene una lista de todos los usuarios conectados al servidor");
                                System.out.println("restart: Reinicia el servidor");                                
                                System.out.println("help: Muestra una lista de todos los comandos disponibles\n\n");
                                
                            
                            
                            }
                            else{System.out.println("Comando desconocido");}
                        }
                        else if(ls.size()==4){                            
                            if(ls.get(0).toString().equals("create")&&ls.get(1).toString().equals("user")){                                
                                System.out.println(db.CreateUser(ls.get(2).toString(),ls.get(3).toString()));
                            }                            
                            else System.out.println("Comando desconocido");
                        }
                        else if(ls.size()==2){
                            if(ls.get(0).toString().equals("users")&&ls.get(1).toString().equals("list")){                         
                                System.out.println(db.GetUsers());
                            }
                            else if(ls.get(0).toString().equals("delete")&&ls.get(1).toString().equals("users")){                                
                                System.out.println(db.DeleteUsers());
                            }
                            else System.out.println("Comando desconocido");
                        }
                        else if(ls.size()==3){
                            if(ls.get(0).toString().equals("users")&&ls.get(1).toString().equals("list")&&ls.get(2).toString().equals("-c")){                                
                                if(clients.size()!=0){
                                    System.out.println("\n\n Usuarios Conectados\n\n");
                                    for(int i=0;i<clients.size();i++){                                        
                                        System.out.println(clients.get(i));
                                    }
                                }
                                else{
                                        System.out.println("\nNo hay usuarios conectados\n");
                                }
                            }
                            else if(ls.get(0).toString().equals("delete")&&ls.get(1).toString().equals("user")){                                
                                System.out.println(db.DeleteUser(ls.get(2).toString()));
                            }
                            else System.out.println("Comando desconocido");
                        }
                        else{System.out.println("Comando desconocido");}
                    }
                    }catch(Exception e){System.out.println(e.toString());}
            }
	}                
        
        @Override
        public void run(){                                             
            msgs = new LinkedList<>(); 
            clients = new LinkedList<>();            
            String msg;                        
		try{
			ss = new ServerSocket(2000);                           
                        System.out.println("Server running on port: "+ss.getLocalPort());                                                
                        while(!Thread.interrupted()){                              
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
        private LinkedList<String> msgs;            
        private LinkedList<String> clients;            
        private StringTokenizer tk;
        private static OperacionesDB db;
        String msg;
    public Hilo(Socket s,LinkedList<String> msgs,LinkedList<String> clients,OperacionesDB db){        
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
                //System.out.println(msg+"    ."+s.getInetAddress().toString());
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
