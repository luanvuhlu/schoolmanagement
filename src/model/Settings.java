/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author luan
 */
public class Settings implements Serializable{
    private String IP;
    private int port;
    private String nameDatabase;
    private String usrnDatabase;
    private String pswdDatabase;
    
    private int language;
    private boolean remove_staff;
    private boolean remove_student;
    private boolean remove_subject;
    private boolean remove_extraCurricular;
    private boolean remove_department;
    private boolean remove_position;
    private boolean remove_user;
    private boolean remove_batch;
    
    private boolean show_deactived_staff;
    private boolean show_deactived_student;
    private boolean show_deactived_subject;
    private boolean show_deactived_extraCurricular;
    private boolean show_deactived_department;
    private boolean show_deactived_position;
    private boolean show_deactived_user;
    private boolean show_deactived_batch;
    
    public static int VIETNAMESE=0;
    public static int ENGLISH=1;
    public static int FRANCE=2;
    public static boolean TEMPORARY=true;
    public static boolean PERMANENTLY=false;

    public int getPort() {
        return port;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }

    public String getIP() {
        return IP;
    }

    public String getUsrnDatabase() {
        return usrnDatabase;
    }

    public String getPswdDatabase() {
        return pswdDatabase;
    }

    public int getLanguage() {
        return language;
    }

    public boolean isRemove_staff() {
        return remove_staff;
    }

    public boolean isRemove_student() {
        return remove_student;
    }

    public boolean isRemove_subject() {
        return remove_subject;
    }

    public boolean isRemove_extraCurricular() {
        return remove_extraCurricular;
    }

    public boolean isRemove_department() {
        return remove_department;
    }

    public boolean isRemove_position() {
        return remove_position;
    }

    public boolean isRemove_user() {
        return remove_user;
    }

    public boolean isRemove_batch() {
        return remove_batch;
    }

    public boolean isShow_deactived_staff() {
        return show_deactived_staff;
    }

    public boolean isShow_deactived_student() {
        return show_deactived_student;
    }

    public boolean isShow_deactived_subject() {
        return show_deactived_subject;
    }

    public boolean isShow_deactived_extraCurricular() {
        return show_deactived_extraCurricular;
    }

    public boolean isShow_deactived_department() {
        return show_deactived_department;
    }

    public boolean isShow_deactived_position() {
        return show_deactived_position;
    }

    public boolean isShow_deactived_user() {
        return show_deactived_user;
    }

    public boolean isShow_deactived_batch() {
        return show_deactived_batch;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNameDatabase(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public void setUsrnDatabase(String usrnDatabase) {
        this.usrnDatabase = usrnDatabase;
    }

    public void setPswdDatabase(String pswdDatabase) {
        this.pswdDatabase = pswdDatabase;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setRemove_staff(boolean remove_staff) {
        this.remove_staff = remove_staff;
    }

    public void setRemove_student(boolean remove_student) {
        this.remove_student = remove_student;
    }

    public void setRemove_subject(boolean remove_subject) {
        this.remove_subject = remove_subject;
    }

    public void setRemove_extraCurricular(boolean remove_extraCurricular) {
        this.remove_extraCurricular = remove_extraCurricular;
    }

    public void setRemove_department(boolean remove_department) {
        this.remove_department = remove_department;
    }

    public void setRemove_position(boolean remove_position) {
        this.remove_position = remove_position;
    }

    public void setRemove_user(boolean remove_user) {
        this.remove_user = remove_user;
    }

    public void setRemove_batch(boolean remove_batch) {
        this.remove_batch = remove_batch;
    }

    public void setShow_deactived_staff(boolean show_deactived_staff) {
        this.show_deactived_staff = show_deactived_staff;
    }

    public void setShow_deactived_student(boolean show_deactived_student) {
        this.show_deactived_student = show_deactived_student;
    }

    public void setShow_deactived_subject(boolean show_deactived_subject) {
        this.show_deactived_subject = show_deactived_subject;
    }

    public void setShow_deactived_extraCurricular(boolean show_deactived_extraCurricular) {
        this.show_deactived_extraCurricular = show_deactived_extraCurricular;
    }

    public void setShow_deactived_department(boolean show_deactived_department) {
        this.show_deactived_department = show_deactived_department;
    }

    public void setShow_deactived_position(boolean show_deactived_position) {
        this.show_deactived_position = show_deactived_position;
    }

    public void setShow_deactived_user(boolean show_deactived_user) {
        this.show_deactived_user = show_deactived_user;
    }

    public void setShow_deactived_batch(boolean show_deactived_batch) {
        this.show_deactived_batch = show_deactived_batch;
    }
    
    
}

