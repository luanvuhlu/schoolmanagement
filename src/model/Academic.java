/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luan
 */
public class Academic {
    private int registerNo;
    private Batch batch;

    public Batch getBatch() {
        return batch;
    }

    public Academic(int registerNo, Batch batch) {
        this.registerNo = registerNo;
        this.batch = batch;
    }
    public Academic(int registerNo) {
        this.registerNo = registerNo;
    }

    public void setRegisterNo(int registerNo) {
        this.registerNo = registerNo;
    }

    public int getRegisterNo() {
        return registerNo;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Academic() {
    }

    public Academic(Batch batch) {
        this.batch = batch;
    }
    
}
