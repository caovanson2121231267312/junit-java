/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class FormHienThi extends JPanel {
    
    MyTable mtb;
    
    public FormHienThi() {
        
    }
    
    public String getSelectedRow(int col) {
        int i = mtb.getTable().getSelectedRow();
        int realI = mtb.getTable().convertRowIndexToModel(i);
        if (i >= 0) {
            return mtb.getModel().getValueAt(realI, col).toString();
        }
        return null;
    }
    
}