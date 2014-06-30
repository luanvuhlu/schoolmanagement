/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public interface UserMng {
    public int addUser(String[] data);
    public int deactiveUser(int ID);
    public int editUser(String[] data);
}
