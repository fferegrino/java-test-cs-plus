package org.fferegrino.refereeapp.ui.datamodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.fferegrino.refereeapp.entities.Match;

@SuppressWarnings("serial")
public class MatchesTableModel extends AbstractTableModel {

	ArrayList<Match> matches;
	String[] columnNames = {"Week",
            "Level",
            "Area",
            "Referee 1",
            "Referee 2"};
	
	public MatchesTableModel(ArrayList<Match> arrayList) {
		this.matches = arrayList;
	}

	@Override
	public int getRowCount() {
		return matches.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Match m = matches.get(rowIndex);
		
		switch(columnIndex)
		{
		case 0: // ID
			return String.valueOf(m.week);
		case 1:
			return m.level;
		case 2:
			return m.area.getRealName();
		case 3: 
			return m.referee1.getFirstName() + " " + m.referee1.getLastName();
		case 4:
			return m.referee2.getFirstName() + " " + m.referee2.getLastName();
			default:
				return ":)";
		}
	}


}
