/*
©2020.Todos los derechos reservados.
Autor: Kevin Fondevila
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin Fondevila
 */

import java.net.*;
import java.util.*;
import java.io.*;

public class Mensajes extends javax.swing.JTextArea implements Runnable{
    private Socket s;
    private DataOutputStream out;
    private DataInputStream in; 
    private String ip;
    private int port;
    
    public Mensajes(){}
    public Mensajes(String ip, int port){        
        this.ip = ip;
        this.port = port;         
    }

    @Override
    public void run() {
        while(true){
            try{
                s = new Socket(this.ip,this.port);        
                out = new DataOutputStream(s.getOutputStream());
                out.writeUTF("?");
                in = new DataInputStream(s.getInputStream());                                
                this.setText(in.readUTF());
                Thread.sleep(1000);
            }catch(Exception e){System.out.println(this.ip+":"+this.port+e.toString());}
        }
    }
}
