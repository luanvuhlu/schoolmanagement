/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package export;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author luan
 */
public class DirectoryChooser {
    
    public static File chooser(Component parent){
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Choose directory to save file"); 
        fileChooser.showOpenDialog(parent);
        return fileChooser.getSelectedFile();
    }
}
