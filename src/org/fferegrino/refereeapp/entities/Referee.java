package org.fferegrino.refereeapp.entities;

import java.util.ArrayList;

public class Referee {
	public String id;
	public String firstName;
	public String lastName;
	public int sequenceNumber;
	public String qualification;
	public AwardingBody qualificationAwardingBody;
	public int qualificationLevel;
	public Area home;
	public String localities;
	public int allocatedMatches;
	public ArrayList<Match> newMatches; 
	
	@Override
	public String toString() {
		return "Name: " + firstName;
	}
	}
