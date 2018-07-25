package project4;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalStore extends AbstractListModel {

    private MyDoubleLinkedList<DVD> DVDList;

    private boolean filter;

    public RentalStore() {
        super();
        filter = false;
        DVDList = new MyDoubleLinkedList<DVD>();
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
                fireIntervalAdded(this, 0, DVDList.size() - 1);
            }
            is.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading db");
        }
    }

    public void saveAsText(String filename) {
        // TODO
    }

    public void loadFromText(String filename) {
        // TODO
    }


}
