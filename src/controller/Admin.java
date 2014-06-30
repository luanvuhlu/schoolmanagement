/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public class Admin extends Manager implements UserMng{
     @Override
    public int addUser(String[] data) {
        String SQL="INSERT user (usrn, pswd, name, role, email) VALUES ( "
                + "'"+data[0]+"', "
                + "'"+data[1]+"', "
                + "'"+data[2]+"', "
                +data[3]+", "
                + "'"+data[4]+"')";
         System.out.println(SQL);
        return executeUpdate(SQL);
    }

    @Override
    public int editUser(String[] data) {
        String SQL="UPDATE user SET "
                + "usrn='"+data[1]+"', "
                + "pswd='"+data[2]+"', "
                + "name='"+data[3]+"', "
                + "role="+data[4]+", "
                + "email='"+data[5]+"' "
                + "WHERE ID="+data[0];
        System.out.println(SQL);
        return executeUpdate(SQL);
    }
    
    @Override 
    public int deactiveUser(int ID) {
        String SQL="UPDATE user SET status=0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }
    
  

}
