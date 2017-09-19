package org.fferegrino.refereeapp.entities;

import java.util.ArrayList;

public class Referee {
	
	public String firstName;
	public String lastName;
	public int sequenceNumber;
	private String qualification;
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

	public String getId() {
		return  String.valueOf(firstName.charAt(0)) +  String.valueOf(lastName.charAt(0)) + String.valueOf(sequenceNumber);
	}

	public String getQualification() {
		return qualificationAwardingBody.name() + String.valueOf(qualificationLevel);
	}
}
