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

import de.nrw.hspv.exception.DatabaseException;

public class FileDatabase<O> implements Serializable {
	
	private static final long serialVersionUID = 3L;
	
	private File storageFile;
	private HashMap<Integer, O> storageMap;


	public FileDatabase(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileDatabase(File file) throws DatabaseException, IOException {
		this.storageFile = file;

		if (storageFile.isDirectory()) {
			throw new DatabaseException("The given String is a directory");
		}

		if (storageFile.length() == 0 || storageFile.createNewFile()) {
			storageMap = new HashMap<Integer, O>();
			save();
		} else {
			load();
		}
		
	}

	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(storageFile);
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));
		oos.writeObject(storageMap);
		oos.flush();
		oos.close();
		fos.close();
	}

	@SuppressWarnings("unchecked")
	private void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(storageFile)));
			storageMap = (HashMap<Integer, O>) ois.readObject();
			ois.close();
			System.err.println(Tools.getCurrentDateAndTime() + ": " + storageFile.getName() + " geladen");
		} catch (Exception e) {
			System.err.println("Fehler load()");
			e.printStackTrace();
		}
	}
	
	public void store(O o) throws IOException{
		store(getNextId(), o);
	}

	public void store(int key, O o) throws IOException {
		if(get(key) != null)
			throw new IOException("key already exists");
		
		storageMap.put(key, o);
		save();
		System.err.println(Tools.getCurrentDateAndTime() + ": Objekt (ID: " + key + ") gespeichert.");
	}

	public Object get(int key) {
		return storageMap.get(key);
	}
	
	public int getNextId() {
		int nextId = 0;
		Iterator<Entry<Integer, O>> it = storageMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry<Integer, O> pair = (Map.Entry<Integer, O>)it.next();
	        nextId = (pair.getKey()+1);
	    }
		return nextId;
	}

	public ArrayList<O> getAllAsArrayList() {
		ArrayList<O> result = new ArrayList<O>();
		for (O c : storageMap.values()) {
			result.add(c);
		}
		return result;
	}

	public HashMap<Integer, O> getAll() {
		return storageMap;
	}

	public void printAll() {
		System.out.println(this);
	}

	public void remove(int key) throws IOException {
		if(get(key) == null)
			throw new IOException("key does not exist");
		
		storageMap.remove(key);
		save();
		System.err.println(Tools.getCurrentDateAndTime() + ": Objekt (ID: " + key + ") entfernt.");
	}

	public boolean hasKey(int key) {
		return storageMap.containsKey(key);
	}

	public boolean hasObject(Object o) {
		return storageMap.containsValue(o);
	}

	public int getSize() {
		return storageMap.size();
	}

	@Override
	public String toString() {
		String result = "FileStorage @ " + storageFile.getAbsolutePath() + "\n";
		for (int cKey : storageMap.keySet()) {
			result += "ID: " + cKey + "\n" + storageMap.get(cKey) + "\n\n";
		}
		return result.trim();
	}

}