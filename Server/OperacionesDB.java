
import java.sql.*;
import java.io.*;

public class OperacionesDB{

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String sql;
    private File f;    
    private String dbname;    
    
    public OperacionesDB(String dbname){
        this.dbname = dbname;
        File f = new File(dbname);
        try{            
            if(!f.exists()){                                                            
                con = connect();                
                sql = "create table users("                      
                      + "name varchar(50) not null PRIMARY KEY,"
                      + "password varchar(50) not null);select * from users";
                st = con.createStatement();
                st.executeUpdate(sql);                        
                con.commit();            
                System.out.println("DataBase created successfully");
            }else{                        
                con = connect();
                sql = "select * from users";
                st = con.createStatement();
                st.executeQuery(sql);
                System.out.println("DataBase opened successfully");
                
                con.commit();
                st.close();
                con.close();
            }        
        }catch(Exception e){System.out.println(e.toString());}
        
    }
    
    
    private Connection connect(){
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:"+this.dbname);
            con.setAutoCommit(false);        
        }catch(Exception e){System.out.println(e.toString());}
        return con;
    }
    
    public boolean CheckUser(String name, String pass){
        boolean res = false;
        con = connect();
        sql = "select name from users where name='"+name+"' and password='"+pass+"';";
        try{
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                res = true;
            }
            
        con.commit();
        rs.close();
        st.close();
        con.close();
        }catch(Exception e){System.out.println(e.toString());}
        
        return res;
    
    }
    public boolean CreateUser(String name, String pass){
        boolean res = false;
        con = connect();
        sql = "insert into users(name,password) values('"+name+"','"+pass+"');";
        try{
            st = con.createStatement();
            st.executeUpdate(sql);
            con.commit();
            res = true;                        
            
            st.close();
            con.close();
        }catch(Exception e){System.out.println(e.toString());}    
            
        return res;
    
    }
    
    
}