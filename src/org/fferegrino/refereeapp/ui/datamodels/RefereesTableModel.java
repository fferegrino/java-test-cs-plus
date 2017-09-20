package org.fferegrino.refereeapp.ui.datamodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.fferegrino.refereeapp.entities.Referee;

@SuppressWarnings("serial")
public class RefereesTableModel extends AbstractTableModel {

	ArrayList<Referee> referees;
	String[] columnNames = {"ID",
            "Name",
            "Qualification",
            "Home locality",
            "Available",
            "Matches"};
	
	public RefereesTableModel(ArrayList<Referee> referees) {
		this.referees = referees;
	}
	
	@Override
	public int getRowCount() {
		return referees.size();
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
		Referee r = referees.get(rowIndex);
		
		switch(columnIndex)
		{
		case 0: // ID
			return r.getId();
		case 1:
			return r.getFirstName() + " " + r.getLastName();
		case 2:
			return r.getQualification();
		case 3: 
			return r.getHome().getRealName();
		case 4:
			return r.getLocalities();
		case 5:
			return String.valueOf(r.getAllocatedMatches());
			default:
				return ":)";
		}
	}

}
