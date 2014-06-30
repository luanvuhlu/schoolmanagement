/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.home;

/**
 *
 * @author luan
 */
public interface UIInterface {
    public final int EDIT=0;
    public final int ADD=1;
    public final int DETAIL=-1;
    public void initUI();
    public void enableEditAddPanel(boolean b);
    public void enableButton(boolean b);
    public void loadEdit();
    public void search();
    public void save();
    public void deactive();
    public boolean validateForm();
}
