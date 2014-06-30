/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package export;

import controller.Manager;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import jxl.CellView; 
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author luan
 */
public class Excel {

    public static final int ROW_START = 4;
    public static final int COLUMN_START = 1;
    public static final int NOT_CHOOSE_DIRECTORY = -1;
    public static final int EXPORT_SUCCESS = 1;
    private WritableCellFormat formatHeader;
    private WritableCellFormat formatHeaderColumn;
    private WritableCellFormat formatRowData;
    private int currentRow;
    private String header;
    private String fileName = "Export";
    private String[] ColumnHeader;
    private int type;
    private JTable tbl;

    public Excel(JTable tbl, int type) {
        this.tbl = tbl;
        this.type = type;
        setFormatHeader();
        setfomatHeaderColumn();
        setfomatRowData();
        currentRow = ROW_START;
        switch (type) {
            case ExportFile.EXTRA_CURRICULAR:
                header = "Extra Curricular";
                ColumnHeader = Manager.EXTRA_CURRICULAR_MODEL;
                break;
            case ExportFile.STUDENT:
                header = "Student";
                ColumnHeader = Manager.STUDENT_MODEL;
                break;
            case ExportFile.SUBJECT:
                header = "SUBJECT";
                ColumnHeader = Manager.SUBJECT_MODEL;
                break;
            case ExportFile.MARK:
                header = "Mark";
                ColumnHeader = Manager.MARK_MODEL;
                break;
            case ExportFile.BATCH:
                header = "Batch";
                ColumnHeader = Manager.BATCH_MODEL;
                break;
            case ExportFile.DEPARTMENT:
                header = "Department";
                ColumnHeader = Manager.DEPARTMENT_MODEL;
                break;
            case ExportFile.POSITION:
                header = "position";
                ColumnHeader = Manager.POSITION_MODEL;
                break;
            case ExportFile.STAFF:
                header = "Staff";
                ColumnHeader = Manager.STAFF_MODEL;
                break;
            default:
                break;
        }
    }

    private void setFormatHeader() {
        formatHeader = new WritableCellFormat();
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD);
        formatHeader.setFont(fontHeader);
        try {
            formatHeader.setAlignment(Alignment.CENTRE);
            formatHeader.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException ex) {
            Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setfomatHeaderColumn() {
        formatHeaderColumn = new WritableCellFormat();
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        formatHeaderColumn.setFont(fontHeader);
        try {
            formatHeaderColumn.setAlignment(Alignment.CENTRE);
            formatHeaderColumn.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException ex) {
            Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setfomatRowData() {
        formatRowData = new WritableCellFormat();
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
        formatRowData.setFont(fontHeader);
        try {
            formatRowData.setAlignment(Alignment.CENTRE);
            formatRowData.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException ex) {
            Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int write() {
        File direct = null;
        try {
            direct = new File(DirectoryChooser.chooser(null).getPath() + "/export.xls");
        } catch (NullPointerException ex) {
            return NOT_CHOOSE_DIRECTORY;
        }
        WritableWorkbook writableWorkbook = null;
        WritableSheet sheet = null;
        try {
            writableWorkbook = Workbook.createWorkbook(direct);
            sheet = writableWorkbook.createSheet(fileName, 0);

            //Write Header
            writeHeader(sheet);
            writeHeaderColumn(sheet);
            writeRows(sheet, tbl);

            writableWorkbook.write();
        } catch (IOException ex) {
            Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writableWorkbook.close();
            } catch (IOException | WriteException ex) {
                Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return EXPORT_SUCCESS;
    }

    private void writeHeader(WritableSheet sheet) {
        try {
            sheet.mergeCells(COLUMN_START, ROW_START, COLUMN_START + ColumnHeader.length - 1, ROW_START + 1);
            sheet.addCell(new Label(COLUMN_START, ROW_START, header, formatHeader));
        } catch (WriteException ex) {
            Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentRow = currentRow + 4;
    }

    private void writeHeaderColumn(WritableSheet sheet) {
        int startRow = currentRow + 4;
        for (int i = 0; i < ColumnHeader.length; i++) {
            try {
                WritableCell cell = new Label(i + COLUMN_START, currentRow, ColumnHeader[i], formatHeaderColumn);
                sheet.addCell(cell);
            } catch (WriteException ex) {
                Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        currentRow++;
    }

    private void writeRows(WritableSheet sheet, JTable tbl) {
        int rowCount = tbl.getRowCount();
        int columnCount = tbl.getColumnCount();
        for (int i = 0; i < columnCount; i++) {

            if (ColumnHeader[i].equalsIgnoreCase("ID")
                    || ColumnHeader[i].equalsIgnoreCase("Point")
                    || ColumnHeader[i].equalsIgnoreCase("Student No")
                    || ColumnHeader[i].equalsIgnoreCase("Staff No")) {
                sheet.setColumnView(i + COLUMN_START, 7);
            } else {
                CellView cv = sheet.getColumnView(i + COLUMN_START);
                cv.setAutosize(true);
                sheet.setColumnView(i + COLUMN_START, cv);
            }

            for (int j = 0; j < rowCount; j++) {
                try {
                    String data = "";
                    try {
                        data = tbl.getValueAt(j, i).toString();
                    } catch (NullPointerException ex) {
                    }
                    WritableCell cell = new Label(i + COLUMN_START, j + currentRow, data, formatRowData);
                    sheet.addCell(cell);
                } catch (WriteException ex) {
                    Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        currentRow += rowCount;
    }
}
