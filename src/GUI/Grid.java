package GUI;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class Grid extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public Grid() {
		super("Cellular Automata");
		setLayout(new FlowLayout());
		
		//create cellModel at 10*10 and all cells 'dead'
		TableModel cellModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			public int getColumnCount() { return 10; }
			public int getRowCount() { return 10; }
			public Object getValueAt(int row, int col) { return "dead"; }
		};
		JTable grid = new JTable(cellModel);	
		
		//sets all cells in JTable(grid) to be centered
		DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();
		renderCenter.setHorizontalAlignment( JLabel.CENTER );
		renderCenter.setBackground(Color.red);
		for(int i = 0; i < grid.getColumnCount();i++){
			grid.getColumnModel().getColumn(i).setCellRenderer(renderCenter);
		}
		
		add(grid);
		
		//set all height and width to 50px
		TableColumn column = null;
		for (int i = 0; i < grid.getColumnCount(); i++){
			column = grid.getColumnModel().getColumn(i);
			column.setPreferredWidth(50);
			grid.setRowHeight(i,50);
		}
		
		//set JTable(grid) cell selection to false
		grid.setCellSelectionEnabled(false);
		
		
		
	}
}
