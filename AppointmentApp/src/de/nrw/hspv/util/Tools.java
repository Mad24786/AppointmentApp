package de.nrw.hspv.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
	
	/** Erstmal unwichtig hier
	 * 
	 * @return
	 */
	
	

	public static String getCurrentDateAndTime() {
		Date date = new Date();
	    SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy hh:mm:ss");
	    return ft.format(date);
	}
	
	

}
