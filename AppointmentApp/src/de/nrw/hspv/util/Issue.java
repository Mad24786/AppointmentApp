package de.nrw.hspv.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import de.nrw.hspv.views.AppointmentApp;

public class Issue implements Serializable, Comparable<Issue>{

	private static final long serialVersionUID = -7500772649592229468L;
	
	/** Nur zum Testen */ 
	//public static Get get = new Get();
	/*******************/
	
	/**
	 * Objektvariablen
	 * id: die zu vergebende ID
	 * name: der Name des Anliegens
	 * description: eine kurze Beschreibung des Anliegens (kann man vielleicht auch weg lassen 
	 * scheduledTime: die vorgegebene Zeit, die ein Anliegen im Kalender blockieren soll
	 */
	private int id;
	private String name;
	private String description;
	private int scheduledTime;
	
	/** 
	 * Konstruktor bei Übergabe eines Namens und einer Zeit
	 * (Name und Zeit müssen mindestens übergeben werden)
	 * ruft den Konstruktor mit Übergabe einer Beschreibung auf
	 * 
	 * @param id Die ID des Anliegens
	 * @param n Die Bezeichnung des Anliegens
	 * @param t Die Bearbeitungsdauer des Anliegens
	 */
	public Issue(String n, int t) {
		this(n, t, "./.");
	}
	
	/**
	 * Konstruktor bei Übergabe eines Namens, der Zeit sowie 
	 * einer Beschreibung
	 * 
	 * @param id Die ID des Anliegens
	 * @param n Die Bezeichnung des Anliegens
	 * @param t Die Bearbeitungsdauer des Anliegens
	 * @param d Die Beschreibung des Anliegens
	 */
	public Issue(String n, int t, String d) {
		setId(AppointmentApp.ISSUES.getNextId());
		setName(n);
		setDescription(d);
		setScheduledTime(t);
	}
	
	/** 
	 * Main-Methode zum Erzeugen und Speichern von Objekten
	 * während der Entwicklung
	 *  
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
		
//		/** Löschen */
////		try {
////			database.remove(2);
////		} catch (IOException e) {
////			System.err.println(Tools.getCurrentDateAndTime() + ": Das Objekt existiert nicht -> Löschen nicht möglich!");
////		}
//		
//		/** Anliegen erzeugen und hinzufügen */
//		new AppointmentApp();
//		Issue issue = new Issue("Perso_neu", 15);
//		Issue issue2 = new Issue("Reisepass_neu", 20);
//		Issue issue3 = new Issue("Perso_Reisepass_neu", 30);
//		AppointmentApp.ISSUES.store(issue);
//		AppointmentApp.ISSUES.store(issue2);
//		AppointmentApp.ISSUES.store(issue3);
		
		
//		ArrayList<Issue> allIssues = Get.issues.getAllAsArrayList();
//
//		for(Issue i : allIssues) {
//			System.out.println(i.getId());
//		}
//		
//		Collections.sort(allIssues);
//		
//		for(Issue i : allIssues) {
//			System.out.println(i.getId());
//		}
//				
//		/** Zieht ein Objekt mittels ID aus der Datenbank */
//		Issue issue2 = (Issue) database.get(2);
//		
//		/** Holt alle Objekte aus der DB ab */
//		ArrayList<Issue> allIssues = database.getAllAsArrayList();
//		
//		for(Issue i : allIssues) {
////			Hier kann dann eine Operation für alle Objekte ausgeführt werden
////			z.B. testweise Ausgabe der Objekte:
////			System.out.println(i.toString());
//		}
//			
//		/** Zeigt den Namen der DB-Datei sowie alle Objekte auf der Konsole an */
//		System.out.println(database.toString());
		
	}
	
	/** 
	 * Überladene toString-Methode, um 
	 * ein Objekt ausgeben zu können
	 */
	@Override
	public String toString() {
		return this.getName();
	}
	
	/**
	 * Ab hier nur noch Getter und Setter
	 * der Objektvariablen
	 */
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the scheduledTime
	 */
	public int getScheduledTime() {
		return this.scheduledTime;
	}

	/**
	 * @param scheduledTime the scheduledTime to set
	 */
	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Override
	public int compareTo(Issue o) {
		return this.getName().compareTo(o.getName());
	}	

}
