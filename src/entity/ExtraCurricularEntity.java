/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.ExtraCurricular;

/**
 *
 * @author luan
 */
public class ExtraCurricularEntity extends Manager implements IEntity<ExtraCurricular> {

    private ExtraCurricular extraCurricular;

    public void search(String name, int monthStart, int yearStart, int monthEnd, int yearEnd, JTable tbl) {
        String SQL = "SELECT ID, name, location, start, end FROM extra_curricular WHERE status=1";
        Connection connect = null;
        connect = ConnectDB.getConnect();
        
            if (connect == null) {
                return;
            }
            PreparedStatement state = null;
            int count = 1;
            if (!name.isEmpty()) {
                SQL += " AND name like ?";
            }
            if (monthStart != 0) {
                SQL += " AND MONTH(start) = ?";
            }
            if (yearStart != 0) {
                SQL += " AND YEAR(start) = ?";
            }
            if (monthEnd != 0) {
                SQL += " AND MONTH(end) = ?";
            }
            if (yearEnd != 0) {
                SQL += " AND YEAR(end) = ?";
            }
            try {
                state = connect.prepareStatement(SQL);
                if (!name.isEmpty()) {
                    state.setString(count++, "%" + name + "%");
                }
                if (monthStart != 0) {
                    state.setInt(count++, monthStart);
                }
                if (yearStart != 0) {
                    state.setInt(count++, yearStart);
                }
                if (monthEnd != 0) {
                    state.setInt(count++, monthEnd);
                }
                if (yearEnd != 0) {
                    state.setInt(count++, yearEnd);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExtraCurricularEntity.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            loadTable(state, tbl, EXTRA_CURRICULAR_MODEL);
        
    }

    @Override
    public ExtraCurricular findByID(int ID) {
        ExtraCurricular ec = null;
        String SQL = "SELECT ID, name, location, start, end FROM extra_curricular WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        Date date = null;
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                ec = new ExtraCurricular();
                ec.setID(res.getInt(1));
                ec.setName(res.getString(2));
                ec.setLocation(res.getString(3));
                try {
                    date = new Date(res.getDate(4).getTime());
                } catch (NullPointerException ex) {
                }
                ec.setStart(date);
                date = null;
                try {
                    date = new Date(res.getDate(5).getTime());
                } catch (NullPointerException ex) {
                }
                ec.setEnd(date);
                date = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ec;
    }

    @Override
    public ExtraCurricular findByName(String name) {
        ExtraCurricular ec = null;
        PreparedStatement state = null;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        Date date = null;
        String SQL = "SELECT ID, name, location, location, start, end FROM extra_curricular WHERE name=?";
        ResultSet res = null;
        try {
            ec = new ExtraCurricular();
            ec.setID(res.getInt(1));
            ec.setName(res.getString(2));
            ec.setLocation(res.getString(3));
            try {
                date = new Date(res.getDate(4).getTime());
            } catch (NullPointerException ex) {
            }
            ec.setStart(date);
            date = null;
            try {
                date = new Date(res.getDate(5).getTime());
            } catch (NullPointerException ex) {
            }
            ec.setEnd(date);
            date = null;
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ec;
    }

    public void setExtraCurricular(ExtraCurricular ec) {
        extraCurricular = ec;
    }

    @Override
    public int save() {
        if (extraCurricular == null) {
            return FAIL;
        }
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return CONNECT_FAIL;
        }
        int res = 0;
        java.sql.Date date = null;
        PreparedStatement state = null;
        String SQL = "";
        try {
            if (extraCurricular.getID() == 0) {
                SQL = "INSERT extra_curricular (name, location, start, end) VALUES(?, ?, ?, ?)";
                state = connect.prepareStatement(SQL);
                state.setString(1, extraCurricular.getName());
                state.setString(2, extraCurricular.getLocation());
                try {
                    date = new java.sql.Date(extraCurricular.getStart().getTime());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                state.setDate(3, date);
                date = null;
                try {

                    date = new java.sql.Date(extraCurricular.getEnd().getTime());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                state.setDate(4, date);
            } else {
                SQL = "UPDATE extra_curricular SET name=?, location=?, start=?, end=? WHERE ID=?";
                state = connect.prepareStatement(SQL);
                state.setString(1, extraCurricular.getName());
                state.setString(2, extraCurricular.getLocation());
                try {
                    date = new java.sql.Date(extraCurricular.getStart().getTime());
                } catch (NullPointerException ex) {
                }
                state.setDate(3, date);
                date = null;
                try {
                    date = new java.sql.Date(extraCurricular.getEnd().getTime());
                } catch (NullPointerException ex) {
                }
                state.setDate(4, date);
                state.setInt(5, extraCurricular.getID());
            }
            res = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExtraCurricularEntity.class.getName()).log(Level.SEVERE, null, ex);
            return FAIL;
        }
        return res;
    }

    @Override
    public int remove(int ID) {
        String SQL = "DELETE FROM extra_curricular WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE extra_curricular SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE extra_curricular SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public void loadList(JTable tbl) {
        String SQL = "SELECT ID, name, location, start, end FROM extra_curricular WHERE status=1";
        loadTable(SQL, tbl, EXTRA_CURRICULAR_MODEL);
    }
}
