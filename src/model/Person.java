/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author luan
 */
public class Person {
    
    private String name;
    private Date birthday;
    private String address;
    private String phone;
    private String email;
    private int gender;
    
    
    public Person() {
    }

    public Person(String name, Date birthday, String address, String phone, String email, int gender) {
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public String getAddress() {
        if(address==null){
            return "Unknown";
        }
        return address;
    }

    public Date getBirthday() {
        if(birthday==null){
            return null;
        }
        return birthday;
    }

    public String getEmail() {
        if(email==null){
            return "Unknown";
        }
        return email;
    }

    public String getName() {
        if(name==null){
            return "Unknown";
        }
        return name;
    }

    

    public String getPhone() {
        if(phone==null){
            return "Unknown";
        }
        return phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
