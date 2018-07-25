package project4;

/***********************************************************************
RentalStore is the connector class between our LinkedList and the GUI.
Contains database-like functionality. Also creates the tableModel for
the GUI based on the doubly linked list of rentals.

@author David Whynot
@version 7/25/2018
***********************************************************************/

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import java.util.*;

public class RentalStore extends AbstractTableModel {

    /**
    * Double linked list for storing rentals in
    */
    private MyDoubleLinkedList<DVD> DVDList;

    /**
    * Names of the columns for the table in the GUI
    */
    private String[] colNames = {
        "Name",
        "Title",
        "Bought",
        "Due",
        "System"
    };

    /*******************************************************************
    Default constructor, creates a new linked list for the database
    and calls the superclass constructor.
    *******************************************************************/
    public RentalStore() {
        super();
        DVDList = new MyDoubleLinkedList<DVD>();
    }

    /*******************************************************************
    Override method for getting the names of the columns for the table
    @param x the column index to get the name of
    @return name of the column
    *******************************************************************/
    public String getColumnName(int x) {
        return colNames[x];
    }

    /*******************************************************************
    Adds a DVD to the database and fire notification event for the model
    @param a the DVD to add
    *******************************************************************/
    public void add (DVD a) {
        DVDList.add(a);
        fireTableRowsInserted(0, DVDList.size());
    }

    /*******************************************************************
    Removes the DVD at the specified index from the database and
    notifies the frontend of the change by firing the required event.
    @param i the index of the DVD to be deleted.
    *******************************************************************/
    public void remove (int i) {
        DVDList.remove(i);
        fireTableRowsDeleted(0, DVDList.size());
    }

    /*******************************************************************
    Getter for a DVD at the given index
    @param i the index of the DVD to get
    @return the DVD object
    *******************************************************************/
    public DVD get (int i) {
        return DVDList.get(i);
    }

    /*******************************************************************
    Gets the size of the database/number of rows in the table
    @return the number of rows in the database
    *******************************************************************/
    public int getRowCount() {
        return this.getSize();
    }

    /*******************************************************************
    Gets the number of columns in the table.
    @return 5, the number of fields in our database
    *******************************************************************/
    public int getColumnCount() {
        return 5;
    }

    /*******************************************************************
    Gets the value of a given cell in the table
    @param x the x value of the cell
    @param y the y value of the cell
    @return an Object (string) that is the value to be displayed in the
    table for this cell
    *******************************************************************/
    public Object getValueAt(int x, int y) {
        DVD unit = DVDList.get(x);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        switch(y) {
            case 0:
                return unit.getNameOfRenter();
            case 1:
                return unit.getTitle();
            case 2:
                GregorianCalendar boughtCal = unit.getBought();
                df.setCalendar(boughtCal);
                return df.format(boughtCal.getTime());
            case 3:
                GregorianCalendar dueCal = unit.getDueBack();
                df.setCalendar(dueCal);
                return df.format(dueCal.getTime());
            case 4:
                if (unit instanceof Game) {
                    return ((Game)unit).getPlayer();
                } else {
                    return "DVD";
                }
            default:
                return "Error retrieving data from table";
        }
    }

    public Object getElementAt(int i) {
        DVD unit = DVDList.get(i);

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        GregorianCalendar boughtCal = unit.getBought();
        GregorianCalendar dueCal = unit.getDueBack();

        df.setCalendar(boughtCal);
        String bought = df.format(boughtCal.getTime());

        df.setCalendar(dueCal);
        String due = df.format(dueCal.getTime());

        String line =
            "Name: " + unit.getNameOfRenter()
            + "   ||   Title: " + unit.getTitle()
            + "   ||   Bought: " + bought
            + "   ||   Due: " + due;

        if (unit instanceof Game) {
            line += "   ||   System: " + ((Game)unit).getPlayer();
        }

        return line;
    }

    public int getSize() {
        return DVDList.size();
    }

    public MyDoubleLinkedList<DVD> getList() {
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

            DVDList = (MyDoubleLinkedList<DVD>) is.readObject();
            if(DVDList.size() > 0) {
                fireTableRowsInserted(0, DVDList.size() - 1);
            }
            is.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading db");
        }
    }

    public void saveAsText(String fileName) {
        DNode curNode = DVDList.getTop();

        try {
            FileOutputStream outFile = new FileOutputStream(fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(outFile);

            // writes each node's dvd as an object
            while (curNode != null) {
                objOut.writeObject(curNode.getDVD());
                curNode = curNode.getNext();
            }

            objOut.close();
        }
        catch(Exception e) {
            e.printStackTrace(); // Prints exceptions to system.out
        }
    }

    public void loadFromText(String fileName) {
        DVDList = new MyDoubleLinkedList();

        try {
            FileInputStream inputFile = new FileInputStream(fileName);
            ObjectInputStream objInput = new ObjectInputStream(
                inputFile
            );
            Object readObj = objInput.readObject();
            while (readObj != null) {
                if (readObj instanceof DVD) {
                    DVDList.add((DVD)readObj);
                }
                readObj = objInput.readObject();
            }
            objInput.close();
        }
        catch(Exception e) {
            e.printStackTrace(); // Prints exceptions to system.out
        }
    }


}
