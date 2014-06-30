/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.home;

import controller.Authentication;
import controller.Manager;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import view.manager.staff.ListDepartment;
import view.manager.staff.ListPosition;
import view.manager.admin.ListUser;
import view.manager.staff.ListStaff;
import view.manager.student.ListBatch;
import view.manager.student.ListExtraCurricular;
import view.manager.student.ListStudent;
import view.manager.student.ListSubject;
import view.manager.student.ListMark;

/**
 *
 * @author luan
 */
public class Main extends javax.swing.JFrame {

    Authentication authentication;
    
   
    public static final int WIDTH = 900;
    public static final int HEIGHT = 650; 

    /**
     * Creates new form Main
     */
    public Main(Authentication authentication) {
        this.authentication = authentication;
        initComponents();
        enableComponent(authentication.getRole());
        initUI();
    }
    private void initUI(){
        setSize(WIDTH, HEIGHT+20);
        mainPane.setSize(WIDTH, HEIGHT-30);
        this.setLocationRelativeTo(this);
        showAccountInfo();
    }
    private static ImageIcon loadImageIcon(String path) {
        URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void refreshMainPane() {
        mainPane.removeAll();
        mainPane.repaint();
    }

    private void enableComponent(int pess) {
        muBatch.setVisible(false); 
        muStudent.setVisible(false);
        muSubject.setVisible(false);
        muExtraCurricular.setVisible(false);
        muBatch.setVisible(false);
        muUser.setVisible(false);
        muStaff.setVisible(false);
        muDepartment.setVisible(false);
        muPosition.setVisible(false);
        muMark.setVisible(false);
        
        if (pess == Authentication.HEADER_ADMIN) {
            muUser.setVisible(true);
        } else if (pess == Authentication.STAFF_MANAGER) {
            muStaff.setVisible(true);
            muDepartment.setVisible(true);
            muPosition.setVisible(true);
        } else if (pess == Authentication.STUDENT_MANAGER) {
            muMark.setVisible(true);
        muStudent.setVisible(true);
        muSubject.setVisible(true);
        muExtraCurricular.setVisible(true);
        muBatch.setVisible(true);
        }
    }

    private void showComponent(int comp) {
        switch (comp) {
            case Manager.USER:
                setTitle("Manage User");
                ListUser users = new ListUser(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(users);
                users.show();
                break;
            case Manager.STAFF:
                setTitle("Manage Staff");
                ListStaff listStaff = new ListStaff(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(listStaff);
                listStaff.show();
                break;
            case Manager.STUDENT:
                setTitle("Manage Student");
                ListStudent listStudent = new ListStudent(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(listStudent);
                listStudent.show();
                break;
            case Manager.SUBJECT:
                setTitle("Manage Subject");
                ListSubject listSubject = new ListSubject(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(listSubject);
                listSubject.show();
                break;
            case Manager.BATCH:
                setTitle("Manage Batch");
                ListBatch batchs = new ListBatch(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(batchs);
                batchs.show();
                break;
            case Manager.DEPARTMENT:
                setTitle("Manage Department");
                ListDepartment department = new ListDepartment(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(department);
                department.show();
                break;
            case Manager.POSITION:
                setTitle("Manage Position");
                ListPosition listPosition = new ListPosition(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(listPosition);
                listPosition.show();
                break;
            case Manager.EXTRA_CURRICULAR:
                setTitle("Manage Extra curricular");
                ListExtraCurricular extraCurricular = new ListExtraCurricular(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(extraCurricular);
                extraCurricular.show();
                break;
            case Manager.MARK:
                setTitle("Manage Mark");
                ListMark searchMark = new ListMark(WIDTH, HEIGHT);
                refreshMainPane();
                mainPane.add(searchMark);
                searchMark.show(); 
            default:
                break;
        }
    }

    private void showDeactived(int type) {
        refreshMainPane();
        ListDeactived listDeactived = null;
        switch (type) {
            case Manager.USER:
                setTitle("Deactived User");
                break;
            case Manager.DEPARTMENT:
                setTitle("Deactived Department");
                break;
            case Manager.POSITION:
                setTitle("Deactived Position");
                break;
            case Manager.STAFF:
                setTitle("Deactived Staff");
                break;
            case Manager.STUDENT:
                setTitle("Deactived Student");
                break;
            case Manager.SUBJECT:
                setTitle("Deactived Subject");
                break;
            case Manager.BATCH:
                setTitle("Deactived Batch");
                break;
            case Manager.EXTRA_CURRICULAR:
                setTitle("Deactived Extra curricular");
                break;
            default:
                break;
        }
        listDeactived = new ListDeactived(type);
        mainPane.add(listDeactived);
        listDeactived.show();
    }
    private void showAccountInfo(){
        AccountInfo accountInfo=new AccountInfo(WIDTH, HEIGHT, authentication);
        setTitle("Account Infomation");
        refreshMainPane();
        mainPane.add(accountInfo);
        accountInfo.show();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JDesktopPane();
        jMenuBar2 = new javax.swing.JMenuBar();
        muExit = new javax.swing.JMenu();
        miExit = new javax.swing.JMenuItem();
        muAccount = new javax.swing.JMenu();
        miInfo = new javax.swing.JMenuItem();
        miLogout = new javax.swing.JMenuItem();
        miChangePassword = new javax.swing.JMenuItem();
        muStaff = new javax.swing.JMenu();
        miListStaff = new javax.swing.JMenuItem();
        miDeactStaff = new javax.swing.JMenuItem();
        muExtraCurricular = new javax.swing.JMenu();
        miListExtraCurricular = new javax.swing.JMenuItem();
        miDeactExtraCurricular = new javax.swing.JMenuItem();
        muMark = new javax.swing.JMenu();
        miSearchMark = new javax.swing.JMenuItem();
        muSubject = new javax.swing.JMenu();
        miListSubject = new javax.swing.JMenuItem();
        miDeactSubject = new javax.swing.JMenuItem();
        muStudent = new javax.swing.JMenu();
        miListStudent = new javax.swing.JMenuItem();
        miDeactStudent = new javax.swing.JMenuItem();
        muDepartment = new javax.swing.JMenu();
        miDepartmentList = new javax.swing.JMenuItem();
        miDeactDepartment = new javax.swing.JMenuItem();
        muPosition = new javax.swing.JMenu();
        miPositionList = new javax.swing.JMenuItem();
        miDeactPosition = new javax.swing.JMenuItem();
        muBatch = new javax.swing.JMenu();
        miBatchList = new javax.swing.JMenuItem();
        miDeactBatch = new javax.swing.JMenuItem();
        muUser = new javax.swing.JMenu();
        miUserList = new javax.swing.JMenuItem();
        miDeactUser = new javax.swing.JMenuItem();
        muHelp = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("School Management System");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainPane.setPreferredSize(new java.awt.Dimension(1024, 700));
        getContentPane().add(mainPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        muExit.setText("File");

        miExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        muExit.add(miExit);

        jMenuBar2.add(muExit);

        muAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/account.png"))); // NOI18N
        muAccount.setText("My Account");

        miInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/info.jpg"))); // NOI18N
        miInfo.setText("Info");
        miInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miInfoActionPerformed(evt);
            }
        });
        muAccount.add(miInfo);

        miLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/logout.png"))); // NOI18N
        miLogout.setText("Logout");
        miLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLogoutActionPerformed(evt);
            }
        });
        muAccount.add(miLogout);

        miChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/chagePassword.png"))); // NOI18N
        miChangePassword.setText("Change password");
        miChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miChangePasswordActionPerformed(evt);
            }
        });
        muAccount.add(miChangePassword);

        jMenuBar2.add(muAccount);

        muStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/staff.png"))); // NOI18N
        muStaff.setText("Staff");

        miListStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miListStaff.setText("View List");
        miListStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miListStaffActionPerformed(evt);
            }
        });
        muStaff.add(miListStaff);

        miDeactStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactStaff.setText("Deactived List");
        miDeactStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactStaffActionPerformed(evt);
            }
        });
        muStaff.add(miDeactStaff);

        jMenuBar2.add(muStaff);

        muExtraCurricular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/Activity.png"))); // NOI18N
        muExtraCurricular.setText("Extra Curricular");

        miListExtraCurricular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miListExtraCurricular.setText("View list");
        miListExtraCurricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miListExtraCurricularActionPerformed(evt);
            }
        });
        muExtraCurricular.add(miListExtraCurricular);

        miDeactExtraCurricular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactExtraCurricular.setText("Deactived Extra Curricular");
        miDeactExtraCurricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactExtraCurricularActionPerformed(evt);
            }
        });
        muExtraCurricular.add(miDeactExtraCurricular);

        jMenuBar2.add(muExtraCurricular);

        muMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/mark.png"))); // NOI18N
        muMark.setText("Mark");

        miSearchMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miSearchMark.setText("View All");
        miSearchMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSearchMarkActionPerformed(evt);
            }
        });
        muMark.add(miSearchMark);

        jMenuBar2.add(muMark);

        muSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/subject.png"))); // NOI18N
        muSubject.setText("Subject");

        miListSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miListSubject.setText("View list");
        miListSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miListSubjectActionPerformed(evt);
            }
        });
        muSubject.add(miListSubject);

        miDeactSubject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactSubject.setText("Deactived Subject");
        miDeactSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactSubjectActionPerformed(evt);
            }
        });
        muSubject.add(miDeactSubject);

        jMenuBar2.add(muSubject);

        muStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/student.png"))); // NOI18N
        muStudent.setText("Student");

        miListStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miListStudent.setText("View List");
        miListStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miListStudentActionPerformed(evt);
            }
        });
        muStudent.add(miListStudent);

        miDeactStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactStudent.setText("Deactived Student");
        miDeactStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactStudentActionPerformed(evt);
            }
        });
        muStudent.add(miDeactStudent);

        jMenuBar2.add(muStudent);

        muDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/department.png"))); // NOI18N
        muDepartment.setText("Department");

        miDepartmentList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miDepartmentList.setText("View List");
        miDepartmentList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDepartmentListActionPerformed(evt);
            }
        });
        muDepartment.add(miDepartmentList);

        miDeactDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactDepartment.setText("Deactived list");
        miDeactDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactDepartmentActionPerformed(evt);
            }
        });
        muDepartment.add(miDeactDepartment);

        jMenuBar2.add(muDepartment);

        muPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/position.png"))); // NOI18N
        muPosition.setText("Position");

        miPositionList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miPositionList.setText("View List");
        miPositionList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPositionListActionPerformed(evt);
            }
        });
        muPosition.add(miPositionList);

        miDeactPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactPosition.setText("Deactived list");
        miDeactPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactPositionActionPerformed(evt);
            }
        });
        muPosition.add(miDeactPosition);

        jMenuBar2.add(muPosition);

        muBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/batch.png"))); // NOI18N
        muBatch.setText("Batch");

        miBatchList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miBatchList.setText("View List");
        miBatchList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBatchListActionPerformed(evt);
            }
        });
        muBatch.add(miBatchList);

        miDeactBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactBatch.setText("Deactived list");
        miDeactBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactBatchActionPerformed(evt);
            }
        });
        muBatch.add(miDeactBatch);

        jMenuBar2.add(muBatch);

        muUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/user.gif"))); // NOI18N
        muUser.setText("User");

        miUserList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/list.jpg"))); // NOI18N
        miUserList.setText("View List");
        miUserList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUserListActionPerformed(evt);
            }
        });
        muUser.add(miUserList);

        miDeactUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/lock.png"))); // NOI18N
        miDeactUser.setText("Deactived list");
        miDeactUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeactUserActionPerformed(evt);
            }
        });
        muUser.add(miDeactUser);

        jMenuBar2.add(muUser);

        muHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/Help.png"))); // NOI18N
        muHelp.setText("Help");

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/help-content.png"))); // NOI18N
        jMenuItem12.setText("Help content");
        muHelp.add(jMenuItem12);

        mnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/20x20/about.png"))); // NOI18N
        mnAbout.setText("About");
        mnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAboutActionPerformed(evt);
            }
        });
        muHelp.add(mnAbout);

        jMenuBar2.add(muHelp);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        int res=JOptionPane.showConfirmDialog(this, "Are you sure logout ?");
        try {
            if(res==0)
                authentication.logout();
            else
                return;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }//GEN-LAST:event_miExitActionPerformed

    private void miLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLogoutActionPerformed
        int res=JOptionPane.showConfirmDialog(this, "Are you sure logout ?");
        
        try {
            if(res==0)
                authentication.logout();
            else{
                return;
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Can't logout ! Please try again !");
            return;
        }
        dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_miLogoutActionPerformed

    private void mnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAboutActionPerformed
        About about = new About(this, true);
        about.setVisible(true);
    }//GEN-LAST:event_mnAboutActionPerformed

    private void miListStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miListStaffActionPerformed
        showComponent(Manager.STAFF);
    }//GEN-LAST:event_miListStaffActionPerformed

    private void miListStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miListStudentActionPerformed
        showComponent(Manager.STUDENT);
    }//GEN-LAST:event_miListStudentActionPerformed

    private void miDepartmentListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDepartmentListActionPerformed
        showComponent(Manager.DEPARTMENT);
    }//GEN-LAST:event_miDepartmentListActionPerformed

    private void miPositionListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPositionListActionPerformed
        showComponent(Manager.POSITION);
    }//GEN-LAST:event_miPositionListActionPerformed

    private void miBatchListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBatchListActionPerformed
        showComponent(Manager.BATCH);
    }//GEN-LAST:event_miBatchListActionPerformed

    private void miUserListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUserListActionPerformed
        showComponent(Manager.USER);
    }//GEN-LAST:event_miUserListActionPerformed

    private void miListSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miListSubjectActionPerformed
        showComponent(Manager.SUBJECT);
    }//GEN-LAST:event_miListSubjectActionPerformed

    private void miListExtraCurricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miListExtraCurricularActionPerformed
        showComponent(Manager.EXTRA_CURRICULAR);
    }//GEN-LAST:event_miListExtraCurricularActionPerformed

    private void miDeactSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactSubjectActionPerformed
        showDeactived(Manager.SUBJECT); 
    }//GEN-LAST:event_miDeactSubjectActionPerformed

    private void miDeactStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactStaffActionPerformed
        showDeactived(Manager.STAFF);
    }//GEN-LAST:event_miDeactStaffActionPerformed

    private void miDeactExtraCurricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactExtraCurricularActionPerformed
        showDeactived(Manager.EXTRA_CURRICULAR);
    }//GEN-LAST:event_miDeactExtraCurricularActionPerformed

    private void miDeactStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactStudentActionPerformed
        showDeactived(Manager.STUDENT);
    }//GEN-LAST:event_miDeactStudentActionPerformed

    private void miDeactDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactDepartmentActionPerformed
        showDeactived(Manager.DEPARTMENT);
    }//GEN-LAST:event_miDeactDepartmentActionPerformed

    private void miDeactPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactPositionActionPerformed
        showDeactived(Manager.POSITION);
    }//GEN-LAST:event_miDeactPositionActionPerformed

    private void miDeactBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactBatchActionPerformed
        showDeactived(Manager.BATCH);
    }//GEN-LAST:event_miDeactBatchActionPerformed

    private void miDeactUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeactUserActionPerformed
        showDeactived(Manager.USER);
    }//GEN-LAST:event_miDeactUserActionPerformed

    private void miSearchMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSearchMarkActionPerformed
        showComponent(Manager.MARK);
    }//GEN-LAST:event_miSearchMarkActionPerformed

    private void miChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miChangePasswordActionPerformed
        ChangePassword changePassword=new ChangePassword(this, true, authentication);
        changePassword.setVisible(true);
    }//GEN-LAST:event_miChangePasswordActionPerformed

    private void miInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miInfoActionPerformed
        showAccountInfo();
    }//GEN-LAST:event_miInfoActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenuItem miBatchList;
    private javax.swing.JMenuItem miChangePassword;
    private javax.swing.JMenuItem miDeactBatch;
    private javax.swing.JMenuItem miDeactDepartment;
    private javax.swing.JMenuItem miDeactExtraCurricular;
    private javax.swing.JMenuItem miDeactPosition;
    private javax.swing.JMenuItem miDeactStaff;
    private javax.swing.JMenuItem miDeactStudent;
    private javax.swing.JMenuItem miDeactSubject;
    private javax.swing.JMenuItem miDeactUser;
    private javax.swing.JMenuItem miDepartmentList;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miInfo;
    private javax.swing.JMenuItem miListExtraCurricular;
    private javax.swing.JMenuItem miListStaff;
    private javax.swing.JMenuItem miListStudent;
    private javax.swing.JMenuItem miListSubject;
    private javax.swing.JMenuItem miLogout;
    private javax.swing.JMenuItem miPositionList;
    private javax.swing.JMenuItem miSearchMark;
    private javax.swing.JMenuItem miUserList;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenu muAccount;
    private javax.swing.JMenu muBatch;
    private javax.swing.JMenu muDepartment;
    private javax.swing.JMenu muExit;
    private javax.swing.JMenu muExtraCurricular;
    private javax.swing.JMenu muHelp;
    private javax.swing.JMenu muMark;
    private javax.swing.JMenu muPosition;
    private javax.swing.JMenu muStaff;
    private javax.swing.JMenu muStudent;
    private javax.swing.JMenu muSubject;
    private javax.swing.JMenu muUser;
    // End of variables declaration//GEN-END:variables
}
