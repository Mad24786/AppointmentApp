package de.nrw.hspv.util;

import java.io.IOException;
import java.io.Serializable;


@SuppressWarnings("serial")
public class User implements Serializable{
	
	/**
	 * Objektvariablen
	 * id, name, telefon, usw...
	 * 
	 */
	private int id;
	
	
	/** 
	 * Konstruktor 
	 * hier festlegen, wie ein user-objekt erzeugt werden soll
	 */
	public User() {
		
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
//		User user = new User(...Parameter...);
//		database.store(user);
//				
//		/** Zieht ein Objekt mittels ID aus der Datenbank */
//		User user2 = (User) database.get(2);
//		
//		/** Holt alle Objekte aus der DB ab */
//		ArrayList<User> allUsers = database.getAllAsArrayList();
//		
//		for(User i : allUsers) {
////			Hier kann dann eine Operation für alle Objekte ausgeführt werden
////			z.B. testweise Ausgabe der Objekte:
////			System.out.println(i.toString());
//		}
//			
//		/** Zeigt den Namen der DB-Datei sowie alle Objekte auf der Konsole an */
//		System.out.println(database.toString());
		
	}
	
	/** 
	 * Überladene toString-Methode, um ein Objekt 
	 * testweise ausgeben zu können
	 */
	@Override
	public String toString() {
		return String.format("...");
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

}
