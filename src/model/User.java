/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luan
 */
public class User {
    private int ID;
    private String usrn;
    private String pswd;
    private String name;
    private String email;
    private int role;

    public User() {
    }

    public User(int ID, String usrn, String pswd, String name, String email, int role) {
        this.ID = ID;
        this.usrn = usrn;
        this.pswd = pswd;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setUsrn(String usrn) {
        this.usrn = usrn;
    }

    public String getEmail() {
        return email;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPswd() {
        return pswd;
    }

    public int getRole() {
        return role;
    }

    public String getUsrn() {
        return usrn;
    }
    
}
