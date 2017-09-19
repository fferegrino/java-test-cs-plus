package org.fferegrino.refereeapp.entities;

public class Match {
	public static final String LEVEL_JUNIOR = "Junior";
	public static final String LEVEL_SENIOR = "Senior";
	public static final String[] LEVELS = { LEVEL_JUNIOR, LEVEL_SENIOR };
	
	public int week;
	public Area area;
	public String level;
	public Referee referee1;
	public Referee referee2;
}