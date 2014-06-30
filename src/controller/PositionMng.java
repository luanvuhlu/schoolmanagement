/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author luan
 */
public interface PositionMng {
    public int addPosition(String[] data);
    public int deactivePosition(int ID);
    public int editPosition(String[] data);
    public String removePosition(int ID);
}
