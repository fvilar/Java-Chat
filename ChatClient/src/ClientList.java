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
    
    public ClientList(){
      Thread t = new Thread(this);   
      t.start();
    }
    @Override
    public void run() {
        while(true){
            try{
                s = new Socket("127.0.0.1",2000);        
                out = new DataOutputStream(s.getOutputStream());
                out.writeUTF("*");
                in = new DataInputStream(s.getInputStream());                                
                this.setText(in.readUTF());
            }catch(Exception e){System.out.println(e.toString());}
        }                
    }
    
    
}
