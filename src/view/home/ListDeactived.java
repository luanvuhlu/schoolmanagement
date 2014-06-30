/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.home;

import controller.GeneralManager;
import controller.Manager;
import entity.BatchEntity;
import entity.DepartmentEntity;
import entity.ExtraCurricularEntity;
import entity.PositionEntity;
import entity.StaffEntity;
import entity.StudentEntity;
import entity.SubjectEntity;
import entity.UserEntity;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author luan
 */
public class ListDeactived extends javax.swing.JInternalFrame {

    private int type;
    private String SQL;
    private GeneralManager manager;
    private String header;

    public ListDeactived(int type) {
        this.manager = new GeneralManager();
        this.type = type;
        this.SQL = "";
        initComponents();
        initUI();
        enableButton(false);
    }

    public void initUI() {
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        switch (type) {
            case Manager.USER:
                header = "User";
                SQL = "SELECT ID, name FROM user where status=0";
                break;
            case Manager.DEPARTMENT:
                header = "Department";
                SQL = "SELECT ID, name FROM department where status=0";
                break;
            case Manager.BATCH:
                header = "Batch";
                SQL = "SELECT ID, name FROM batch where status=0";
                break;
            case Manager.EXTRA_CURRICULAR:
                header = "Extra Curricular";
                SQL = "SELECT ID, name FROM extra_curricular where status=0";
                break;
            case Manager.SUBJECT:
                header = "Subject";
                SQL = "SELECT ID, name FROM subject where status=0";
                break;
            case Manager.STUDENT:
                header = "Student";
                SQL = "SELECT ID, name FROM student where status=0";
                break;
            case Manager.STAFF:
                header = "Staff";
                SQL = "SELECT No, name FROM staff where status=0";
                break;
            case Manager.POSITION:
                header = "Position";
                SQL = "SELECT ID, name FROM position where status=0";
                break;
            default:
                break;
        }
        labelTitle.setText(header);
        manager.loadTable(SQL, tblList, new String[]{"ID", "Name"});
    }

    private void search(int ID, String name) {
        String query = SQL;
        if (ID != 0) {
            if (type == Manager.STAFF) {
                query += " AND No=" + ID;
            } else {
                query += " AND ID=" + ID;
            }
        }
        if (name != null && !name.isEmpty()) {
            query += " AND name like '%" + name + "%'";
        }
        manager.loadTable(query, tblList, new String[]{"ID", "Name"});
        enableButton(false);
    }

    private void remove() {
        int ID = manager.getSelectID(tblList);
        int res = 0;
        String query = "";
        switch (type) {
            case Manager.USER:
                query = "DELETE FROM user";
                break;
            case Manager.DEPARTMENT:
                System.out.println("nbull");
                res = new DepartmentEntity().remove(ID);
                break;
            case Manager.BATCH:
                res = new BatchEntity().remove(ID);
                break;
            case Manager.EXTRA_CURRICULAR:
                res = new ExtraCurricularEntity().remove(ID);
                break;
            case Manager.SUBJECT:
                res = new SubjectEntity().remove(ID);
                break;
            case Manager.STUDENT:
                res = new StudentEntity().remove(ID);
                break;
            case Manager.POSITION:
                res = new PositionEntity().remove(ID);
                break;
            case Manager.STAFF:
                res = new StaffEntity().remove(ID);
                break;
            default:
                break;
        }
        manager.showMessage(res, this);
        if (res == Manager.SUCCESS) {
            initUI();
        }
    }

    private void active() {
        int ID = manager.getSelectID(tblList);
        int res = 0;
        switch (type) {
            case Manager.USER:
                res = new UserEntity().active(ID);
                break;
            case Manager.DEPARTMENT:
                res = new DepartmentEntity().active(ID);
                break;
            case Manager.BATCH:
                res = new BatchEntity().active(ID);
                break;
            case Manager.EXTRA_CURRICULAR:
                res = new ExtraCurricularEntity().active(ID);
                break;
            case Manager.SUBJECT:
                res = new SubjectEntity().active(ID);
                break;
            case Manager.STUDENT:
                res = new StudentEntity().active(ID);
                break;
            case Manager.POSITION:
                res = new PositionEntity().active(ID);
                break;
            case Manager.STAFF:
                res = new StaffEntity().active(ID);
                break;
            default:
                break;
        }
        manager.showMessage(res, this);
        if (res == Manager.SUCCESS) {
            initUI();
        }
    }

    private void enableButton(boolean b) {
        btnRemove.setEnabled(b);
        btnActive.setEnabled(b);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnActive = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1024, 700));

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/unlock.png"))); // NOI18N
        btnActive.setText("Active");
        btnActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(403, 403, 403)
                .addComponent(btnRemove)
                .addGap(53, 53, 53)
                .addComponent(btnActive)
                .addContainerGap(425, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActive)
                    .addComponent(btnRemove))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        labelTitle.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        labelTitle.setText("Title");

        jLabel5.setText("ID");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel6.setText("Name");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(45, 45, 45)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(449, 449, 449)
                .addComponent(labelTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(458, 458, 458)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        int ID = 0;
        try {
            ID = Integer.parseInt(txtID.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        search(ID, txtName.getText());
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        enableButton(true);
    }//GEN-LAST:event_tblListMouseClicked

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure ?");
        if (confirm == 0) {
            remove();
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActiveActionPerformed
        active();
    }//GEN-LAST:event_btnActiveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActive;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
