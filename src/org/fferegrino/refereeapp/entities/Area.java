package org.fferegrino.refereeapp.entities;

public enum Area {
	NORTH, CENTRAL, SOUTH;
	
	public String getRealName() {
		String name = this.name();
		return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	}
}