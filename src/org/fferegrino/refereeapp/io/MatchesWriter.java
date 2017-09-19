package org.fferegrino.refereeapp.io;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.fferegrino.refereeapp.entities.Match;

public class MatchesWriter {
	public void writeMatches(PrintWriter writer, ArrayList<Match> matches) {
	    writer.println(
	    		"Week" + " " +
				"Level" + " " + 
				"Area" + " " +
				"Referee1" + " " + 
				"Referee2" );
		for(int i = 0; i < matches.size(); i++)
		{
			Match m = matches.get(i);
		    writer.println(
		    		String.valueOf(m.week) + " " +
    				m.level + " " + 
    				m.area.name() + " " +
    				m.referee1.getFirstName() + " " + m.referee1.getLastName() + " " +
    				m.referee2.getFirstName() + " " + m.referee2.getLastName() + " " );
		}
	    writer.close();
	}
}
