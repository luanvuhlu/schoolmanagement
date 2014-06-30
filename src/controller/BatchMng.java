/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public interface BatchMng {
    public int addBatch(String[] data);
    public int editBatch(String[] data);
    public String removeBatch(int ID);
    public int deactiveBatch(int ID);
}
