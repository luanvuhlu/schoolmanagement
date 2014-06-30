/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.home;

import controller.Authentication;
import controller.Validate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luan
 */
public class ChangePassword extends javax.swing.JDialog {

    private Authentication auth;
    private int role;

    /**
     * Creates new form ChangePassword
     */
    public ChangePassword(java.awt.Frame parent, boolean modal, Authentication auth) {
        super(parent, modal);
        this.auth = auth;
        role = auth.getRole();
        initComponents();
        initUI();
        this.setLocationRelativeTo(parent);
    }

    private void initUI() {
        Connection connect = null;
        connect = connectDB.ConnectDB.getConnect();
        if (connect == null) {
            JOptionPane.showMessageDialog(this, "Connect fail !");
            return;
        }
        String SQL = "";
        ResultSet res = null;

        if (role == Authentication.HEADER_ADMIN) {
            try {
                SQL = "SELECT username FROM admin WHERE ID=" + auth.getID();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    labelUsername.setText(res.getString(1));
                } else {
                    JOptionPane.showMessageDialog(this, "Have some error ! Please logout and try again !");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (role == Authentication.STAFF_MANAGER || role == Authentication.STUDENT_MANAGER) {
            try {
                SQL = "SELECT usrn FROM user WHERE ID=" + auth.getID();

                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    labelUsername.setText(res.getString(1));

                } else {
                    JOptionPane.showMessageDialog(this, "Have some error ! Please logout and try again !");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void save() {
        if (!validateForm()) {
            return;
        }
        Connection connect = null;
        connect = connectDB.ConnectDB.getConnect();
        if (connect == null) {
            JOptionPane.showMessageDialog(this, "Connect fail !");
            return;
        }
        String SQLCheck = "";
        String SQLUpdate = "";
        PreparedStatement pre = null;
        ResultSet res = null;
        if (role == Authentication.HEADER_ADMIN) {
            SQLCheck = "SELECT 1 FROM admin WHERE ID=? AND password=?";
            SQLUpdate = "UPDATE admin SET password=? WHERE ID=?";
        } else if (role == Authentication.STAFF_MANAGER || role == Authentication.STUDENT_MANAGER) {
            SQLCheck = "SELECT 1 FROM user WHERE ID=? AND pswd=?";
            SQLUpdate = "UPDATE user SET email=? WHERE ID=?";
        }
        try {
            pre = connect.prepareStatement(SQLCheck);
            pre.setInt(1, auth.getID());
            pre.setString(2, new String(txtPswd.getPassword()));

            res = pre.executeQuery();
            if (!res.next()) {
                JOptionPane.showMessageDialog(this, "Password in correct ! Please try again !");
                return;
            }
            if (new String(txtPswd.getPassword()).equalsIgnoreCase(new String(txtNewPswd.getPassword()))) {
                JOptionPane.showMessageDialog(this, "New Password and old Password is match !");
                return;
            }
            pre = connect.prepareStatement(SQLUpdate);
            pre.setString(1, new String(txtNewPswd.getPassword()));
            pre.setInt(2, auth.getID());
            int result = pre.executeUpdate();
            if (result != 1) {
                JOptionPane.showMessageDialog(this, "Update fail !");
            } else {
                JOptionPane.showMessageDialog(this, "Update success !");
                dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateForm() {
        if (Validate.isEmpty(new String(txtPswd.getPassword()))
                || Validate.isEmpty(new String(txtNewPswd.getPassword()))
                || Validate.isEmpty(new String(txtReNewPswd.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Password is required !");
            return false;
        }
        if (!Validate.checkPassword(new String(txtNewPswd.getPassword()), new String(txtReNewPswd.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Re-Password is not match !");
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCacel = new javax.swing.JButton();
        txtPswd = new javax.swing.JPasswordField();
        txtNewPswd = new javax.swing.JPasswordField();
        txtReNewPswd = new javax.swing.JPasswordField();
        labelUsername = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Password");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel1.setText("Change Password");

        jLabel2.setText("Username");

        jLabel3.setText("Old Password");

        jLabel4.setText("New Password");

        jLabel5.setText("Re-enter New Password");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCacel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/cancel.png"))); // NOI18N
        btnCacel.setText("Cancel");
        btnCacel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCacelActionPerformed(evt);
            }
        });

        txtReNewPswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReNewPswdActionPerformed(evt);
            }
        });

        labelUsername.setText("Username");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPswd)
                            .addComponent(txtNewPswd, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(txtReNewPswd, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(labelUsername)))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addGap(33, 33, 33)
                        .addComponent(btnCacel)
                        .addGap(69, 69, 69)))
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNewPswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtReNewPswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCacel))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtReNewPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReNewPswdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReNewPswdActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCacelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCacelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCacelActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCacel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JPasswordField txtNewPswd;
    private javax.swing.JPasswordField txtPswd;
    private javax.swing.JPasswordField txtReNewPswd;
    // End of variables declaration//GEN-END:variables
}
