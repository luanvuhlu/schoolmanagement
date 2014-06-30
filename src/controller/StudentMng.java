/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Student;

/**
 *
 * @author luan
 */
public interface StudentMng {
    public String searchStudent(int no, String name);
    public int addStudent(Student student);
    public int editStudent(Student student, Student oldStudent);
    public int removeStudent(int ID);
    public int deactiveStudent(int ID);
    public Student getStudentDetail(int No);
    public String getStudentList(int page);
}
