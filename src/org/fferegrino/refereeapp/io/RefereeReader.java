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
			r.setFirstName(fields[1]);
			r.setLastName(fields[2]);
			r.setSequenceNumber(fields[0].charAt(2) - '0');
			r.setQualificationLevel(fields[3].charAt(3) - '0');
			String awardingBody = fields[3].substring(0, 3);

			if(awardingBody.equals("NJB"))
				r.setQualificationAwardingBody(AwardingBody.NJB);
			else if(awardingBody.equals("IJB"))
				r.setQualificationAwardingBody(AwardingBody.IJB);
			
			r.setAllocatedMatches(Integer.parseInt(fields[4]));

			String home = fields[5];
			if(home.equals("North"))
				r.setHome(Area.NORTH);
			else if(home.equals("Central"))
				r.setHome(Area.CENTRAL);
			else if(home.equals("South"))
				r.setHome(Area.SOUTH);
			
			r.setLocalities(fields[6]);
			
			referees.add(r);
		}
		s.close();
		return referees;
	}
}
