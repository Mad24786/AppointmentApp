package de.nrw.hspv.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import de.nrw.hspv.views.AppointmentApp;

/**
 * A utility for this application allowing you to store 
 * all kind of serializable object in one or more files 
 * under a certain key.
 * 
 * @author Mathias Fernahl
 * @version 17 May 2021
 * @see java.io.Serializable
 */
public class FileDatabase<T> implements Serializable {
	
	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 3L;
	
	/**
	 * The file to store objects.
	 */
	private File storageFile;
	
	/**
	 * HashMap with a numeric key and a object. 
	 */
	private HashMap<Integer, T> storageMap;

	/**
     * Creates a FileStorage. It allows you to store your serializable object in a file using a key
     * for identification and to read it somewhen later.
     * 
     * All data stored in this FileStorage will instantly be stored in the given file.
     * 
     * @param filepath 					The path of the file your data shall be stored in
     * @throws IOException 				if the file cannot be created
     */
	public FileDatabase(String filepath) throws IOException {
		this(new File(filepath));
	}
	
	/**
	 * Creates a FileStorage. It allows you to store a serializable object in a file using a key
     * for identification and to read it somewhen later.
     * 
     * All data stored in this FileStorage will instantly be stored in the given file.
     * 
	 * @param file			The file your data shall be stored in
     * @throws IOException 	if the file cannot be created
	 */
	public FileDatabase(File file) throws IOException {
		this.storageFile = file;

		if (storageFile.isDirectory()) {
			throw new IOException("The given String is a directory");
		}

		if (storageFile.length() == 0 || storageFile.createNewFile()) {
			storageMap = new HashMap<Integer, T>();
			save();
		} else {
			load();
		}
		
	}

	/**
     * Stores the FileStorage in the file on disk
     * 
	 * @throws IOException		if there is a problem with output streams
	 */
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(storageFile);
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));
		oos.writeObject(storageMap);
		oos.flush();
		oos.close();
		fos.close();
	}

	/**
     * Loads the FileStorage from the file
     */
	@SuppressWarnings("unchecked")
	private void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(storageFile)));
			storageMap = (HashMap<Integer, T>) ois.readObject();
			ois.close();
			if (AppointmentApp.logEvents)
				AppointmentApp.log.log(Level.INFO, storageFile.getName() + " loaded successfully");
		} catch (Exception e) {
			if (AppointmentApp.logEvents)
				AppointmentApp.log.log(Level.SEVERE, "Error while loading data");
		}
	}
	
	/**
	  * Stores an Object using a key for later identification.
	  * 
	  * @param o The Object to store.
	  * @throws IOException
	  */
	public void store(T o) throws IOException{
		// we need a key first from getNextId()
		store(getNextId(), o);
	}
	
	/**
	 * Stores an Object using a key for later identification.
	 * 
	 * @param key		Auto generated key.
	 * @param o			The Object to store.
	 * @throws IOException
	 */
	public void store(int key, T o) throws IOException {
		if(get(key) != null)
			throw new IOException("key already exists");
		// put key and object to HashMap
		storageMap.put(key, o);
		// save the file and log
		save();
		if (AppointmentApp.logEvents)
			AppointmentApp.log.log(Level.INFO, o.toString() + " #" + key + " saved.");
	}
	
	/**
	 * Returns an Object by using its key.
	 * 
	 * @param key 		The key of the Object to return.
	 * @return			The Object from storageFile.
	 */
	public T get(int key) {
		return storageMap.get(key);
	}
	
	/**
	 * Generates automatically the next ID in current storageFile.
	 * 
	 * @return 	The next higher ID as last element.
	 */
	public int getNextId() {
		// first id is 0
		int nextId = 0;
		Iterator<Entry<Integer, T>> it = storageMap.entrySet().iterator();
		// go through all elements in storageMap
	    while (it.hasNext()) {
	    	Map.Entry<Integer, T> pair = (Map.Entry<Integer, T>)it.next();
	    	// save last id of this map and add 1
	        nextId = (pair.getKey()+1);
	    }
		return nextId;
	}

	/**
	 * Returns all elements from storageMap for further use in the application.
	 * 
	 * @return		ArrayList with all elements from storageMap.
	 */
	public ArrayList<T> getAllAsArrayList() {
		ArrayList<T> result = new ArrayList<T>();
		for (T c : storageMap.values()) {
			result.add(c);
		}
		return result;
	}

	/**
	 * Deletes an Object by using its key.
	 * 
	 * @param key				The ID of the Object to delete.
	 * @throws IOException
	 */
	public void remove(int key) throws IOException {
		if(get(key) == null)
			throw new IOException("key does not exist");
		T o = get(key);
		storageMap.remove(key);
		save();
		AppointmentApp.log.log(Level.INFO, o.toString() + " #" + key + " deleted.");
	}
	
	/**
	 * Returns the size of current FileStorage.
	 * 
	 * @return 		The size of storageMap as an Integer.
	 */
	public int getSize() {
		return storageMap.size();
	}

}