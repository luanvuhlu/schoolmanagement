/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.manager.staff;

import export.ExportFile;
import controller.Manager;
import controller.Validate;
import entity.DepartmentEntity;
import entity.PositionEntity;
import entity.StaffEntity;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.FamilyStaff;
import model.Feedback;
import model.Position;
import model.Promotion;
import model.Relative;
import model.Staff;
import view.home.UIInterface;

/**
 *
 * @author luan
 */
public class ListStaff extends javax.swing.JInternalFrame implements UIInterface {
    private StaffEntity entity;
    private DepartmentEntity departmentEntity;
    private PositionEntity positionEntity;
    Staff currentStaff;
    Staff newStaff;
    private int act;

    public ListStaff(int w, int h) {
        this.entity=new StaffEntity();
        this.departmentEntity=new DepartmentEntity();
        this.positionEntity=new PositionEntity();
        initComponents();
        initUI();
        setSize(w, h+30); 
    }

    @Override
    public void initUI() {
        enableButton(false);
        showPanel("List Staff", listPanel);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        entity.loadList(tblStaff);
        List<String> listDepartment=departmentEntity.getNameList();
        comboDepartmentSearch.removeAllItems();
        comboDepartmentSearch.addItem("All");
        for(String str:listDepartment){
            comboDepartmentSearch.addItem(str);
        }
        comboPositionSearch.setEnabled(false);
    }

    @Override
    public void enableEditAddPanel(boolean b) {
    }

    @Override
    public void search() {
        if(!Validate.isEmpty(txtNoSearch.getText()) && !Validate.isNumber(txtNoSearch.getText())){
            JOptionPane.showMessageDialog(this, "No must be a number !");
            return;
            
        }
        int department=0;
        int position=0;
        String name=txtNameSearch.getText();
        int No=0;
        try{
            No=Integer.parseInt(txtNoSearch.getText());
        }catch(NumberFormatException ex){ex.printStackTrace();}
        if(comboDepartmentSearch.getSelectedIndex() !=0){
            try{
            department=departmentEntity.findByName(comboDepartmentSearch.getSelectedItem().toString()).getID();
            }catch(NullPointerException ex){ex.printStackTrace();};
        }
        if(comboPositionSearch.getSelectedIndex() !=0){
            try{
            position=positionEntity.findByName(comboPositionSearch.getSelectedItem().toString(), department).getID();
            }catch(NullPointerException ex){ex.printStackTrace();};
        }
        entity.search(No, name, department, position, tblStaff);
        enableButton(false);
    }


    @Override
    public void deactive() {
        int res=entity.deactive(entity.getSelectID(tblStaff));
        entity.showMessage(res, this);
        if(res==Manager.SUCCESS)
            initUI();
    }



    public void showPanel(String name, JPanel panel) {
        staffPanels.removeAll();
        staffPanels.addTab(name, panel);
        staffPanels.setSelectedComponent(panel);
    }

    public void loadDetail() {
        btnFeedbackDetail.setEnabled(false);
        btnFamilyDetail.setEnabled(false);
        btnPromotionDetail.setEnabled(false);
        
        nameStaff.setText(currentStaff.getName());
        birthdayStaff.setText(entity.formatDate(currentStaff.getBirthday()));
        addressStaff.setText(currentStaff.getAddress());
        educationStaff.setText(currentStaff.getEducation());
        emailStaff.setText(currentStaff.getEmail());
        experienceStaff.setText(currentStaff.getExperience());
        labelGender.setText(entity.parseGender(currentStaff.getGender()));
        if (currentStaff.getFamilyStaff() != null) {
            labelMarried.setText(entity.parseMarried(currentStaff.getFamilyStaff().getMarried()));
        }
        phoneStaff.setText(currentStaff.getPhone());

        loadPromotionTable(tblPromotionDetail);
        loadRelationsTable(tblRelationDetail);
        loadFeedbackTable(tblFeedbackDetail);

    }

    @Override
    public void loadEdit() {
        newStaff=currentStaff;
        btnPromotionDelete.setEnabled(false);
        btnPromotionEdit.setEnabled(false);
        btnRelationDelete.setEnabled(false);
        btnRelationEdit.setEnabled(false);
        btnFeedbackDelete.setEnabled(false);
        btnFeedbackEdit.setEnabled(false);
        
        txtName.setText(currentStaff.getName());
        txtPhone.setText(currentStaff.getPhone());
        txtAddress.setText(currentStaff.getAddress());
        dPicBirthday.setDate(currentStaff.getBirthday());
        txtEducation.setText(currentStaff.getEducation());
        txtExperience.setText(currentStaff.getExperience());
        txtEmail.setText(currentStaff.getEmail());
        comboGender.setSelectedItem(entity.parseGender(currentStaff.getGender()));
        if (currentStaff.getFamilyStaff() != null) {
            comboMarried.setSelectedItem(entity.parseMarried(currentStaff.getFamilyStaff().getMarried()));
        }

        loadPromotionTable(tblPromotion);
        loadRelationsTable(tblRelation);
        loadFeedbackTable(tblFeedback);
    }

    private void loadAdd() {
        
        btnPromotionDelete.setEnabled(false);
        btnPromotionEdit.setEnabled(false);
        btnRelationDelete.setEnabled(false);
        btnRelationEdit.setEnabled(false);
        btnFeedbackDelete.setEnabled(false);
        btnFeedbackEdit.setEnabled(false);
        
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
//        txtBirthday.setText("");
        dPicBirthday.setDate(null);
        txtEducation.setText("");
        txtExperience.setText("");
        txtEmail.setText("");
        entity.refreshTable(tblPromotion);
        entity.refreshTable(tblRelation);
        entity.refreshTable(tblFeedback);

    }

    private void loadPromotionTable(JTable tbl) {
        if (currentStaff.getPromotions() == null) {
            return;
        }
        List<Promotion> listPromotion = currentStaff.getPromotions();
        String[][] data = new String[listPromotion.size()][4];
        int count = 0;
        for (Promotion promotion : listPromotion) {
            Position position = promotion.getPosition();
            data[count++] = new String[]{
                promotion.getID() + "",
                position.getName(),
                position.getDepartment().getName(),
                entity.formatDate(promotion.getTime())
            };
        }
        entity.loadTable(data, tbl);
    }

    private void loadRelationsTable(JTable tbl) {
        if (currentStaff.getFamilyStaff() == null || currentStaff.getFamilyStaff().getRelatives() == null) {
            return;
        }
        int count = 0;
        List<Relative> listreRelatives = currentStaff.getFamilyStaff().getRelatives();
        String[][] data = new String[listreRelatives.size()][7];
        for (Relative r : listreRelatives) {
            data[count++] = new String[]{
                r.getID() + "",
                r.getRelation(),
                r.getName(),
                r.getPhone(),
                r.getAddress(),
                r.getOccupation(),
                entity.parseGender(r.getGender())
            };
        }
        entity.loadTable(data, tbl);
    }

    private void loadFeedbackTable(JTable tbl) {
        if (currentStaff.getFeedbacks() == null || currentStaff.getFeedbacks().size() < 1) {
            return;
        }
        int count = 0;
        List<Feedback> feedbacks = currentStaff.getFeedbacks();
        String[][] data = new String[feedbacks.size()][5];
        for (Feedback f : feedbacks) {
            data[count++] = new String[]{
                f.getID() + "",
                f.getStudent().getNo(),
                f.getStudent().getName(),
                f.getContent(),
                entity.formatDate(f.getTime()),
                f.getStatus()+""
            };
        }
        entity.loadTable(data, tbl);

    }

    private void loadPromotionInfo(int mode) {
        if (act == DETAIL) {
            new PromotionDetail(null, true, currentStaff.getPromotions().get(tblPromotionDetail.getSelectedRow())).setVisible(true);
            return;
        }
        PromotionUpdate promotionUpdate = null;
        if (mode == ADD) {
            promotionUpdate = new PromotionUpdate(null, true, null);
            promotionUpdate.setVisible(true);
        } else if (mode == EDIT) {
            promotionUpdate = new PromotionUpdate(null, true, entity.getSelectRow(tblPromotion));
            promotionUpdate.setVisible(true);
            if (promotionUpdate.getPromotion() != null) {
                removeSelectedPromotion();
            }
        }
        if (promotionUpdate.getPromotion() != null) {
            addRowTablePromotion(promotionUpdate.getPromotion());
            newStaff.addPromotion(promotionUpdate.getPromotion());
        }

    }

    private void addRowTablePromotion(Promotion promotion) {
        Vector row = new Vector();
        row.add(promotion.getID());
        row.add(promotion.getPosition().getName());
        row.add(promotion.getPosition().getDepartment().getName());
        row.add(entity.formatDate(promotion.getTime()));
        DefaultTableModel model = (DefaultTableModel) tblPromotion.getModel();
        model.addRow(row);
    }

    private void removeSelectedPromotion() {
        Vector row = entity.getSelectRow(tblPromotion);
        int ID=0;
        try{
            ID=Integer.parseInt(row.get(0).toString());
        }catch(NumberFormatException | NullPointerException ex){
            ex.printStackTrace();
        }
        if(ID >0){
            entity.removePromotion(ID);
        }
        newStaff.removePromotion(entity.parseDate((String) row.get(3)));
        entity.removeSelectedRowTable(tblPromotion);
        
        
    }

    private void loadRelationInfo(int mode) {
        if (act == DETAIL) {
            new RelationDetail(null, true, currentStaff.getFamilyStaff().getRelatives().get(tblRelationDetail.getSelectedRow())).setVisible(true);
            return;
        }
        RelationUpdate relationUpdate = null;
        if (mode == ADD) {
            relationUpdate = new RelationUpdate(null, true, entity, null);
            relationUpdate.setVisible(true);
        } else if (mode == EDIT) {
            relationUpdate = new RelationUpdate(null, true, entity, entity.getSelectRow(tblRelation));
            relationUpdate.setVisible(true);
            if (relationUpdate.getRelaRelatives() != null) {
                removeSelectedRelation();
            }
        }
        if (relationUpdate.getRelaRelatives() != null) {
            addRowTableRelation(relationUpdate.getRelaRelatives());
            newStaff.getFamilyStaff().addRelative(relationUpdate.getRelaRelatives());
        }
    }

    private void addRowTableRelation(Relative r) {
        Vector row = new Vector();
        row.add(r.getID());
        row.add(r.getRelation());
        row.add(r.getName());
        row.add(r.getPhone());
        row.add(r.getAddress());
        row.add(r.getOccupation());
        row.add(entity.parseGender(r.getGender()));
        DefaultTableModel model = (DefaultTableModel) tblRelation.getModel();
        model.addRow(row);
    }

    private void removeSelectedRelation() {
        Vector row = entity.getSelectRow(tblRelation);
        int ID=0;
        try{
            ID=Integer.parseInt(row.get(0).toString());
        }catch(NumberFormatException | NullPointerException ex){
            ex.printStackTrace();
        }
        if(ID >0){
            entity.removeRelative(ID);
        }
        newStaff.getFamilyStaff().removeRelative((String) row.get(1), (String) row.get(2));
        entity.removeSelectedRowTable(tblRelation);
    }
    private void loadFeedbackInfo(int mode) {
        if (act == DETAIL) {
            new FeedbackDetail(null, true, currentStaff.getFeedbacks().get(tblFeedbackDetail.getSelectedRow())).setVisible(true);
            return;
        }
        FeedbackUpdate feedbackUpdate = null;
        if (mode == ADD) {
            feedbackUpdate = new FeedbackUpdate(null, true, null);
            feedbackUpdate.setVisible(true);
        } else if (mode == EDIT) {
            feedbackUpdate = new FeedbackUpdate(null, true, entity.getSelectRow(tblFeedback));
            feedbackUpdate.setVisible(true);
            if (feedbackUpdate.getFeedback()!= null) {
                removeSelectedFeedback();
            }
        }
        if (feedbackUpdate.getFeedback()!= null) {
            addRowTableFeedback(feedbackUpdate.getFeedback());
            newStaff.addFeedback(feedbackUpdate.getFeedback());
        }
    }

    private void addRowTableFeedback(Feedback f) {
        Vector row = new Vector();
        row.add(f.getID());
        row.add(f.getStudent().getNo());
        row.add(f.getStudent().getName());
        row.add(f.getContent());
        row.add(entity.formatDate(f.getTime()));
        row.add(f.getStatus());
        DefaultTableModel model = (DefaultTableModel) tblFeedback.getModel();
        model.addRow(row);
    }

    private void removeSelectedFeedback() {
        Vector row = entity.getSelectRow(tblFeedback);
        int ID=0;
        try{
            ID=Integer.parseInt(row.get(0).toString());
        }catch(NumberFormatException | NullPointerException ex){
            ex.printStackTrace();
        }
        if(ID >0){
            entity.removeFeedback(ID);
        }
        newStaff.removeFeedback(row.get(1).toString(), entity.parseDate(row.get(4).toString()));
        entity.removeSelectedRowTable(tblFeedback);
    }

    @Override
    public void save() {
        if(!validateForm()){
            return;
        }
        newStaff.setName(txtName.getText());
        newStaff.setAddress(txtAddress.getText());
        newStaff.setEducation(txtEducation.getText());
        newStaff.setEmail(txtEmail.getText());
        newStaff.setExperience(txtExperience.getText());
        newStaff.setGender(entity.formatGender((String) comboGender.getSelectedItem()));
        newStaff.setPhone(txtPhone.getText());
        newStaff.setBirthday(dPicBirthday.getDate());
//        newStaff.setBirthday(entity.parseDate(txtBirthday.getText()));
        if (newStaff.getFamilyStaff() == null) {
            newStaff.setFamilyStaff(new FamilyStaff());
        }
        newStaff.getFamilyStaff().setMaried(entity.formatMarried((String) comboMarried.getSelectedItem()));
        
        entity.setStaff(newStaff);
        int res=entity.save();
        if(res==Manager.SUCCESS){
            initUI();
        staffPanels.setSelectedComponent(listPanel);
        }
        entity.showMessage(res, this);
        
    }

    private void reset() {
        newStaff = currentStaff;
        if (act == EDIT) {
            entity.refreshTable(tblPromotion);
            entity.refreshTable(tblRelation);
            entity.refreshTable(tblFeedback);
            loadEdit();
        } else if (act == ADD) {
            loadAdd();
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

        staffPanels = new javax.swing.JTabbedPane();
        listPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDeactive = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        comboPositionSearch = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        comboDepartmentSearch = new javax.swing.JComboBox();
        btnSearch = new javax.swing.JButton();
        txtNameSearch = new javax.swing.JTextField();
        txtNoSearch = new javax.swing.JTextField();
        btnExport = new javax.swing.JButton();
        btnResetSearch = new javax.swing.JButton();
        detailPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblRelationDetail = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        btnEdit1 = new javax.swing.JButton();
        nameStaff = new javax.swing.JLabel();
        phoneStaff = new javax.swing.JLabel();
        emailStaff = new javax.swing.JLabel();
        birthdayStaff = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        addressStaff = new javax.swing.JTextPane();
        labelGender = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        educationStaff = new javax.swing.JTextPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        experienceStaff = new javax.swing.JTextPane();
        labelMarried = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblPromotionDetail = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        btnFeedbackDetail = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        tblFeedbackDetail = new javax.swing.JTable();
        btnPromotionDetail = new javax.swing.JButton();
        btnFamilyDetail = new javax.swing.JButton();
        btnViewList = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        updatePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        comboGender = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtEducation = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtExperience = new javax.swing.JTextArea();
        labelAction = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPromotion = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboMarried = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        btnRelationAdd = new javax.swing.JButton();
        btnRelationDelete = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblRelation = new javax.swing.JTable();
        btnRelationEdit = new javax.swing.JButton();
        btnPromotionAdd = new javax.swing.JButton();
        btnPromotionEdit = new javax.swing.JButton();
        btnPromotionDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane22 = new javax.swing.JScrollPane();
        tblFeedback = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        btnFeedbackAdd = new javax.swing.JButton();
        btnFeedbackEdit = new javax.swing.JButton();
        btnFeedbackDelete = new javax.swing.JButton();
        dPicBirthday = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(0, 0));

        tblStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Name", "Phone", "Email", "Position", "Department", "Gender"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStaffMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStaff);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnDetail.setText("Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
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

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel9.setText("STAFF");

        jLabel10.setText("No");

        jLabel42.setText("Name");

        jLabel43.setText("Position");

        jLabel44.setText("Department");

        comboDepartmentSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDepartmentSearchActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/Export.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnResetSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/reset.gif"))); // NOI18N
        btnResetSearch.setText("Reset");
        btnResetSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel10))
                        .addGap(26, 26, 26)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(comboPositionSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(listPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboDepartmentSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(listPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel43)))
                        .addGap(139, 139, 139))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(31, 31, 31)
                        .addComponent(btnResetSearch)
                        .addGap(356, 356, 356))))
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(jLabel9))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(btnAdd)
                        .addGap(30, 30, 30)
                        .addComponent(btnDetail)
                        .addGap(34, 34, 34)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeactive)
                        .addGap(18, 18, 18)
                        .addComponent(btnExport)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel9)
                .addGap(28, 28, 28)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(comboDepartmentSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboPositionSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addGap(57, 57, 57))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)))
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnResetSearch))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDetail)
                    .addComponent(btnEdit)
                    .addComponent(btnDeactive)
                    .addComponent(btnExport))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        staffPanels.addTab("List", listPanel);

        detailPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setText("Name");
        detailPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel24.setText("Phone");
        detailPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 112, -1, -1));

        jLabel25.setText("Email");
        detailPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 133, -1, -1));

        jLabel26.setText("Birthday");
        detailPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel27.setText("Address");
        detailPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 211, -1, -1));

        jLabel34.setText("Gender");
        detailPanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 315, -1, -1));

        jLabel35.setText("Education");
        detailPanel.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 348, -1, -1));

        jLabel36.setText("Experience");
        detailPanel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 472, -1, -1));

        jLabel37.setText("Promotion");
        detailPanel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 71, -1, -1));

        jLabel38.setText("Family Infomation");
        detailPanel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 231, -1, -1));

        jLabel39.setText("Married");
        detailPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 260, -1, -1));

        jLabel40.setText("Relatives");
        detailPanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 289, -1, -1));

        tblRelationDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Relation", "Name", "Phone", "Address", "Occupation", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRelationDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelationDetailMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(tblRelationDetail);

        detailPanel.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 318, 420, 75));

        jLabel41.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel41.setText("STAFF DETAIL");
        detailPanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 0, -1, -1));

        btnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEdit1.setText("Edit");
        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });
        detailPanel.add(btnEdit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 560, 100, -1));

        nameStaff.setText("                           ");
        detailPanel.add(nameStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));

        phoneStaff.setText("                         ");
        detailPanel.add(phoneStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 112, -1, -1));

        emailStaff.setText("                           ");
        detailPanel.add(emailStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 141, -1, -1));

        birthdayStaff.setText("                              ");
        detailPanel.add(birthdayStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 176, -1, -1));

        addressStaff.setEditable(false);
        jScrollPane10.setViewportView(addressStaff);

        detailPanel.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 211, 237, 86));

        labelGender.setText("                             ");
        detailPanel.add(labelGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 315, -1, -1));

        educationStaff.setEditable(false);
        jScrollPane14.setViewportView(educationStaff);

        detailPanel.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 348, 237, 84));

        experienceStaff.setEditable(false);
        jScrollPane15.setViewportView(experienceStaff);

        detailPanel.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 472, 237, 60));

        labelMarried.setText("                           ");
        detailPanel.add(labelMarried, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 231, -1, -1));

        tblPromotionDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Position name", "Department name", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPromotionDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPromotionDetailMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(tblPromotionDetail);

        detailPanel.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 112, 420, 101));

        jLabel48.setText("Feedback");
        detailPanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 411, -1, -1));

        btnFeedbackDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnFeedbackDetail.setText("Detail");
        btnFeedbackDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnFeedbackDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 405, -1, -1));

        tblFeedbackDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "No", "Student name", "Content", "Time", "Status"
            }
        ));
        tblFeedbackDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFeedbackDetailMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(tblFeedbackDetail);

        detailPanel.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 446, 420, 80));

        btnPromotionDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnPromotionDetail.setText("Detail");
        btnPromotionDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromotionDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnPromotionDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

        btnFamilyDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnFamilyDetail.setText("Detail");
        btnFamilyDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFamilyDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnFamilyDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, -1, -1));

        btnViewList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        btnViewList.setText("View List");
        btnViewList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewListActionPerformed(evt);
            }
        });
        detailPanel.add(btnViewList, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 560, -1, -1));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        jButton8.setText("Add new");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        detailPanel.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 560, -1, -1));

        staffPanels.addTab("Detail", detailPanel);

        updatePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Name");
        updatePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 85, -1, -1));

        jLabel2.setText("Phone");
        updatePanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 124, -1, -1));

        jLabel3.setText("Email");
        updatePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 169, -1, -1));

        jLabel4.setText("Birthday");
        updatePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 214, -1, -1));

        jLabel5.setText("Address");
        updatePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 242, -1, -1));

        jLabel6.setText("Gender");
        updatePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel7.setText("Education");
        updatePanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 384, -1, -1));

        jLabel8.setText("Experience");
        updatePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 477, -1, -1));
        updatePanel.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 80, 225, -1));
        updatePanel.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 119, 225, -1));
        updatePanel.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 164, 225, -1));

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane3.setViewportView(txtAddress);

        updatePanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 242, -1, 91));

        comboGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));
        updatePanel.add(comboGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 345, -1, -1));

        txtEducation.setColumns(20);
        txtEducation.setRows(5);
        jScrollPane4.setViewportView(txtEducation);

        updatePanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 384, -1, 70));

        txtExperience.setColumns(20);
        txtExperience.setRows(5);
        jScrollPane5.setViewportView(txtExperience);

        updatePanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 477, -1, 74));

        labelAction.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        labelAction.setText("ADD STAFF");
        updatePanel.add(labelAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 0, -1, -1));

        tblPromotion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Position name", "Department name", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPromotionMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblPromotion);

        updatePanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 121, 350, 101));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        updatePanel.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 560, -1, -1));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/reset.gif"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        updatePanel.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 560, -1, -1));

        jLabel12.setText("Family Infomation");
        updatePanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 228, -1, -1));

        jLabel13.setText("Maried");
        updatePanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 256, -1, -1));

        comboMarried.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Married", "Single" }));
        updatePanel.add(comboMarried, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 251, -1, -1));

        jLabel14.setText("Relatives");
        updatePanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 290, -1, -1));

        btnRelationAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnRelationAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelationAddActionPerformed(evt);
            }
        });
        updatePanel.add(btnRelationAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, -1, -1));

        btnRelationDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnRelationDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelationDeleteActionPerformed(evt);
            }
        });
        updatePanel.add(btnRelationDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 280, -1, -1));

        jLabel29.setText("Promotion");
        updatePanel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 86, -1, -1));

        tblRelation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Relation", "Name", "Phone", "Address", "Occupation", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRelation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelationMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblRelation);

        updatePanel.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 325, 350, 87));

        btnRelationEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnRelationEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelationEditActionPerformed(evt);
            }
        });
        updatePanel.add(btnRelationEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, -1, -1));

        btnPromotionAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnPromotionAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromotionAddActionPerformed(evt);
            }
        });
        updatePanel.add(btnPromotionAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        btnPromotionEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnPromotionEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromotionEditActionPerformed(evt);
            }
        });
        updatePanel.add(btnPromotionEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, -1, -1));

        btnPromotionDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnPromotionDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromotionDeleteActionPerformed(evt);
            }
        });
        updatePanel.add(btnPromotionDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 80, -1, -1));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelbtnCancelActionPerformed(evt);
            }
        });
        updatePanel.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 560, -1, -1));

        tblFeedback.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student register no", "Student name", "Content", "Time", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFeedback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFeedbackMouseClicked(evt);
            }
        });
        jScrollPane22.setViewportView(tblFeedback);

        updatePanel.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 459, 350, 90));

        jLabel49.setText("Feedback");
        updatePanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 424, -1, -1));

        btnFeedbackAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnFeedbackAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackAddActionPerformed(evt);
            }
        });
        updatePanel.add(btnFeedbackAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 420, -1, -1));

        btnFeedbackEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnFeedbackEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackEditActionPerformed(evt);
            }
        });
        updatePanel.add(btnFeedbackEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 420, -1, -1));

        btnFeedbackDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnFeedbackDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackDeleteActionPerformed(evt);
            }
        });
        updatePanel.add(btnFeedbackDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 420, -1, -1));
        updatePanel.add(dPicBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 150, -1));

        staffPanels.addTab("Update", updatePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(staffPanels)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(staffPanels, javax.swing.GroupLayout.PREFERRED_SIZE, 611, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        act = ADD;
        newStaff = new Staff();
        showPanel("Add Staff", updatePanel);
        loadAdd();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        act = DETAIL;
        currentStaff = entity.findByID(entity.getSelectID(tblStaff));
        showPanel("Staff Detail", detailPanel);
        loadDetail();
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        act = EDIT;
        currentStaff = entity.findByID(entity.getSelectID(tblStaff));
        if (currentStaff == null) {
            return;
        }
        newStaff = currentStaff;
        loadEdit();
        showPanel("Edit Staff", updatePanel);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactiveActionPerformed
        deactive();
    }//GEN-LAST:event_btnDeactiveActionPerformed

    private void btnCancelbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelbtnCancelActionPerformed
        newStaff = null;
        showPanel("List Staff", listPanel);
    }//GEN-LAST:event_btnCancelbtnCancelActionPerformed

    private void btnPromotionDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromotionDetailActionPerformed
        loadPromotionInfo(DETAIL);
    }//GEN-LAST:event_btnPromotionDetailActionPerformed

    private void btnFeedbackDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackDetailActionPerformed
        loadFeedbackInfo(DETAIL);
    }//GEN-LAST:event_btnFeedbackDetailActionPerformed

    private void btnFamilyDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFamilyDetailActionPerformed
        loadRelationInfo(DETAIL);
    }//GEN-LAST:event_btnFamilyDetailActionPerformed

    private void tblFeedbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFeedbackMouseClicked
        btnFeedbackDelete.setEnabled(true);
        btnFeedbackEdit.setEnabled(true);
    }//GEN-LAST:event_tblFeedbackMouseClicked

    private void btnRelationEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelationEditActionPerformed
        loadRelationInfo(EDIT);
    }//GEN-LAST:event_btnRelationEditActionPerformed

    private void btnRelationAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelationAddActionPerformed
        loadRelationInfo(ADD);
    }//GEN-LAST:event_btnRelationAddActionPerformed

    private void btnPromotionAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromotionAddActionPerformed
        loadPromotionInfo(ADD);
    }//GEN-LAST:event_btnPromotionAddActionPerformed

    private void btnPromotionEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromotionEditActionPerformed
        loadPromotionInfo(EDIT);
    }//GEN-LAST:event_btnPromotionEditActionPerformed

    private void btnPromotionDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromotionDeleteActionPerformed
        removeSelectedPromotion();
    }//GEN-LAST:event_btnPromotionDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRelationDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelationDeleteActionPerformed
        removeSelectedRelation();
    }//GEN-LAST:event_btnRelationDeleteActionPerformed

    private void btnViewListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewListActionPerformed
        showPanel("List Staff", listPanel);
    }//GEN-LAST:event_btnViewListActionPerformed

    private void tblStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStaffMouseClicked
//        currentStaff = entity.getDetailStaff(entity.getSelectID(tblStaff));
        enableButton(true);
    }//GEN-LAST:event_tblStaffMouseClicked

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        act=EDIT;
        loadEdit();
        showPanel("Edit Staff", updatePanel);
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void tblPromotionDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPromotionDetailMouseClicked
        btnPromotionDetail.setEnabled(true);
    }//GEN-LAST:event_tblPromotionDetailMouseClicked

    private void tblRelationDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelationDetailMouseClicked
        btnFamilyDetail.setEnabled(true);
    }//GEN-LAST:event_tblRelationDetailMouseClicked

    private void tblFeedbackDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFeedbackDetailMouseClicked
        btnFeedbackDetail.setEnabled(true);
    }//GEN-LAST:event_tblFeedbackDetailMouseClicked

    private void tblPromotionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPromotionMouseClicked
        btnPromotionDelete.setEnabled(true);
        btnPromotionEdit.setEnabled(true);
    }//GEN-LAST:event_tblPromotionMouseClicked

    private void tblRelationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelationMouseClicked
        btnRelationDelete.setEnabled(true);
        btnRelationEdit.setEnabled(true);
    }//GEN-LAST:event_tblRelationMouseClicked

    private void btnFeedbackAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackAddActionPerformed
        loadFeedbackInfo(ADD);
    }//GEN-LAST:event_btnFeedbackAddActionPerformed

    private void btnFeedbackEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackEditActionPerformed
       loadFeedbackInfo(EDIT);
    }//GEN-LAST:event_btnFeedbackEditActionPerformed

    private void btnFeedbackDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackDeleteActionPerformed
        removeSelectedFeedback();
    }//GEN-LAST:event_btnFeedbackDeleteActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        export();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnResetSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSearchActionPerformed
        txtNoSearch.setText("");
        txtNameSearch.setText("");
       
        comboDepartmentSearch.setSelectedIndex(0);

        comboPositionSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void comboDepartmentSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDepartmentSearchActionPerformed
        if(comboDepartmentSearch.getSelectedIndex() >0){
        comboPositionSearch.setEnabled(true);
        comboPositionSearch.removeAllItems();
        comboPositionSearch.addItem("All");
        List<String> listPos=positionEntity.getNameList(comboDepartmentSearch.getSelectedItem().toString());
        for(String str:listPos){
            comboPositionSearch.addItem(str);
        }
        }
    }//GEN-LAST:event_comboDepartmentSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane addressStaff;
    private javax.swing.JLabel birthdayStaff;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeactive;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnFamilyDetail;
    private javax.swing.JButton btnFeedbackAdd;
    private javax.swing.JButton btnFeedbackDelete;
    private javax.swing.JButton btnFeedbackDetail;
    private javax.swing.JButton btnFeedbackEdit;
    private javax.swing.JButton btnPromotionAdd;
    private javax.swing.JButton btnPromotionDelete;
    private javax.swing.JButton btnPromotionDetail;
    private javax.swing.JButton btnPromotionEdit;
    private javax.swing.JButton btnRelationAdd;
    private javax.swing.JButton btnRelationDelete;
    private javax.swing.JButton btnRelationEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewList;
    private javax.swing.JComboBox comboDepartmentSearch;
    private javax.swing.JComboBox comboGender;
    private javax.swing.JComboBox comboMarried;
    private javax.swing.JComboBox comboPositionSearch;
    private com.toedter.calendar.JDateChooser dPicBirthday;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JTextPane educationStaff;
    private javax.swing.JLabel emailStaff;
    private javax.swing.JTextPane experienceStaff;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelAction;
    private javax.swing.JLabel labelGender;
    private javax.swing.JLabel labelMarried;
    private javax.swing.JPanel listPanel;
    private javax.swing.JLabel nameStaff;
    private javax.swing.JLabel phoneStaff;
    private javax.swing.JTabbedPane staffPanels;
    private javax.swing.JTable tblFeedback;
    private javax.swing.JTable tblFeedbackDetail;
    private javax.swing.JTable tblPromotion;
    private javax.swing.JTable tblPromotionDetail;
    private javax.swing.JTable tblRelation;
    private javax.swing.JTable tblRelationDetail;
    private javax.swing.JTable tblStaff;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextArea txtEducation;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtExperience;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameSearch;
    private javax.swing.JTextField txtNoSearch;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JPanel updatePanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void enableButton(boolean b) {
        btnDetail.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDeactive.setEnabled(b);
    }

    @Override
    public boolean validateForm() {
        return true;
    }

    private void export() {
        ExportFile export=new ExportFile(tblStaff, ExportFile.STAFF);
        export.toExcel();
    }
}
