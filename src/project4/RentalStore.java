package project4;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

public class RentalStore extends AbstractListModel {

	private ArrayList<DVD> DVDList;
	
	private MyDoubleLinkedList dvdLinkedList;

	private boolean filter;

	public RentalStore() {
		super();
		filter = false;
		DVDList = new ArrayList<DVD>();
		dvdLinkedList = new MyDoubleLinkedList();
		
	}

	public void add (DVD a) {
		DVDList.add(a);
		fireIntervalAdded(this, 0, DVDList.size());
	}

	public void remove (int i) {
		DVDList.remove(i);
		fireIntervalRemoved(this, 0, DVDList.size());
	}

	public DVD get (int i) {
		return DVDList.get(i);
	}

	public Object getElementAt(int i) {
		DVD unit = DVDList.get(i);

		//	String rentedOnDateStr = DateFormat.getDateInstance(DateFormat.SHORT)
		//				.format(unit.getRentedOn().getTime());

		String line = "Name: " + " " + DVDList.get(i).getNameOfRenter() + "  Title: " + DVDList.get(i).getTitle();

		if (unit instanceof Game) {
			line += "  System: " + ((Game)unit).getPlayer();
		}

		return line;
	}

	public int getSize() {
		return DVDList.size();
	}

	public ArrayList<DVD> getList() {
		return DVDList;
	}

	public void saveAsSerializable(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(DVDList);
			os.close();
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error saving db");
		}
	}

	public void loadFromSerializable(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			DVDList = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, DVDList.size() - 1);
			is.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error loading db");
		}
	}
	
	public void saveAsText(String fileName) {
		DNode curNode = dvdLinkedList.getTop();
		
		try {
			FileOutputStream outFile = new FileOutputStream(fileName);
			ObjectOutputStream objOut = new ObjectOutputStream(outFile);
			
			//writes each node's dvd as an object
			while (curNode != null) 
				objOut.writeObject(curNode.getDVD());
			
			objOut.close();
		} 
		catch(Exception e) {
			e.printStackTrace(); // Prints exceptions to system.out
		}	
	}
	
	public void loadFromText(String fileName) {
		dvdLinkedList = new MyDoubleLinkedList();
		
		try {
			FileInputStream inputFile = new FileInputStream(fileName);
			ObjectInputStream objInput = new ObjectInputStream(inputFile);

			while (objInput.readObject() != null)
				if (objInput.readObject() instanceof DVD)
					dvdLinkedList.add((DVD)objInput.readObject());
			objInput.close();
		}
		catch(Exception e) {
			e.printStackTrace(); // Prints exceptions to system.out
		}
	}
	
}
