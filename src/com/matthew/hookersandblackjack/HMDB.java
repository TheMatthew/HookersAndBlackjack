package com.matthew.hookersandblackjack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HMDB {

	private java.io.File dbFile;

	public HMDB(String fileName) throws IOException {
		super();
		dbFile = new File(fileName);
		if (!dbFile.exists())
			dbFile.createNewFile();
	}

	public Long get(String key) {
		java.io.BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(dbFile));
			String nextLine = reader.readLine();
			while (nextLine != null) {
				String tokens[] = nextLine.split(" ");
				String oldKey = tokens[0];
				Long money = Long.parseLong(tokens[1]);
				if (oldKey.equals(key)) {
					reader.close();
					return money;
				}
				nextLine = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		put(key, 50L);
		return 50L;
	}

	public Long put(String key, Long value) {
		String oldFile = "";
		java.io.BufferedReader reader;
		java.io.BufferedWriter writer;
		try {
			reader = new BufferedReader(new FileReader(dbFile));
			boolean found = false;
			String nextLine = reader.readLine();
			while (nextLine != null && !nextLine.isEmpty()) {
				String tokens[] = nextLine.split(" ");
				if (tokens.length == 2) {

					String oldKey = tokens[0];
					Long money = Long.parseLong(tokens[1]);
					if (oldKey.equals(key)) {
						found = true;
						oldFile += key + " " + value.toString() + "\n";
					} else
						oldFile += key + " " + money.toString() + "\n";
					nextLine = reader.readLine();
				}else{
					System.out.println( "Error in db, please contact your admin.");
				}
				if (!found)
					oldFile += key + " " + value.toString() + "\n";
				reader.close();
				writer = new BufferedWriter(new FileWriter(dbFile));
				writer.write(oldFile);
				writer.close();
			}
			
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
