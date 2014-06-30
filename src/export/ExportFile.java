/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package export;

import javax.swing.JTable;

/**
 *
 * @author luan
 */
public class ExportFile { 

    public static final int NOT_CHOOSE_DIRECTORY = -1;
    public static final int EXPORT_SUCCESS = 1;
     
    
    public static final int USER = 0;
    public static final int STAFF = 1;
    public static final int STUDENT = 2;
    public static final int BATCH = 3;
    public static final int SUBJECT = 4;
    public static final int POSITION = 5;
    public static final int DEPARTMENT = 6;
    public static final int EXTRA_CURRICULAR = 7;
    public static final int MARK = 8;
    
    public int type;
    private JTable tbl;
    
    

    public ExportFile(JTable tbl, int type) {
        this.tbl=tbl;
        this.type=type;
    }
    public void toExcel(){
        Excel file=new Excel(tbl, type);
        file.write();
    }
    public void toDoc(){}
    public void print(){}
    

    

    
}
