/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.manager.student;

import export.ExportFile;
import controller.Manager;
import controller.Validate;
import entity.ExtraCurricularEntity;
import java.util.Date; 
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import model.ExtraCurricular;
import view.home.UIInterface;

/**
 *
 * @author luan
 */
public class ListExtraCurricular extends javax.swing.JInternalFrame implements UIInterface {
    private ExtraCurricularEntity entity; 
    private ExtraCurricular extraCurricular;
    private int actionType;
    /**
     * Creates new form ListPosition
     */
    public ListExtraCurricular(int w, int h) {
        initComponents();
        this.entity=new ExtraCurricularEntity();
        initUI();
        setSize(w, h);
        
        setSize(w, h);
    }

    @Override
    public void initUI() {
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        enableButton(false);
        editAddPanel.setVisible(false);
        entity.loadList(tblExtraCurricular);
    }

    @Override
    public void search() {
        String name=txtNameSearch.getText();
        if( !Validate.isEmpty(txtYearStartSearch.getText()) && !Validate.isNumber(txtYearStartSearch.getText())){
            JOptionPane.showMessageDialog(this, "Year is incorrect !");
            return;
        }
        if( !Validate.isEmpty(txtYearEndSearch.getText()) && !Validate.isNumber(txtYearEndSearch.getText())){
            JOptionPane.showMessageDialog(this, "Year is incorrect !");
            return;
        }
        int monthStart = 0;
        int monthEnd = 0;
        int yearStart = 0;
        int yearEnd = 0;
        try {
            if (comboMonthStartSearch.getSelectedIndex() != 0) {
                monthStart=Integer.parseInt(comboMonthStartSearch.getSelectedItem().toString());
            }
            if (comboMonthEndSearch.getSelectedIndex() != 0) {
                monthEnd=Integer.parseInt(comboMonthEndSearch.getSelectedItem().toString());
            }
            if(!txtYearStartSearch.getText().isEmpty()){
                yearStart=Integer.parseInt(txtYearStartSearch.getText());
            }
            if(!txtYearEndSearch.getText().isEmpty()){
                yearEnd=Integer.parseInt(txtYearStartSearch.getText());
            }
            entity.search(name, monthStart, yearStart, monthEnd, yearEnd, tblExtraCurricular);
        } catch (NullPointerException | NumberFormatException ex) {}
        enableButton(false);
    }

    @Override
    public void enableEditAddPanel(boolean b) {
        if (b == false) {
            tblExtraCurricular.setEnabled(true);
            txtName.setText(null);

            editAddPanel.setVisible(false);
            bannerPanel.setVisible(true);
            actionPanel.setVisible(true);
        } else {
            if (actionType == EDIT) {
                labelAction.setText("Edit Extra Curricular");
                loadEdit();
            } else if (actionType == ADD) {
                labelAction.setText("Add Extra Curricular");
            }
            tblExtraCurricular.setEnabled(false);
            btnReset.setVisible(true);

            editAddPanel.setVisible(true);
            bannerPanel.setVisible(false);
            actionPanel.setVisible(false);
        }
    }

    @Override
    public void save() {
        if (!validateForm()) {
            return;
        }
        if(extraCurricular==null){
            extraCurricular=new ExtraCurricular();
        }
        String name = txtName.getText();
        String location=txtLocation.getText();
        Date start=null;
        start=dPicStart.getDate();
        Date end=null;
        end=dPicEnd.getDate();
        
        extraCurricular.setName(name);
        extraCurricular.setLocation(location);
        extraCurricular.setStart(start);
        extraCurricular.setEnd(end);
        entity.setExtraCurricular(extraCurricular);
        int res=0;
        res=entity.save();
        if (res == Manager.SUCCESS) {
            initUI();
            enableEditAddPanel(false);
        }
        entity.showMessage(res, this);
        
    }

    @Override
    public void loadEdit() {
        if(actionType==ADD){
            txtName.setText("");
            txtLocation.setText("");
            dPicStart.setDate(null);
            dPicEnd.setDate(null);
        }else{
        extraCurricular=entity.findByID(entity.getSelectID(tblExtraCurricular));
        if(extraCurricular!=null){
            txtName.setText(extraCurricular.getName());
            txtLocation.setText(extraCurricular.getLocation());
            dPicStart.setDate(extraCurricular.getStart());
            dPicEnd.setDate(extraCurricular.getEnd());
        }
        }
    }

    @Override
    public void deactive() {
        int res = 0;
        res=entity.deactive(entity.getSelectID(tblExtraCurricular));
        entity.showMessage(res, this);
        if (res == Manager.SUCCESS) {
            initUI();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExtraCurricular = new javax.swing.JTable();
        actionPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDeactive = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        bannerPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNameSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        comboMonthStartSearch = new javax.swing.JComboBox();
        txtYearStartSearch = new javax.swing.JTextField();
        comboMonthEndSearch = new javax.swing.JComboBox();
        txtYearEndSearch = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnResetSearch = new javax.swing.JButton();
        editAddPanel = new javax.swing.JPanel();
        labelAction = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dPicStart = new com.toedter.calendar.JDateChooser();
        dPicEnd = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(1024, 500));

        tblExtraCurricular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Location", "Start", "End"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblExtraCurricular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExtraCurricularMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblExtraCurricular);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(359, 359, 359))
        );

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDeactive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        btnDeactive.setText("Deactive");
        btnDeactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactiveActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/Export.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionPanelLayout = new javax.swing.GroupLayout(actionPanel);
        actionPanel.setLayout(actionPanelLayout);
        actionPanelLayout.setHorizontalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(btnAdd)
                .addGap(46, 46, 46)
                .addComponent(btnEdit)
                .addGap(38, 38, 38)
                .addComponent(btnDeactive)
                .addGap(46, 46, 46)
                .addComponent(btnExport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        actionPanelLayout.setVerticalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnEdit)
                        .addComponent(btnDeactive))
                    .addComponent(btnExport))
                .addGap(0, 77, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel4.setText("Extra Curricular");

        jLabel3.setText("Name");

        txtNameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameSearchsearchAction(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchsearchAction(evt);
            }
        });

        comboMonthStartSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Month", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        comboMonthEndSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Month", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel7.setText("Start");

        jLabel8.setText("End");

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
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bannerPanelLayout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addComponent(jLabel4))
                    .addGroup(bannerPanelLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bannerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(bannerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMonthStartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bannerPanelLayout.createSequentialGroup()
                                        .addComponent(btnSearch)
                                        .addGap(27, 27, 27)
                                        .addComponent(btnResetSearch))
                                    .addGroup(bannerPanelLayout.createSequentialGroup()
                                        .addComponent(txtYearStartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(82, 82, 82)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboMonthEndSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtYearEndSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(293, Short.MAX_VALUE))
        );
        bannerPanelLayout.setVerticalGroup(
            bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bannerPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMonthStartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYearStartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(comboMonthEndSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYearEndSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(19, 19, 19)
                .addGroup(bannerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnResetSearch)))
        );

        labelAction.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        labelAction.setText("Add");

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

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/cancel.png"))); // NOI18N
        btnCancel.setText("Cancle");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Name");

        jLabel2.setText("Location");

        jLabel5.setText("Start");

        jLabel6.setText("End");

        javax.swing.GroupLayout editAddPanelLayout = new javax.swing.GroupLayout(editAddPanel);
        editAddPanel.setLayout(editAddPanelLayout);
        editAddPanelLayout.setHorizontalGroup(
            editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editAddPanelLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(btnSave)
                .addGap(57, 57, 57)
                .addComponent(btnReset)
                .addGap(46, 46, 46)
                .addComponent(btnCancel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(editAddPanelLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(42, 42, 42)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(35, 35, 35)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dPicStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dPicEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editAddPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelAction)
                .addGap(435, 435, 435))
        );
        editAddPanelLayout.setVerticalGroup(
            editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editAddPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAction)
                .addGap(18, 18, 18)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editAddPanelLayout.createSequentialGroup()
                        .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editAddPanelLayout.createSequentialGroup()
                        .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(dPicStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(dPicEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(editAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset)
                    .addComponent(btnCancel)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editAddPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bannerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 4, Short.MAX_VALUE))
            .addComponent(actionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(bannerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editAddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(actionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        loadEdit();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        enableEditAddPanel(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
            actionType=ADD;
        enableEditAddPanel(true);
        loadEdit();
        btnReset.setVisible(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (entity.getSelectID(tblExtraCurricular) != 0) {
            enableEditAddPanel(true);
            actionType=EDIT;
        }
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactiveActionPerformed
        if (entity.confirmRemove(this, "Are you sure ?") == 0) {
            deactive();
        }
    }//GEN-LAST:event_btnDeactiveActionPerformed

    private void txtNameSearchsearchAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameSearchsearchAction
        search();
    }//GEN-LAST:event_txtNameSearchsearchAction

    private void btnSearchsearchAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchsearchAction
        search();
    }//GEN-LAST:event_btnSearchsearchAction

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblExtraCurricularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExtraCurricularMouseClicked
        enableButton(true);
    }//GEN-LAST:event_tblExtraCurricularMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        export();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnResetSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSearchActionPerformed
        txtNameSearch.setText("");
        comboMonthStartSearch.setSelectedIndex(0);
        comboMonthEndSearch.setSelectedIndex(0);
        txtYearEndSearch.setText("");
        txtYearStartSearch.setText("");
    }//GEN-LAST:event_btnResetSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JPanel bannerPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeactive;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox comboMonthEndSearch;
    private javax.swing.JComboBox comboMonthStartSearch;
    private com.toedter.calendar.JDateChooser dPicEnd;
    private com.toedter.calendar.JDateChooser dPicStart;
    private javax.swing.JPanel editAddPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAction;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblExtraCurricular;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameSearch;
    private javax.swing.JTextField txtYearEndSearch;
    private javax.swing.JTextField txtYearStartSearch;
    // End of variables declaration//GEN-END:variables

    @Override
    public void enableButton(boolean b) { 
        btnEdit.setEnabled(b);
        btnDeactive.setEnabled(b);
    }

    @Override
    public boolean validateForm() {
        String name = txtName.getText();
        if (Validate.isEmpty(name)) {
            JOptionPane.showMessageDialog(this, "Name is required !");
            return false;
        }
        return true;
    }

    private void export() {
        ExportFile export =new ExportFile(tblExtraCurricular, ExportFile.EXTRA_CURRICULAR);
        export.toExcel();
        
    }
}
