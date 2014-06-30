/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.swing.JTable;

/**
 *
 * @author luan
 */
public interface IEntity<T> {
    public void loadList(JTable tbl);
    public T findByID(int ID);
    public T findByName(String name);
    public int save();
    public int remove(int ID);
    public int deactive(int ID);
    public int active(int ID);
}
