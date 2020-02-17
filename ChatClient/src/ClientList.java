/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
import java.net.*;
import java.util.*;
import java.io.*;

public class ClientList extends javax.swing.JTextArea implements Runnable{
    private Socket s;
    private DataOutputStream out;
    private DataInputStream in;  
    private int port;
    private String ip;
    public ClientList(){}
    public ClientList(String ip, int port){
      this.port = port;
      this.ip=ip;            
    }
    @Override
    public void run() {
        while(true){
            try{
                s = new Socket(this.ip,this.port);        
                out = new DataOutputStream(s.getOutputStream());
                out.writeUTF("*");
                in = new DataInputStream(s.getInputStream());                                
                this.setText(in.readUTF());
                Thread.sleep(5000);
            }catch(Exception e){System.out.println(e.toString());}
        }                
    }
    
    
}
