/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeladorrelacional;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rsoto
 */
public class RelationTableModel extends DefaultTableModel{
    
    public RelationTableModel(Object[][] data, Object[] colNames){
        super(data, colNames);
    }

    public Vector getDataVector() {
        return dataVector;
    }

    public Vector getColumnIdentifiers() {
        return columnIdentifiers;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return col > 0;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        Class clazz = String.class;
        
        switch(columnIndex){
            case 0:
                clazz = String.class;
                break;
            case 1:
                clazz = String.class;
                break;
            case 2:
                clazz = Integer.class;
                break;
            case 3:
                clazz = Integer.class;
                break;
            case 4:
                clazz = Boolean.class;
                break;
            case 5:
                clazz = Boolean.class;
                break;
        }
        return clazz;
    }
    
}
