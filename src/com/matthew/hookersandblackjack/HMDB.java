/**
 * 
 */
package com.matthew.hookersandblackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class HMDB {

	private Properties prop;
	private File dbFile;

	public HMDB(String fileName) throws IOException {
		super();
		dbFile = new File(fileName);
		if (!dbFile.exists())
			dbFile.createNewFile();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	synchronized public Long get(String key) {
		Long ret = null;
		FileReader reader;
		try {
			reader = new FileReader(dbFile);
			prop.load(reader);
			ret = (Long) prop.get(key);
			if (ret == null) {
				prop.put(key, 50L);
				ret = (Long) prop.get(key);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	synchronized public Long put(String key, Long value) {
		FileReader reader;
		FileWriter writer;
		try {
			reader = new FileReader(dbFile);
			prop.load(reader);
			reader.close();
			prop.put(key, value);
			writer = new FileWriter(dbFile);
			prop.store(writer, key + " new value " + value.toString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
}
