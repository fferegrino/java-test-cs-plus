package org.fferegrino.refereeapp.io;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.fferegrino.refereeapp.entities.*;

public class RefereeWriter {
	public void writeReferees(PrintWriter writer, ArrayList<Referee> referees) {
		for(int i = 0; i < referees.size(); i++)
		{
			Referee r = referees.get(i);
		    writer.println(
		    		r.getId() + " " + 
		    		r.getFirstName() + " " + 
		    		r.getLastName() + " " +
		    		r.getQualification() + " " +
		    		String.valueOf(r.getAllocatedMatches()) + " " +
		    		r.getHome().name() + " " +
		    		r.getLocalities());
		}
	    writer.close();
	}
}
