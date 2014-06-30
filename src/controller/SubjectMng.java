/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public interface SubjectMng {
    public int addSubject(String[] data);
    public String removeSubject(int ID);
    public int deactiveSubject(int ID);
    public int editSubject(String[] data);
}
