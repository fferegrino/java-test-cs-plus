package org.fferegrino.refereeapp.ui.datamodels;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.AbstractListModel;

import org.fferegrino.refereeapp.entities.Area;
import org.fferegrino.refereeapp.entities.Match;
import org.fferegrino.refereeapp.entities.Referee;

@SuppressWarnings("serial")
public class SuitableRefereesListModel extends AbstractListModel<Referee> {

	ArrayList<Referee> referees;
	private Referee[] suitableReferees;
	
	public SuitableRefereesListModel(ArrayList<Referee> referees) {
			this.referees = referees;
			this.suitableReferees = new Referee[0];
	}
	
	public void setConditions(String gameLevel, Area area)
	{
		boolean isJuniorMatch = gameLevel.equals(Match.LEVEL_JUNIOR);
		Referee[] refs = referees
			.stream()
			.filter(referee -> (referee.getQualificationLevel() == 1) == isJuniorMatch)
			.filter(referee -> referee.willingToTravelTo(area))
			.sorted((r1, r2) -> r1.getAllocatedMatches() < r2.getAllocatedMatches() ? -1 : 1)
			.toArray(size -> new Referee[size]);
		 
		Stream<Referee> refsFromHome;
		Stream<Referee> refsFromAdjacent = null;
		Stream<Referee> refsOther = Stream.empty();
		
		refsFromHome = Stream.of(refs).filter(r -> r.getHome() == area);
		
		if(area == Area.CENTRAL)
		{
			refsFromAdjacent = Stream.of(refs).filter(r -> r.getHome() != area);
		}
		else if(area == Area.SOUTH)
		{
			refsFromAdjacent = Stream.of(refs).filter(r -> r.getHome() == Area.CENTRAL);
			refsOther = Stream.of(refs).filter(r -> r.getHome() == Area.NORTH);
		}
		else if(area == Area.NORTH)
		{
			refsFromAdjacent = Stream.of(refs).filter(r -> r.getHome() == Area.CENTRAL);
			refsOther = Stream.of(refs).filter(r -> r.getHome() == Area.SOUTH);
		}
		
		this.suitableReferees = Stream.concat(Stream.concat(refsFromHome,refsFromAdjacent), refsOther)
				.toArray(size -> new Referee[size]);
		fireContentsChanged(this, 0, getSuitableReferees().length);
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return getSuitableReferees().length;
	}

	@Override
	public Referee getElementAt(int index) {
		return getSuitableReferees()[index];
		/*
		.getFirstName() + " "+ 
				getSuitableReferees()[index].getLastName() + " " + 
				getSuitableReferees()[index].getHome().name() + " " + 
				String.valueOf(getSuitableReferees()[index].getAllocatedMatches());
				*/
	}

	public Referee[] getSuitableReferees() {
		return suitableReferees;
	}
}
