package org.fferegrino.refereeapp.io;

import java.util.ArrayList;
import java.util.Scanner;
import org.fferegrino.refereeapp.entities.*;

public class RefereeReader {

	public ArrayList<Referee> getReferees(Scanner s) {
		ArrayList<Referee> referees = new ArrayList<Referee>();
		while (s.hasNext()) {
			String read = s.nextLine();
			String[] fields = read.split("\\s");
			Referee r = new Referee();
			r.firstName = fields[1];
			r.lastName = fields[2];
			r.sequenceNumber = fields[0].charAt(2) - '0';
			r.qualificationLevel = fields[3].charAt(3) - '0';
			String awardingBody = fields[3].substring(0, 3);

			if(awardingBody == "NJB")
				r.qualificationAwardingBody = AwardingBody.NJB;
			else if(awardingBody == "IJB")
				r.qualificationAwardingBody = AwardingBody.IJB;
			
			r.allocatedMatches = Integer.parseInt(fields[4]);

			String home = fields[5];
			if(home == "North")
				r.home = Area.NORTH;
			else if(home == "Central")
				r.home = Area.CENTRAL;
			else if(home == "South")
				r.home = Area.SOUTH;
			
			r.localities = fields[6];
			
			referees.add(r);
		}
		s.close();
		return referees;
	}
}
