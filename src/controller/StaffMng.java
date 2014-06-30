/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Staff;

/**
 *
 * @author luan
 */
public interface StaffMng {
    public String searchStaff(int no, String name);
    public int addStaff(Staff staff);
    public int editStaff(Staff staff, Staff oldStaff);
    public int removeStaff(int ID);
    public int deactiveStaff(int ID);
    public Staff getDetailStaff(int No);
    public String getListStaff();
}
