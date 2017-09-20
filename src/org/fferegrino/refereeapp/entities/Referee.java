package org.fferegrino.refereeapp.entities;

import java.util.ArrayList;

public class Referee {
	
	private String firstName;
	private String lastName;
	private int sequenceNumber;
	private AwardingBody qualificationAwardingBody;
	private int qualificationLevel;
	private Area home;
	private String localities;
	private int allocatedMatches;
	public ArrayList<Match> newMatches;
	
	public boolean willingToTravelTo(Area area) {
		int loc = area == Area.NORTH ? 0 : area == Area.CENTRAL ? 1 : 2;
		return localities.charAt(loc) == 'Y';
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + " (" + getHome().getRealName() + "). Matches: " + getAllocatedMatches();
	}

	public String getId() {
		return  String.valueOf(getFirstName().charAt(0)) +  String.valueOf(getLastName().charAt(0)) + String.valueOf(getSequenceNumber());
	}

	public String getQualification() {
		return getQualificationAwardingBody().name() + String.valueOf(getQualificationLevel());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public AwardingBody getQualificationAwardingBody() {
		return qualificationAwardingBody;
	}

	public void setQualificationAwardingBody(AwardingBody qualificationAwardingBody) {
		this.qualificationAwardingBody = qualificationAwardingBody;
	}

	public int getQualificationLevel() {
		return qualificationLevel;
	}

	public void setQualificationLevel(int qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}

	public Area getHome() {
		return home;
	}

	public void setHome(Area home) {
		this.home = home;
	}

	public String getLocalities() {
		return localities;
	}

	public void setLocalities(String localities) {
		this.localities = localities;
	}

	public int getAllocatedMatches() {
		return allocatedMatches;
	}

	public void setAllocatedMatches(int allocatedMatches) {
		this.allocatedMatches = allocatedMatches;
	}
}
