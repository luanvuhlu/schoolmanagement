/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.manager.student;

import export.ExportFile;
import controller.Manager;
import controller.StudentManager;
import controller.Validate;
import entity.ExtraCurricularEntity;
import entity.StudentEntity;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Admission;
import model.Batch;
import model.ExtraCurricular;
import model.FamilyStudent;
import model.Student;
import model.Mark;
import model.Relative;
import model.Remark;
import view.home.UIInterface;
import view.manager.staff.RelationDetail;
import view.manager.staff.RelationUpdate;

/**
 *
 * @author luan
 */
public class ListStudent extends javax.swing.JInternalFrame implements UIInterface{
    StudentManager studentManager=new StudentManager();
    private ExtraCurricularEntity extraCurricularEntity;
    private StudentEntity entity;
    private int act;
    private int currentPage=1;
    private int totalPage=3; 
    Student currenStudent;
    Student newStudent;
    /**
     * Creates new form ListStudent
     */
    public ListStudent() {
        initComponents();
    }
    public ListStudent(int w, int h ) {
        this.extraCurricularEntity=new ExtraCurricularEntity();
        this.entity=new StudentEntity();
        initComponents();
        initUI();
        setSize(w, h+30);
    }
    public void initUI(){
        enableButton(false);
        showPanel("List Student", listPanel);
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        studentManager.loadCombobox("SELECT name FROM batch", comboBatchSearch);
        studentManager.loadTable(studentManager.getStudentList(currentPage), tblStudent, Manager.STUDENT_MODEL);
    }
    public void showPanel(String name, JPanel panel) {
        studentPanel.removeAll();
        studentPanel.addTab(name, panel);
        studentPanel.setSelectedComponent(panel);
    }
    @Override
    public void enableEditAddPanel(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void add() {
        int res = studentManager.addStudent(newStudent);
        studentManager.showMessage(res, this);
    }


    public void edit() {
        int res = studentManager.editStudent(newStudent, currenStudent);
        studentManager.showMessage(res, this);
    }

    @Override
    public void deactive() {
        int res=studentManager.deactiveStudent(studentManager.getSelectID(tblStudent));
        entity.showMessage(res, this);
        if(res==Manager.SUCCESS){
            initUI();
        }
    }
    private void reset(){
        newStudent = currenStudent;
        if (act == EDIT) {
            studentManager.refreshTable(tblMark);
            studentManager.refreshTable(tblRelation);
            studentManager.refreshTable(tblRemark);
            loadEdit();
        } else if (act == ADD) {
            loadAdd();
        }
    }
    @Override
    public void save(){
        newStudent.setName(txtName.getText());
        newStudent.setAddress(txtAddress.getText());
        newStudent.setEmail(txtEmail.getText());
        newStudent.setGender(studentManager.formatGender((String) comboGender.getSelectedItem()));
        newStudent.setPhone(txtPhone.getText());
        newStudent.setNo(txtNo.getText());
        newStudent.setAdmission(new Admission( admiDPic.getDate(), txtPrevInstituteEdit.getText(), txtReasonLeavingEdit.getText()));
        newStudent.getAcademic().setBatch(new Batch((String)comboBatch.getSelectedItem()));
        newStudent.setGender(studentManager.formatGender((String)comboGender.getSelectedItem()));
        newStudent.setBirthday(dPic.getDate());
        if (newStudent.getFamilyStudent()== null) {
            newStudent.setFamilyStudent(new FamilyStudent());
        }
        if (act == EDIT) {
            edit();
        } else if (act == ADD) {
            add();

        }
        initUI();
        studentPanel.setSelectedComponent(listPanel);
        return;
    }

    @Override
    public void search(){
        if(!Validate.isEmpty(txtRegisterNoSearch.getText()) && !Validate.isNumber(txtRegisterNoSearch.getText())){
            JOptionPane.showMessageDialog(this, "Register No must be number");
            return;
        }
        String SQL = "SELECT student.ID, student.academic_register_No, student.No, student.name,batch.name AS Batch, student.phone, student.email, student.gender "
                + "from student LEFT JOIN (academic LEFT JOIN batch on academic.batch_ID=batch.ID) ON academic.register_No=student.academic_register_No WHERE student.status=1  ";
        if(!txtRegisterNoSearch.getText().isEmpty()){
            SQL+=" AND student.academic_register_No="+Integer.parseInt(txtRegisterNoSearch.getText());
        }
        if(!txtNoSearch.getText().isEmpty()){
            SQL+=" AND student.No='"+txtNoSearch.getText()+"'";
        }
        if(!txtNameSearch.getText().isEmpty()){
            SQL+=" AND student.name='"+txtNameSearch.getText()+"'";
        }
        if(comboBatchSearch.getSelectedIndex() !=0){
            SQL+=" AND batch.name='"+(String)comboBatchSearch.getSelectedItem()+"'";
        }
        studentManager.loadTable(SQL, tblStudent, Manager.STUDENT_MODEL);
        enableButton(false);
    }
    @Override
    public void loadEdit(){
        
        btnDeleteMark.setEnabled(false);
        btnEditMark.setEnabled(false);
        btnDeleteRelation.setEnabled(false);
        btnEditRelation.setEnabled(false);
        btnDeleteRemark.setEnabled(false);
        btnEditRemark.setEnabled(false);
        
        loadMarkTbl(tblMark);
        loadRelationTbl(tblRelation);
        loadRemarkTbl(tblRemark);
        loadExtraTbl(tblExtraCurricular);
        studentManager.loadCombobox("SELECT name FROM batch", comboBatch);
        
        txtRegisterNo.setText(currenStudent.getRegisterNo()+"");
        txtAddress.setText(currenStudent.getAddress());
        dPic.setDate(currenStudent.getBirthday());
        txtEmail.setText(currenStudent.getEmail());
        txtPhone.setText(currenStudent.getPhone());
        txtName.setText(currenStudent.getName());
        txtNo.setText(currenStudent.getNo());
        if(currenStudent.getAdmission() !=null){
            txtPrevInstituteEdit.setText(currenStudent.getAdmission().getPrev_Institute());
            txtReasonLeavingEdit.setText(currenStudent.getAdmission().getReason_Leaving());
            admiDPic.setDate(currenStudent.getAdmission().getTime());
        }
        comboGender.setSelectedItem(studentManager.parseGender(currenStudent.getGender()));
        
        if(currenStudent.getAcademic() !=null && currenStudent.getAcademic().getBatch() !=null)
            comboBatch.setSelectedItem(currenStudent.getAcademic().getBatch().getName());
        
    }
    private void loadAdd(){
        
        btnDeleteMark.setEnabled(false);
        btnEditMark.setEnabled(false);
        btnDeleteRelation.setEnabled(false);
        btnEditRelation.setEnabled(false);
        btnDeleteRemark.setEnabled(false);
        btnEditRemark.setEnabled(false);
        
        studentManager.refreshTable(tblMark);
        studentManager.refreshTable(tblRelation);
        studentManager.refreshTable(tblRemark);
        studentManager.loadCombobox("SELECT name FROM batch", comboBatch);
        
        txtAddress.setText("");
        dPic.setDate(null);
        txtEmail.setText("");
        txtName.setText("");
        txtNo.setText("");
        txtPrevInstituteEdit.setText("");
        txtReasonLeavingEdit.setText("");
        admiDPic.setDate(null);
    }
    private void loadDetail(){
        if(currenStudent==null)
            return;
        btnRelationDetail.setEnabled(false);
        btnExtraDetail.setEnabled(false);
        
        loadMarkTbl(tblExtraCurricularDetail);
        loadRelationTbl(tblRelationDetail);
        loadRemarkTbl(tblRemarkDetail);
        loadExtraTbl(tblExtraCurricularDetail);
        
        labelAddress.setText(currenStudent.getAddress());
        if(currenStudent.getAdmission() !=null){
            labelAdmissionTime.setText(studentManager.formatDate(currenStudent.getAdmission().getTime()));
            labelPrevInstitute.setText(currenStudent.getAdmission().getPrev_Institute());
            labelReasonLeaving.setText(currenStudent.getAdmission().getReason_Leaving());
        }
        if(currenStudent.getAcademic() !=null){
            labelRegisterNo.setText(currenStudent.getAcademic().getRegisterNo()+"");
            if(currenStudent.getAcademic().getBatch() !=null){
                labelBatch.setText(currenStudent.getAcademic().getBatch().getName());
            }
        }
        labelBirthday.setText(studentManager.formatDate(currenStudent.getBirthday()));
        labelEmail.setText(currenStudent.getEmail());
        labelGender.setText(studentManager.parseGender(currenStudent.getGender()));
        labelName.setText(currenStudent.getName());
        labelNo.setText(currenStudent.getNo());
        labelPhone.setText(currenStudent.getPhone());
    }
    private void loadMarkTbl(JTable tbl){
        List<Mark> marks=currenStudent.getMarks();
        if(marks==null || marks.size() <1){
            return;
        }
        int count=0;
        String[][] data=new String[marks.size()][4];
        for(Mark m:marks){
            data[count++]=new String[]{
              m.getID()+"",
              m.getSubject().getName(),
              m.getPoint()+"",
              studentManager.formatDate(m.getTime())
            };
        }
        studentManager.loadTable(data, tbl);
    }
    private void loadRelationTbl(JTable tbl){
        int count = 0;
        if (currenStudent.getFamilyStudent()== null || currenStudent.getFamilyStudent().getListRelative()== null) {
            return;
        }
        List<Relative> listreRelatives = currenStudent.getFamilyStudent().getListRelative();
        String[][] data = new String[listreRelatives.size()][7];
        for (Relative r : listreRelatives) {
            data[count++] = new String[]{
                r.getID() + "",
                r.getRelation(),
                r.getName(),
                r.getPhone(),
                r.getAddress(),
                r.getOccupation(),
                studentManager.parseGender(r.getGender())
            };
        }
        studentManager.loadTable(data, tbl);
    }
    private void loadRemarkTbl(JTable tbl){
        int count = 0;
        if (currenStudent.getRemarks()== null || currenStudent.getRemarks().size()<1) {
            return;
        }
        List<Remark> remarks = currenStudent.getRemarks();
        String[][] data = new String[remarks.size()][4];
        for (Remark r : remarks) {
            data[count++] = new String[]{
                r.getID()+"",
                r.getStaff().getNo()+"",
                r.getStaff().getName(),
                r.getContent(),
                studentManager.formatDate(r.getTime())
            };
        }
        studentManager.loadTable(data, tbl);
    }
    private void loadRemarkInfo(int mode){
        if (act == DETAIL) {
            new RemarkDetail(null, true, currenStudent.getRemarks().get(tblRemarkDetail.getSelectedRow()), studentManager).setVisible(true);
            return;
        }
        RemarkUpdate remarkUpdate = null;
        if (mode == ADD) {
            remarkUpdate = new RemarkUpdate(null, true, null, studentManager);
            remarkUpdate.setVisible(true);
        } else if (mode == EDIT) {
            remarkUpdate = new RemarkUpdate(null, true, studentManager.getSelectRow(tblRemark), studentManager);
            remarkUpdate.setVisible(true);
            if (remarkUpdate.getRemark()!= null) {
                removeSelectedRemark();
            }
        }
        if (remarkUpdate.getRemark() != null) {
            addRowTableRemark(remarkUpdate.getRemark());
            newStudent.addRemark(remarkUpdate.getRemark());
        }
    }
    private void removeSelectedRemark(){
        Vector row = studentManager.getSelectRow(tblRemark);
        newStudent.removeRemark(Integer.parseInt(row.get(1).toString()), entity.parseDate(row.get(4).toString()));
        studentManager.removeSelectedRowTable(tblRemark); 
    }
    private void addRowTableRemark(Remark r){
        Vector row = new Vector();
        row.add(r.getID());
        row.add(r.getStaff().getNo());
        row.add(r.getStaff().getName());
        row.add(r.getContent());
        row.add(studentManager.formatDate(r.getTime()));
        DefaultTableModel model = (DefaultTableModel) tblRemark.getModel();
        model.addRow(row);
    }
   
    private void loadExtraTbl(JTable tbl){
        int count = 0;
        if (currenStudent.getExtraCurriculars()== null || currenStudent.getExtraCurriculars().size()<1) {
            return;
        }
        List<ExtraCurricular> extraCurriculars = currenStudent.getExtraCurriculars();
        String[][] data = new String[extraCurriculars.size()][4];
        for (ExtraCurricular extra : extraCurriculars) {
            data[count++] = new String[]{
                extra.getID()+"",
                extra.getName(),
                extra.getLocation(),
                extraCurricularEntity.formatDate(extra.getStart()),
                extraCurricularEntity.formatDate(extra.getEnd()),
                extra.getNotes()
            };
        }
        studentManager.loadTable(data, tbl);
    }
    private void loadExtraInfo(int mode){
        if (act == DETAIL) {
            new ExtraCurricularDetail(null, true, currenStudent.getExtraCurriculars().get(tblExtraCurricularDetail.getSelectedRow())).setVisible(true);
            return;
        }
        ExtraCurricularUpdate extraCurricularUpdate = null;
        if (mode == ADD) {
            extraCurricularUpdate = new ExtraCurricularUpdate(null, true, null);
            extraCurricularUpdate.setVisible(true);
        } else if (mode == EDIT) {
            extraCurricularUpdate = new ExtraCurricularUpdate(null, true, entity.getSelectRow(tblExtraCurricular));
            extraCurricularUpdate.setVisible(true);
            if (extraCurricularUpdate.getExtraCurricular()!= null) {
                removeSelectedRemark();
            }
        }
        if (extraCurricularUpdate.getExtraCurricular()!= null) {
            addRowTableExtra(extraCurricularUpdate.getExtraCurricular());
            newStudent.addExtraCurricalar(extraCurricularUpdate.getExtraCurricular());
        }
    }
    private void removeSelectedExtra(){
        Vector row = studentManager.getSelectRow(tblExtraCurricular);
        newStudent.removeExtraCurricular(row.get(0).toString(), row.get(1).toString(), entity.parseDate(row.get(2).toString()), entity.parseDate(row.get(3).toString()));
        studentManager.removeSelectedRowTable(tblExtraCurricular);
    }
    private void addRowTableExtra(ExtraCurricular extra){
        Vector row = new Vector();
        row.add(extra.getID());
        row.add(extra.getName());
        row.add(extra.getLocation());
        row.add(entity.formatDate(extra.getStart()));
        row.add(entity.formatDate(extra.getEnd()));
        row.add(extra.getNotes());
        DefaultTableModel model = (DefaultTableModel) tblExtraCurricular.getModel();
        model.addRow(row);
    }
    private void loadRelationInfo(int mode){
        if (act == DETAIL) {
            new RelationDetail(null, true, currenStudent.getFamilyStudent().getListRelative().get(tblRelationDetail.getSelectedRow())).setVisible(true);
            return;
        }
        RelationUpdate relationUpdate = null;
        if (mode == ADD) {
            relationUpdate = new RelationUpdate(null, true, studentManager, null);
            relationUpdate.setVisible(true);
        } else if (mode == EDIT) {
            relationUpdate = new RelationUpdate(null, true, studentManager, studentManager.getSelectRow(tblRelation));
            relationUpdate.setVisible(true);
            if (relationUpdate.getRelaRelatives() != null) {
                removeSelectedRelation();
            }
        }
        if (relationUpdate.getRelaRelatives() != null) {
            addRowTableRelation(relationUpdate.getRelaRelatives());
            newStudent.getFamilyStudent().addRelative(relationUpdate.getRelaRelatives());
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
        row.add(studentManager.parseGender(r.getGender()));
        DefaultTableModel model = (DefaultTableModel) tblRelation.getModel();
        model.addRow(row);
    }
    private void removeSelectedRelation() {
        Vector row = studentManager.getSelectRow(tblRelation);
        newStudent.getFamilyStudent().removeRelative((String) row.get(1), (String) row.get(2));
        studentManager.removeSelectedRowTable(tblRelation);
    }
    private void loadMarkinfo(int mode){
        
        MarkUpdate markUpdate=null;
        if(mode==EDIT){
            markUpdate=new MarkUpdate(null, true, studentManager.getSelectRow(tblMark), studentManager);
            markUpdate.setVisible(true);
            if (markUpdate.getMark()!= null) {
                removeSelectedMark();
            }
        }else if(mode==ADD){
            markUpdate=new MarkUpdate(null, true, null, studentManager);
            markUpdate.setVisible(true);
        }
        if(markUpdate.getMark() !=null){
            addRowTableMark(markUpdate.getMark());
            newStudent.addMark(markUpdate.getMark());
        }
        
    }
    private void removeSelectedMark(){
        Vector row = studentManager.getSelectRow(tblMark);
        newStudent.removeMark((String)row.get(1), studentManager.parseDate(row.get(3).toString()));
        studentManager.removeSelectedRowTable(tblMark);
    }
    private void addRowTableMark(Mark mark){
        Vector row=new Vector();
        row.add(mark.getID());
        row.add(mark.getSubject().getName());
        row.add(mark.getPoint());
        row.add(studentManager.formatDate(mark.getTime()));
        DefaultTableModel model = (DefaultTableModel) tblMark.getModel();
        model.addRow(row);
    }
    private void enableButton(Boolean b){}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        studentPanel = new javax.swing.JTabbedPane();
        listPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        btnEdit1 = new javax.swing.JButton();
        btnDeactive = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        txtRegisterNoSearch = new javax.swing.JTextField();
        txtNoSearch = new javax.swing.JTextField();
        txtNameSearch = new javax.swing.JTextField();
        comboBatchSearch = new javax.swing.JComboBox();
        btnSearch = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnResetSearch = new javax.swing.JButton();
        detailPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelRegisterNo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelPhone = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelBirthday = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelGender = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        labelAddress = new javax.swing.JTextPane();
        jLabel20 = new javax.swing.JLabel();
        labelNo = new javax.swing.JLabel();
        labelBatch = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblRelationDetail = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        labelPrevInstitute = new javax.swing.JLabel();
        labelReasonLeaving = new javax.swing.JLabel();
        labelAdmissionTime = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblRemarkDetail = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnDeactive1 = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnRelationDetail = new javax.swing.JButton();
        btnExtraDetail = new javax.swing.JButton();
        btnViewList = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblExtraCurricularDetail = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblMarkDetail3 = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        btnRemarkDetail = new javax.swing.JButton();
        updatePanel = new javax.swing.JPanel();
        txtNo = new javax.swing.JTextField();
        comboBatch = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblMark = new javax.swing.JTable();
        comboGender = new javax.swing.JComboBox();
        labelUpdate = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        tblRelation = new javax.swing.JTable();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        txtPrevInstituteEdit = new javax.swing.JTextField();
        txtReasonLeavingEdit = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tblRemark = new javax.swing.JTable();
        btnAddRelation = new javax.swing.JButton();
        btnEditRelation = new javax.swing.JButton();
        btnDeleteRelation = new javax.swing.JButton();
        btnDeleteRemark = new javax.swing.JButton();
        btnEditRemark = new javax.swing.JButton();
        btnAddRemark = new javax.swing.JButton();
        btnDeleteMark = new javax.swing.JButton();
        btnEditMark = new javax.swing.JButton();
        btnAddMark = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        tblExtraCurricular = new javax.swing.JTable();
        btnDeleteExtra = new javax.swing.JButton();
        btnEditExtra = new javax.swing.JButton();
        btnAddExtra = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        txtRegisterNo = new javax.swing.JTextField();
        dPic = new com.toedter.calendar.JDateChooser();
        admiDPic = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(0, 0));

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Register No", "No", "Name", "Batch", "Phone", "Email", "Gender"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        btnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEdit1.setText("Edit");
        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });

        btnDeactive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        btnDeactive.setText("Deactive");
        btnDeactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactiveActionPerformed(evt);
            }
        });

        btnAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAdd1.setText("Add");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnDetail.setText("Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        jLabel19.setText("Register no");

        jLabel64.setText("No");

        jLabel65.setText("Name");

        jLabel66.setText("Batch");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("STUDENT");

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
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(listPanelLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel64))
                                .addGap(35, 35, 35)
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtRegisterNoSearch)
                                    .addComponent(txtNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(54, 54, 54)
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(listPanelLayout.createSequentialGroup()
                                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel65)
                                            .addComponent(jLabel66))
                                        .addGap(35, 35, 35)
                                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNameSearch)
                                            .addComponent(comboBatchSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btnResetSearch)))))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(jLabel5))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(btnDetail)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd1)
                        .addGap(27, 27, 27)
                        .addComponent(btnEdit1)
                        .addGap(31, 31, 31)
                        .addComponent(btnDeactive)
                        .addGap(40, 40, 40)
                        .addComponent(btnExport))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(btnSearch)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel5)
                .addGap(59, 59, 59)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtRegisterNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(txtNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBatchSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66))))
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnResetSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit1)
                    .addComponent(btnDeactive)
                    .addComponent(btnAdd1)
                    .addComponent(btnDetail)
                    .addComponent(btnExport))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        studentPanel.addTab("List", listPanel);

        detailPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel1.setText("STUDENT DETAIL");
        detailPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 12, -1, -1));

        jLabel2.setText("Register No");
        detailPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 81, -1, -1));

        labelRegisterNo.setText("Register No");
        detailPanel.add(labelRegisterNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 81, -1, -1));

        jLabel4.setText("Phone");
        detailPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 186, -1, -1));

        labelPhone.setText("Phone");
        detailPanel.add(labelPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 178, -1, -1));

        jLabel6.setText("Email");
        detailPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 211, -1, -1));

        labelEmail.setText("Email");
        detailPanel.add(labelEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 211, -1, -1));

        jLabel8.setText("Birthday");
        detailPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 246, -1, -1));

        labelBirthday.setText("Birthday");
        detailPanel.add(labelBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 246, -1, -1));

        jLabel10.setText("Address");
        detailPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 275, -1, -1));

        jLabel14.setText("Name");
        detailPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 155, -1, -1));

        labelName.setText("Name");
        detailPanel.add(labelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 155, -1, -1));

        jLabel16.setText("Gender");
        detailPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 346, -1, -1));

        labelGender.setText("Gender");
        detailPanel.add(labelGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 346, -1, -1));

        jScrollPane2.setViewportView(labelAddress);

        detailPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 275, 189, 59));

        jLabel20.setText("No");
        detailPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 104, -1, -1));

        labelNo.setText("No");
        detailPanel.add(labelNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 104, -1, -1));

        labelBatch.setText("Batch");
        detailPanel.add(labelBatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 128, -1, -1));

        jLabel23.setText("Batch");
        detailPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 128, -1, -1));

        tblRelationDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Relation", "Name", "Phone", "Address", "Occupation", "Gender"
            }
        ));
        tblRelationDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelationDetailMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(tblRelationDetail);

        detailPanel.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, 410, 60));

        jLabel11.setText("Family Infomation");
        detailPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, -1, -1));

        jLabel13.setText("Admission");
        detailPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 375, -1, -1));

        jLabel24.setText("Previous institute");
        detailPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 396, -1, -1));

        jLabel25.setText("Reason leaving");
        detailPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 419, -1, -1));

        jLabel26.setText("Time");
        detailPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 448, -1, -1));

        labelPrevInstitute.setText("Previous institute");
        detailPanel.add(labelPrevInstitute, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 396, -1, -1));

        labelReasonLeaving.setText("Reason leaving");
        detailPanel.add(labelReasonLeaving, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 419, -1, -1));

        labelAdmissionTime.setText("Time");
        detailPanel.add(labelAdmissionTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 448, -1, -1));

        jLabel30.setText("Remark");
        detailPanel.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, -1, -1));

        tblRemarkDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Staff No", "Staff name", "Content", "Time"
            }
        ));
        tblRemarkDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRemarkDetailMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(tblRemarkDetail);

        detailPanel.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 410, 75));

        jLabel39.setText("Extra Curricular");
        detailPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        detailPanel.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, 80, -1));

        btnDeactive1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/Export.png"))); // NOI18N
        btnDeactive1.setText("Deactive");
        btnDeactive1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactiveActionPerformed(evt);
            }
        });
        detailPanel.add(btnDeactive1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 520, -1, -1));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        detailPanel.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, 80, -1));

        btnRelationDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnRelationDetail.setText("Detail");
        btnRelationDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelationDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnRelationDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, -1, -1));

        btnExtraDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnExtraDetail.setText("Detail");
        btnExtraDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExtraDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnExtraDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, -1, -1));

        btnViewList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        btnViewList.setText("View List");
        btnViewList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewListActionPerformed(evt);
            }
        });
        detailPanel.add(btnViewList, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 520, -1, -1));

        tblExtraCurricularDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Location", "Start", "End", "Notes"
            }
        ));
        jScrollPane9.setViewportView(tblExtraCurricularDetail);

        detailPanel.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 361, 80));

        tblMarkDetail3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Subject", "Mark", "Time"
            }
        ));
        jScrollPane10.setViewportView(tblMarkDetail3);

        detailPanel.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 361, 105));

        jLabel41.setText("Mark");
        detailPanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, -1, -1));

        btnRemarkDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/detail.png"))); // NOI18N
        btnRemarkDetail.setText("Detail");
        btnRemarkDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemarkDetailActionPerformed(evt);
            }
        });
        detailPanel.add(btnRemarkDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 310, -1, -1));

        studentPanel.addTab("Detail", detailPanel);

        updatePanel.setPreferredSize(new java.awt.Dimension(1031, 700));
        updatePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        updatePanel.add(txtNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 156, -1));

        updatePanel.add(comboBatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 156, -1));

        jLabel40.setText("No");
        updatePanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jLabel42.setText("Batch");
        updatePanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));
        updatePanel.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 156, -1));

        jLabel43.setText("Name");
        updatePanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        jLabel51.setText("Email");
        updatePanel.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, -1, -1));
        updatePanel.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 156, -1));

        jLabel52.setText("Phone");
        updatePanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane6.setViewportView(txtAddress);

        updatePanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 224, 67));

        jLabel53.setText("Birthday");
        updatePanel.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        jLabel54.setText("Gender");
        updatePanel.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, -1, -1));

        jLabel55.setText("Mark");
        updatePanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, -1, -1));

        tblMark.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Subject", "Mark", "Time"
            }
        ));
        tblMark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMarkMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblMark);

        updatePanel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 410, 120));

        comboGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));
        updatePanel.add(comboGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 109, -1));

        labelUpdate.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        labelUpdate.setText("EDIT STUDENT");
        updatePanel.add(labelUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, -1, -1));

        jLabel57.setText("Family ");
        updatePanel.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 460, -1, -1));

        tblRelation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Relation", "Name", "Phone", "Address", "Occupation", "Gender"
            }
        ));
        tblRelation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelationMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(tblRelation);

        updatePanel.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, 410, 50));

        jLabel59.setText("Admission");
        updatePanel.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel60.setText("Previous institute");
        updatePanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        jLabel61.setText("Reason leaving");
        updatePanel.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, -1));

        jLabel62.setText("Time");
        updatePanel.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, -1, -1));
        updatePanel.add(txtPrevInstituteEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 420, 164, -1));
        updatePanel.add(txtReasonLeavingEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 164, -1));

        jLabel63.setText("Remark");
        updatePanel.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 340, -1, -1));

        tblRemark.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Staff No", "Staff name", "Content", "Time"
            }
        ));
        tblRemark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRemarkMouseClicked(evt);
            }
        });
        jScrollPane22.setViewportView(tblRemark);

        updatePanel.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 410, 75));

        btnAddRelation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAddRelation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRelationActionPerformed(evt);
            }
        });
        updatePanel.add(btnAddRelation, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 450, -1, -1));

        btnEditRelation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEditRelation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRelationActionPerformed(evt);
            }
        });
        updatePanel.add(btnEditRelation, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 450, -1, -1));

        btnDeleteRelation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnDeleteRelation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRelationActionPerformed(evt);
            }
        });
        updatePanel.add(btnDeleteRelation, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 450, -1, -1));

        btnDeleteRemark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnDeleteRemark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRemarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnDeleteRemark, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 330, -1, -1));

        btnEditRemark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEditRemark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRemarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnEditRemark, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 330, -1, -1));

        btnAddRemark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAddRemark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRemarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnAddRemark, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 330, -1, -1));

        btnDeleteMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnDeleteMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnDeleteMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, -1, -1));

        btnEditMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEditMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnEditMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, -1, -1));

        btnAddMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAddMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMarkActionPerformed(evt);
            }
        });
        updatePanel.add(btnAddMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, -1, -1));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        updatePanel.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 560, -1, -1));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        updatePanel.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 560, -1, -1));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/reset.gif"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        updatePanel.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 560, -1, -1));

        jLabel3.setText("Address");
        updatePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, -1));
        updatePanel.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 151, -1));

        jLabel67.setText("Extra Curricular");
        updatePanel.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, -1, -1));

        tblExtraCurricular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Location", "Start", "End", "Notes"
            }
        ));
        tblExtraCurricular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExtraCurricularMouseClicked(evt);
            }
        });
        jScrollPane23.setViewportView(tblExtraCurricular);

        updatePanel.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 410, 75));

        btnDeleteExtra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/delete.png"))); // NOI18N
        btnDeleteExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteExtraActionPerformed(evt);
            }
        });
        updatePanel.add(btnDeleteExtra, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, -1, -1));

        btnEditExtra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/edit.png"))); // NOI18N
        btnEditExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditExtraActionPerformed(evt);
            }
        });
        updatePanel.add(btnEditExtra, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, -1, -1));

        btnAddExtra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/add.png"))); // NOI18N
        btnAddExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddExtraActionPerformed(evt);
            }
        });
        updatePanel.add(btnAddExtra, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, -1, -1));

        jLabel44.setText("Register No");
        updatePanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        txtRegisterNo.setEditable(false);
        updatePanel.add(txtRegisterNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 156, -1));
        updatePanel.add(dPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 140, -1));
        updatePanel.add(admiDPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 140, -1));

        studentPanel.addTab("Update", updatePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(studentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 865, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(studentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMouseClicked
        currenStudent=studentManager.getStudentDetail(studentManager.getSelectID(tblStudent));
        enableButton(true);
        loadDetail();
        loadEdit();
        
    }//GEN-LAST:event_tblStudentMouseClicked

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        act=DETAIL;
        currenStudent=studentManager.getStudentDetail(studentManager.getSelectID(tblStudent));
        showPanel("Detail Student", detailPanel);
        loadDetail();
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        act=ADD;
        newStudent=new Student();
        showPanel("Add Student", updatePanel);
        loadAdd();
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        act=EDIT;
        currenStudent=studentManager.getStudentDetail(studentManager.getSelectID(tblStudent));
        newStudent=currenStudent;
        showPanel("Edit Student", updatePanel);
        loadEdit();
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        act=EDIT;
        currenStudent=studentManager.getStudentDetail(studentManager.getSelectID(tblStudent));
        newStudent=currenStudent;
        showPanel("Edit Student", updatePanel);
        loadEdit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactiveActionPerformed
        deactive();
    }//GEN-LAST:event_btnDeactiveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        newStudent=null;
        showPanel("List Student", listPanel);
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnExtraDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExtraDetailActionPerformed
        loadExtraInfo(DETAIL);
    }//GEN-LAST:event_btnExtraDetailActionPerformed

    private void btnRelationDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelationDetailActionPerformed
        loadRelationInfo(DETAIL);
    }//GEN-LAST:event_btnRelationDetailActionPerformed

    private void btnAddRelationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRelationActionPerformed
        loadRelationInfo(ADD);
    }//GEN-LAST:event_btnAddRelationActionPerformed

    private void btnEditRelationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRelationActionPerformed
        loadRelationInfo(EDIT);
    }//GEN-LAST:event_btnEditRelationActionPerformed

    private void btnEditMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMarkActionPerformed
        loadMarkinfo(EDIT);
    }//GEN-LAST:event_btnEditMarkActionPerformed

    private void btnAddMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMarkActionPerformed
        loadMarkinfo(ADD);
    }//GEN-LAST:event_btnAddMarkActionPerformed

    private void btnDeleteMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMarkActionPerformed
        removeSelectedMark();
    }//GEN-LAST:event_btnDeleteMarkActionPerformed

    private void btnDeleteRelationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRelationActionPerformed
        removeSelectedRelation();
    }//GEN-LAST:event_btnDeleteRelationActionPerformed

    private void btnViewListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewListActionPerformed
        showPanel("List Student", listPanel);
    }//GEN-LAST:event_btnViewListActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblRelationDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelationDetailMouseClicked
        btnRelationDetail.setEnabled(true);
    }//GEN-LAST:event_tblRelationDetailMouseClicked

    private void tblRemarkDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRemarkDetailMouseClicked
        btnExtraDetail.setEnabled(true);
    }//GEN-LAST:event_tblRemarkDetailMouseClicked

    private void tblMarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMarkMouseClicked
        btnAddMark.setEnabled(true);
        btnDeleteMark.setEnabled(true);
        btnEditMark.setEnabled(true);
    }//GEN-LAST:event_tblMarkMouseClicked

    private void tblRelationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelationMouseClicked
        btnDeleteRelation.setEnabled(true);
        btnEditRelation.setEnabled(true);
    }//GEN-LAST:event_tblRelationMouseClicked

    private void tblRemarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRemarkMouseClicked
        btnDeleteRemark.setEnabled(true);
        btnEditRemark.setEnabled(true);
    }//GEN-LAST:event_tblRemarkMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddRemarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRemarkActionPerformed
        loadRemarkInfo(ADD);
    }//GEN-LAST:event_btnAddRemarkActionPerformed

    private void btnEditRemarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRemarkActionPerformed
        loadRemarkInfo(EDIT);
    }//GEN-LAST:event_btnEditRemarkActionPerformed

    private void btnDeleteRemarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRemarkActionPerformed
        removeSelectedRemark();
    }//GEN-LAST:event_btnDeleteRemarkActionPerformed

    private void btnRemarkDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemarkDetailActionPerformed
        loadRemarkInfo(DETAIL);
    }//GEN-LAST:event_btnRemarkDetailActionPerformed

    private void tblExtraCurricularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExtraCurricularMouseClicked
        btnDeleteExtra.setEnabled(true);
        btnEditExtra.setEnabled(true);
    }//GEN-LAST:event_tblExtraCurricularMouseClicked

    private void btnDeleteExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteExtraActionPerformed
        removeSelectedExtra();
    }//GEN-LAST:event_btnDeleteExtraActionPerformed

    private void btnEditExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditExtraActionPerformed
        loadExtraInfo(EDIT);
    }//GEN-LAST:event_btnEditExtraActionPerformed

    private void btnAddExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddExtraActionPerformed
        loadExtraInfo(ADD);
    }//GEN-LAST:event_btnAddExtraActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        export();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnResetSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSearchActionPerformed
        txtRegisterNoSearch.setText("");
        txtNoSearch.setText("");
        txtNameSearch.setText("");
        comboBatchSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser admiDPic;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAddExtra;
    private javax.swing.JButton btnAddMark;
    private javax.swing.JButton btnAddRelation;
    private javax.swing.JButton btnAddRemark;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeactive;
    private javax.swing.JButton btnDeactive1;
    private javax.swing.JButton btnDeleteExtra;
    private javax.swing.JButton btnDeleteMark;
    private javax.swing.JButton btnDeleteRelation;
    private javax.swing.JButton btnDeleteRemark;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JButton btnEditExtra;
    private javax.swing.JButton btnEditMark;
    private javax.swing.JButton btnEditRelation;
    private javax.swing.JButton btnEditRemark;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnExtraDetail;
    private javax.swing.JButton btnRelationDetail;
    private javax.swing.JButton btnRemarkDetail;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewList;
    private javax.swing.JComboBox comboBatch;
    private javax.swing.JComboBox comboBatchSearch;
    private javax.swing.JComboBox comboGender;
    private com.toedter.calendar.JDateChooser dPic;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextPane labelAddress;
    private javax.swing.JLabel labelAdmissionTime;
    private javax.swing.JLabel labelBatch;
    private javax.swing.JLabel labelBirthday;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelGender;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNo;
    private javax.swing.JLabel labelPhone;
    private javax.swing.JLabel labelPrevInstitute;
    private javax.swing.JLabel labelReasonLeaving;
    private javax.swing.JLabel labelRegisterNo;
    private javax.swing.JLabel labelUpdate;
    private javax.swing.JPanel listPanel;
    private javax.swing.JTabbedPane studentPanel;
    private javax.swing.JTable tblExtraCurricular;
    private javax.swing.JTable tblExtraCurricularDetail;
    private javax.swing.JTable tblMark;
    private javax.swing.JTable tblMarkDetail3;
    private javax.swing.JTable tblRelation;
    private javax.swing.JTable tblRelationDetail;
    private javax.swing.JTable tblRemark;
    private javax.swing.JTable tblRemarkDetail;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameSearch;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtNoSearch;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPrevInstituteEdit;
    private javax.swing.JTextField txtReasonLeavingEdit;
    private javax.swing.JTextField txtRegisterNo;
    private javax.swing.JTextField txtRegisterNoSearch;
    private javax.swing.JPanel updatePanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void enableButton(boolean b) {
        btnDetail.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDeactive1.setEnabled(b);
    }

    @Override
    public boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void export() {
        ExportFile export=new ExportFile(tblStudent, ExportFile.STUDENT);
        export.toExcel();
    }


    
}
