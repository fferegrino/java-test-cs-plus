package org.fferegrino.refereeapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.fferegrino.refereeapp.io.RefereeReader;
import org.fferegrino.refereeapp.entities.*;;

public class Main {

	public static void main(String [] args) throws Exception{
		

		   File file = new File("RefereesIn.txt");
		   Scanner scanner = new Scanner(file);
		   RefereeReader reader = new RefereeReader();
		   ArrayList<Referee> referees = reader.getReferees(scanner);
		   
		   for(int i = 0; i < referees.size(); i++)
		   {
			   Referee r = referees.get(i);
			   System.out.println(r.toString());
		   }
		
	}
}
