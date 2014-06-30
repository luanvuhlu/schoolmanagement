/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author luan
 */
public class Validate {
    public static boolean isEmpty(String value){
        if(value==null || value.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean checkDate(String str){
        if(isEmpty(str)){
            return false;
        }
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public static boolean isNumber(String str){
        if(isEmpty(str)){
            return false;
        }
        int res=0;
        try{
            res=Integer.parseInt(str);
        }catch(NumberFormatException ex){
            return false;
        }
        return true;
    }
    public static boolean checkEmail(String str){
        
        return true;
    }
    public static boolean checkPoint(String str){
        if(!isNumber(str)){
            return false;
        }
        int point=0;
        try{
            point=Integer.parseInt(str);
        }catch(NumberFormatException ex){
            return false;
        }
        if(point <0 || point >10){
            return false;
        }
        return true;
    }
    public static boolean choosedComboBox(JComboBox combo){
        if(combo.getSelectedIndex()<=0){
            return false;
        }else{
            return true;
        }
    }
    public static boolean checkPassword(String pswd1, String pswd2){
        if(!pswd1.equalsIgnoreCase(pswd2)){
            return false;
        }
        return true;
    }
    
}
