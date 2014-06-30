/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public interface DepartmentMng {
    public int deactiveDepartment(int ID);
    public int editDepartment(String[] data);
    public int addDepartment(String[] data);
    public String removeDepartment(int ID);
    
}
