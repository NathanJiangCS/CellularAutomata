package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	
	public Component getTellCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (hasFocus){
			setBackground(Color.cyan);
		}else if (isSelected) {
			setBackground(table.getSelectionBackground());
		}else{
			setBackground(table.getBackground());
		}
		return this;
	}
}
