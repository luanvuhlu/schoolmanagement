/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.home;

import controller.Authentication;
import controller.GeneralManager;
import controller.Validate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luan
 */
public class AccountInfo extends javax.swing.JInternalFrame {

    private Authentication auth;
    private GeneralManager manger;
    private int role;
    private String email;

    /**
     * Creates new form AccountInfo
     */
    public AccountInfo(int w, int h, Authentication auth) {
        initComponents();
        this.auth = auth;
        this.manger = new GeneralManager();
        role = auth.getRole();
        initUI();
        setSize(w, h + 30);

    }

    private void initUI() {
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Connection connect = null;
        connect = connectDB.ConnectDB.getConnect();
        if (connect == null) {
            JOptionPane.showMessageDialog(this, "Connect fail !");
            return;
        }
        String SQL = "";
        ResultSet res = null;
        java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp time = null;
        DefaultTableModel model = (DefaultTableModel) tblHistory.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        if (role == Authentication.HEADER_ADMIN) {
            try {
                SQL = "SELECT username, name, email FROM admin WHERE ID=" + auth.getID();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    labelUsername.setText(res.getString(1));
                    labelName.setText(res.getString(2));
                    email = res.getString(3);
                    txtEmail.setText(email);
                    labelRole.setText("Admin");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (role == Authentication.STAFF_MANAGER || role == Authentication.STUDENT_MANAGER) {
            try {
                SQL = "SELECT usrn , name,email, role FROM user WHERE ID=" + auth.getID();

                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    labelUsername.setText(res.getString(1));
                    labelName.setText(res.getString(2));
                    txtEmail.setText(res.getString(3));
                    labelRole.setText(manger.parseRole(res.getInt(4)));
                } else {
                    JOptionPane.showMessageDialog(this, "Have some error ! Please logout and try again !");
                    return;
                }

            } catch (SQLException ex) {
                Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        SQL = "SELECT time, action FROM authentication WHERE ID_People=" + auth.getID() + " AND role=" + auth.getRole() + " ORDER BY time desc;";
        try {
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                Vector row = new Vector();
                time = res.getTimestamp(1);
                row.add(time);
                if (res.getInt(2) == 0) {
                    row.add("Logout");
                } else {
                    row.add("Login");
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save() {
        if(!validateForm()){
            return;
        }
        Connection connect = null;
        connect = connectDB.ConnectDB.getConnect();
        if (connect == null) {
            JOptionPane.showMessageDialog(this, "Connect fail !");
        }
        String SQL = "";
        PreparedStatement state = null;
        if (role == Authentication.HEADER_ADMIN) {
            SQL = "UPDATE admin SET email=? WHERE ID=?";
        } else if (role == Authentication.STAFF_MANAGER || role == Authentication.STUDENT_MANAGER) {
            SQL = "UPDATE user SET email=? WHERE ID=?";
        }
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, txtEmail.getText());
            state.setInt(2, auth.getID());
            int res = state.executeUpdate();
            if (res != 1) {
                JOptionPane.showMessageDialog(this, "Update fail !");
            } else {
                JOptionPane.showMessageDialog(this, "Update success !");
                initUI();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean validateForm(){
        if(!Validate.checkEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(this, "Email incorrect ! Please try again !");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        labelUsername = new javax.swing.JLabel();
        labelRole = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel1.setText("Account Infomation");

        jLabel2.setText("Email");

        jLabel3.setText("Username");

        jLabel5.setText("Role");

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Time", "Action"
            }
        ));
        jScrollPane1.setViewportView(tblHistory);

        jLabel6.setText("Action History");

        labelUsername.setText("Username");

        labelRole.setText("Role");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/reset.gif"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel4.setText("Name");

        labelName.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 132, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(btnSave)
                                .addGap(52, 52, 52)
                                .addComponent(btnReset))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addGap(55, 55, 55)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelRole)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelUsername)
                                        .addComponent(labelName))
                                    .addGap(69, 69, 69)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelUsername))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelRole))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtEmail.setText(email);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelRole;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
