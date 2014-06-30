/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connectDB.ConnectDB;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luan
 */
public abstract class Manager {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int CONNECT_FAIL = -1;
    public static final int RECORD_IN_PAGE = 10;
    public static final int MALE = 1;
    public static final int FEMALE = 0;
    public static final int OTHER_GENDER = -1;
    public static final int MARRIED = 1;
    public static final int SINGLE = 0;
    public static final String STUDENT_MODEL[] = {"ID", "Register No", "No", "Name", "Batch", "Phone", "Email", "Gender"};
    public static final String STAFF_MODEL[] = {"No", "Name", "Phone", "Email", "Position", "Department", "Gender"};
    public static final String DEPARTMENT_MODEL[] = {"ID", "Name"};
    public static final String POSITION_MODEL[] = {"ID", "Name", "Department"};
    public static final String BATCH_MODEL[] = {"ID", "Name", "Date"};
    public static final String MARK_MODEL[] = {"ID", "Subject", "Student No", "Student", "Point", "Time"};
    public static final String EXTRA_CURRICULAR_MODEL[] = {"ID", "Name", "Location", "Start", "End"};
    public static final String SUBJECT_MODEL[] = DEPARTMENT_MODEL;
    public static final String USER_MODEL[] = {"ID", "Username", "Password", "Name", "Role", "Email"};
    public static final String STAFF_CHOOSER_MODEL[] = {"No", "Name", "Position", "Department"};
    public static final int USER = 0;
    public static final int STAFF = 1;
    public static final int STUDENT = 2;
    public static final int BATCH = 3;
    public static final int SUBJECT = 4;
    public static final int POSITION = 5;
    public static final int DEPARTMENT = 6;
    public static final int EXTRA_CURRICULAR = 7;
    public static final int MARK=8;

    public int executeManySQL(List<String> listSQL) {
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return CONNECT_FAIL;
        }
        Statement state = null;
        try {
            connect.setAutoCommit(false);
            state = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            for (String SQL : listSQL) {
                state.addBatch(SQL);
                System.out.println(SQL);
            }
            state.executeBatch();
            connect.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connect.rollback();
                return FAIL;
            } catch (SQLException ex1) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                try {
                    connect.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

        return SUCCESS;
    }

    public int executeUpdate(String SQL) {
        System.out.println(SQL);
        Connection connect = null;
        connect = ConnectDB.getConnect();
        int res = 0;
        if (connect == null) {
            return CONNECT_FAIL;
        }
        Statement state = null;
        try {
            res = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(SQL);
            if (res != 1) {
                return FAIL;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            return FAIL;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return SUCCESS;
    }

//    public int executeUpdate(String SQL, String data[]) {
//        Connection connect = null;
//        connect = ConnectDB.getConnect();
//        int res = 0;
//        if (connect == null) {
//            return CONNECT_FAIL;
//        }
//        PreparedStatement state = null;
//        try {
//            state=connect.prepareStatement(SQL);
//            for(int i=0; i<data.length; i++){
//                state.setString(i+1, data[i]);
//            }
//            res=state.executeUpdate();
//            if (res != 1) {
//                return FAIL;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
//            return FAIL;
//        } finally {
//            try {
//                connect.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return SUCCESS;
//    }
    public Date parseDate(String value) {
        if (!Validate.checkDate(value)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(value);
        } catch (ParseException | NullPointerException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return date;
    }

    public String parseRole(int role) {
        if (role == 1) {
            return "Admin";
        } else if (role == 2) {
            return "Staff manager";
        } else if (role == 3) {
            return "Student manager";
        } else {
            return "Unknown";
        }
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str;
    }

    public String parseGender(int gender) {
        if (gender == MALE) {
            return "Male";
        } else if (gender == FEMALE) {
            return "Female";
        }
        return "Other";
    }

    public int formatGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            return 1;
        } else if (gender.equalsIgnoreCase("Female")) {
            return 0;
        }
        return -1;
    }

    public String parseMarried(int married) {
        if (married == MARRIED) {
            return "Married";
        } else if (married == SINGLE) {
            return "Single";
        }
        return "Single";
    }

    public int formatMarried(String married) {
        if (married.equalsIgnoreCase("Married")) {
            return 1;
        } else if (married.equalsIgnoreCase("Single")) {
            return 0;
        }
        return -1;
    }

    public int parseType(String value) {
        if (value.equalsIgnoreCase("Staff manager")) {
            return Authentication.STAFF_MANAGER;
        }
        if (value.equalsIgnoreCase("Student manager")) {
            return Authentication.STUDENT_MANAGER;
        }
        return -1;
    }

    public void loadCombobox(String SQL, JComboBox combobox) {
        combobox.removeAllItems();
        combobox.addItem("Choose");
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return;
        }
        ResultSet res = null;

        try {
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                combobox.addItem(res.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getIDEdentity(String SQL) {
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return -1;
        }
        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    public void loadTable(String SQL, JTable tbl, String[] ColumnName) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return;
        }
        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                Vector row = new Vector();
                for (int i = 0; i < ColumnName.length; i++) {
                    String str = res.getString(i + 1);
                    if (ColumnName[i].equalsIgnoreCase("Gender")) {
                        str = parseGender(Integer.parseInt(str));
                    } else if (ColumnName[i].equalsIgnoreCase("Role")) {
                        str = parseRole(Integer.parseInt(str));
                    }
                    row.add(str);
                }
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTable(PreparedStatement state, JTable tbl, String[] ColumnName) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return;
        }
        ResultSet res = null;
        try {
            res = state.executeQuery();
            while (res.next()) {
                Vector row = new Vector();
                for (int i = 0; i < ColumnName.length; i++) {
                    String str = res.getString(i + 1);
                    if (ColumnName[i].equalsIgnoreCase("Gender")) {
                        str = parseGender(Integer.parseInt(str));
                    } else if (ColumnName[i].equalsIgnoreCase("Role")) {
                        str = parseRole(Integer.parseInt(str));
                    }
                    row.add(str);
                }
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTable(Object[][] data, JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    public int confirmRemove(Component parentComponent, String message) {
        return JOptionPane.showConfirmDialog(parentComponent, message);
    }

    public int getSelectID(JTable tbl) {
        int rowSelect = tbl.getSelectedRow();
        int ID = Integer.parseInt(tbl.getValueAt(rowSelect, 0).toString());
        return ID;
    }

    public Vector getSelectRow(JTable tbl) {
        int rowSelect = tbl.getSelectedRow();
        if (rowSelect == -1) {
            return null;
        }
        Vector row = new Vector();
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            if (tbl.getValueAt(rowSelect, i) == null) {
                row.add("");
            } else {
                row.add(tbl.getValueAt(rowSelect, i));
            }
        }
        return row;
    }

    public void refreshTable(JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void removeSelectedRowTable(JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.removeRow(tbl.getSelectedRow());
    }

    public boolean checkUnique(String nameColummn, String nameTable, String name) {
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return false;
        }
        String SQL = "SELECT " + nameColummn + " FROM " + nameTable;
        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                if (res.getString(1).equalsIgnoreCase(name)) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean checkUnique(String SQL) {
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return false;
        }
        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public void showMessage(int res, Component parent) {
        if (res == CONNECT_FAIL) {
            JOptionPane.showMessageDialog(parent, "Connect fail ! Please try again !");
            return;
        }
        if (res == FAIL) {
            JOptionPane.showMessageDialog(parent, "Error ! Please try again !");
            return;
        }
//        if (res == SUCCESS) {
//            JOptionPane.showMessageDialog(parent, "Success !");
//            return;
//        }
    }

    public void showMessage(String res, Component parent) {
        JOptionPane.showMessageDialog(parent, res);
    }

    public String getNameObject(int ID, int object) {
        String SQL = "";
        switch (object) {
            case USER:
                SQL += "SELECT name FROM user WHERE ID=" + ID;
                break;
            case BATCH:
                SQL += "SELECT name FROM batch WHERE ID=" + ID;
                break;
            case DEPARTMENT:
                SQL += "SELECT name FROM department WHERE ID=" + ID;
                break;
            case POSITION:
                SQL += "SELECT name FROM position WHERE ID=" + ID;
                break;
            case STUDENT:
                SQL += "SELECT name FROM student WHERE ID=" + ID;
                break;
            case SUBJECT:
                SQL += "SELECT name FROM subject WHERE ID=" + ID;
                break;
            case STAFF:
                SQL += "SELECT name FROM staff WHERE No=" + ID;
                break;
            case EXTRA_CURRICULAR:
                SQL += "SELECT name FROM extra_curricular WHERE ID=" + ID;
                break;
            default:
                SQL = "";
                break;
        }
        if (SQL.isEmpty()) {
            return "";
        }
        Connection connect = null;
        connect = ConnectDB.getConnect();
        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        return "";
    }
}
