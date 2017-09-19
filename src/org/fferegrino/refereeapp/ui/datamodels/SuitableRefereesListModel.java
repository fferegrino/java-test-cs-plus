package org.fferegrino.refereeapp.ui.datamodels;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.fferegrino.refereeapp.entities.*;

public class SuitableRefereesListModel extends AbstractListModel {

	ArrayList<Referee> referees;
	
	public SuitableRefereesListModel(ArrayList<Referee> referees) {
			this.referees = referees;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return referees.size();
	}

	@Override
	public Object getElementAt(int index) {
		return referees.get(index).getFirstName();
	}

}
