/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connectDB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luan
 */
public class SettingsDB {
    private String server;
    private int port;
    private String usrn;
    private String pswd;
    public static SettingsDB readSettings(){
        SettingsDB sdb=new SettingsDB();
        Properties p = new Properties();
        try {
            FileInputStream fin = new FileInputStream("Config.cfg");
            p.load(fin);
        } catch (Exception e) {
            return null;
        }
        sdb.setServer(p.getProperty("Server"));
        sdb.setPort(Integer.parseInt(p.getProperty("Port")));
        sdb.setUsrn(p.getProperty("Username"));
        sdb.setPswd(p.getProperty("Password"));
        return sdb;
    }
    public static void writeSettings(String server, int port, String usrn, String pswd){
        Calendar ca = Calendar.getInstance();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("Config.cfg");
            pw.println("#" + ca.getTime().toString());
            pw.println("Server=" + server.trim());
            pw.println("Port=" + port);
            pw.println("Username=" + usrn.trim());
            pw.println("Password=" + pswd);
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
        
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUsrn() {
        return usrn;
    }

    public String getPswd() {
        return pswd;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsrn(String usrn) {
        this.usrn = usrn;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    
}
