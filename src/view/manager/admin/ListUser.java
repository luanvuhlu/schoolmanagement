/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.manager.admin;

import controller.Admin;
import controller.Manager;
import controller.Validate;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import view.home.UIInterface;

/**
 *
 * @author luan
 */
public class ListUser extends javax.swing.JInternalFrame implements UIInterface{
    Admin admin=new Admin();
    private int act;
    
    public ListUser(int w, int h){
        this.admin=admin;
        initComponents();
        initUI();
        setSize(w, h);
    }
    @Override
    public void initUI(){
        
        enableButton(false);
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        editAddPanel.setVisible(false);
        String SQL="SELECT ID, usrn, pswd, name, role, email FROM user";
        admin.loadTable(SQL, tblUser, Manager.USER_MODEL);
    }
     @Override
    public void enableEditAddPanel(boolean b) {
        if(b==false){
            tblUser.setEnabled(true);
            txtName.setText(null);
            editAddPanel.setVisible(false);

            actionPanel.setVisible(true);
            bannerPanel.setVisible(true);
        }else{
            if(act==EDIT){
                labelAction.setText("Edit User");
                loadEdit();
            }else if(act==ADD){
                labelAction.setText("Add User");
                loadEdit();
            }
            tblUser.setEnabled(false);
            editAddPanel.setVisible(true);
            btnReset.setVisible(true);
            actionPanel.setVisible(false);
            bannerPanel.setVisible(false);
        }
    }

    @Override
    public void loadEdit() {
        if(act==EDIT){
            Vector row=admin.getSelectRow(tblUser);
            txtUsername.setText(row.get(1).toString());
            txtPassword.setText(row.get(2).toString());
            txtName.setText(row.get(3).toString());
            comboType.setSelectedItem(row.get(4).toString());
            txtEmail.setText(row.get(5).toString());
        }else if(act==ADD){
            txtUsername.setText("");
            txtPassword.setText("");
            txtName.setText("");
            comboType.setSelectedIndex(0);
            txtEmail.setText("");
        }
        txtUsername.setRequestFocusEnabled(true);
    }


    public void add() {
        String username=txtUsername.getText();
        String password=txtPassword.getText();
        String role=comboType.getSelectedItem().toString();
        String name=txtName.getText();
        String email=txtEmail.getText();
        
        //Check Unique
        if(!admin.checkUnique("usrn", "user", username)){
            JOptionPane.showMessageDialog(this, "Username must be unique !");
            return;
        }
        int roleUser=admin.parseType(role);
        if(roleUser ==-1){
            JOptionPane.showMessageDialog(this, "Role incorrect !");
            return;
        }
        String data[]=new String[]{ username, password, name, roleUser+"", email};
        int res=admin.addUser(data);
        if(res==Manager.SUCCESS){
            initUI();
            enableEditAddPanel(false);
        }
        admin.showMessage(res, this);
    }
    public void save(){
        if(!validateForm()){
            return;
        }
        if(act==ADD){
            add();
            return;
        }
        if(act==EDIT){

            edit();
            return;
        }
    }


    public void edit() {
        int ID=admin.getSelectID(tblUser);
        String username=txtUsername.getText();
        String password=txtPassword.getText();
        String role=comboType.getSelectedItem().toString();
        String name=txtName.getText();
        String email=txtEmail.getText();
        int roleUser=admin.parseType(role);
        
        String data[]=new String[]{admin.getSelectID(tblUser)+"", username, password, name, roleUser+"", email};
        int res=admin.editUser(data);
        admin.showMessage(res, this);
        if(res==Manager.SUCCESS){
            initUI();
        }
        enableEditAddPanel(false);
    }

    @Override
    public void deactive() {
        int res=admin.deactiveUser(admin.getSelectID(tblUser));
        admin.showMessage(res, this);
        if(res==Manager.SUCCESS){
            initUI();
        }
    }


    @Override
    public void search(){
        String SQL="SELECT ID, usrn, pswd, name, role, email FROM user WHERE 2>1 ";
        if(!txtNameSearch.getText().isEmpty()){
            SQL+=" AND name like '%"+txtNameSearch.getText()+"%'";
        }
        if(!txtUsernameSearch.getText().isEmpty()){
            SQL+=" AND usrn like '%"+txtUsernameSearch.getText()+"%'";
        }
        if(comboTypeSearch.getSelectedIndex() !=0){
            if(comboTypeSearch.getSelectedItem().equals("Staff manager")){
                SQL+=" AND role=2";
            }else if(comboTypeSearch.getSelectedItem().equals("Student manager")){
                SQL+=" AND role=3";
            }
        }
        admin.loadTable(SQL, tblUser, Manager.USER_MODEL);
        enableButton(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        bannerPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsernameSearch = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNameSearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboTypeSearch = new javax.swing.JComboBox();
        btnSearch = new javax.swing.JButton();
        btnResetSearch = new javax.swing.JButton();
        editAddPanel = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancle = new javax.swing.JButton();
        labelAction = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        actionPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDeactive = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1024, 700));

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Password", "Name", "Role", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(237, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel2.setText("USER");

        jLabel3.setText("Username");

        txtUsernameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAction(evt);
            }
        });

        jLabel4.setText("Name");

        txtNameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAction(evt);
            }
        });

        jLabel5.setText("Role");

        comboTypeSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Staff manager", "Student manager" }));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAction(evt);
            }
        });

        btnResetSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/reset.gif"))); // NOI18N
        btnResetSearch.setText("Reset");
        btnResetSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bannerPanelLayout = new javax.swing.GroupLayout(bannerPanel);
        bannerPanel.setLayout(bannerPanelLayout);
        bannerPanelLayout.setHorizontalGroup(
            bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bannerPanelLayout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bannerPanelLayout.createSequentialGroup()
                        .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsernameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addComponent(jLabel5)
                        .addGap(31, 31, 31)
                        .addComponent(comboTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bannerPanelLayout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(46, 46, 46)
                        .addComponent(btnResetSearch)
                        .addGap(104, 104, 104)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bannerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(461, 461, 461))
        );
        bannerPanelLayout.setVerticalGroup(
            bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bannerPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsernameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(comboTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnResetSearch)))
        );

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

        btnCancle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/cancel.png"))); // NOI18N
        btnCancle.setText("Cancel");
        btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancleActionPerformed(evt);
            }
        });

        labelAction.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        labelAction.setText("Add");

        jLabel1.setText("Username");

        jLabel6.setText("Password");

        jLabel7.setText("Name");

        jLabel8.setText("Type");

        comboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose", "Staff manager", "Student manager" }));

        jLabel9.setText("Email");

        javax.swing.GroupLayout editAddPanelLayout = new javax.swing.GroupLayout(editAddPanel);
        editAddPanel.setLayout(editAddPanelLayout);
        editAddPanelLayout.setHorizontalGroup(
            editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editAddPanelLayout.createSequentialGroup()
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editAddPanelLayout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editAddPanelLayout.createSequentialGroup()
                                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(44, 44, 44)
                                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsername)
                                    .addComponent(txtPassword)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(29, 29, 29)
                                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editAddPanelLayout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addGap(57, 57, 57)
                                .addComponent(btnReset)
                                .addGap(46, 46, 46)
                                .addComponent(btnCancle)
                                .addGap(104, 104, 104))))
                    .addGroup(editAddPanelLayout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(labelAction)))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        editAddPanelLayout.setVerticalGroup(
            editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editAddPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(labelAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset)
                    .addComponent(btnCancle)))
        );

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDeactive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        btnDeactive.setText("Deactive");
        btnDeactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionPanelLayout = new javax.swing.GroupLayout(actionPanel);
        actionPanel.setLayout(actionPanelLayout);
        actionPanelLayout.setHorizontalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(btnAdd)
                .addGap(46, 46, 46)
                .addComponent(btnEdit)
                .addGap(38, 38, 38)
                .addComponent(btnDeactive)
                .addContainerGap(401, Short.MAX_VALUE))
        );
        actionPanelLayout.setVerticalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDeactive))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(actionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editAddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bannerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(bannerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editAddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if(admin.getSelectRow(tblUser) !=null){
            act=EDIT;
            enableEditAddPanel(true);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactiveActionPerformed
        if(admin.confirmRemove(this, "Are you sure ?")==0){
            deactive();
        }
    }//GEN-LAST:event_btnDeactiveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        loadEdit();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancleActionPerformed
        enableEditAddPanel(false);
    }//GEN-LAST:event_btnCancleActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        act=ADD;
        enableEditAddPanel(true);
        btnReset.setVisible(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void searchAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAction
        search();
    }//GEN-LAST:event_searchAction

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        enableButton(true);
    }//GEN-LAST:event_tblUserMouseClicked

    private void btnResetSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSearchActionPerformed
        txtNameSearch.setText("");
        txtUsernameSearch.setText("");
        comboTypeSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JPanel bannerPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancle;
    private javax.swing.JButton btnDeactive;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox comboType;
    private javax.swing.JComboBox comboTypeSearch;
    private javax.swing.JPanel editAddPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAction;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameSearch;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtUsernameSearch;
    // End of variables declaration//GEN-END:variables

    @Override
    public void enableButton(boolean b) {
        btnEdit.setEnabled(b);
        btnDeactive.setEnabled(b);
    }

    @Override
    public boolean validateForm() {
        String username=txtUsername.getText();
        String password=txtPassword.getText();
        String role=comboType.getSelectedItem().toString();
        String name=txtName.getText();
        String email=txtEmail.getText();
        int roleUser=admin.parseType(role);
        
        if(Validate.isEmpty(username)){
            JOptionPane.showMessageDialog(this, "Username is required !");
            return false;
        }
        if(Validate.isEmpty(password)){
            JOptionPane.showMessageDialog(this, "Password is required !");
            return false;
        }
        if(Validate.isEmpty(name)){
            JOptionPane.showMessageDialog(this, "Name is required !");
            return false;
        }
        
        if(!Validate.choosedComboBox(comboType)){
            JOptionPane.showMessageDialog(this, "Role is required !");
            return false;
        }
        
        if(roleUser ==-1){
            JOptionPane.showMessageDialog(this, "Type User incorrect !");
            return false;
        }
        return true;
    }

   
}
