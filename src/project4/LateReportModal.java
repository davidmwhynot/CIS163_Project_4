package project4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;


public class LateReportModal extends JDialog {

	/**
	 * JDialog's content pane
	 */
	private Container con;

	/**
	 * container for the the report output
	 */
	private JPanel displayList;

	public LateReportModal(JFrame parent, GregorianCalendar d, RentalStore list) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		con = getContentPane();
		displayList = new JPanel();

		setTitle("Late Unit Report");
		setSize(400, 400);

		// iterate through the input list
		int count = 0;
		for(DVD i : list.getList()) {
			// check if the dvd/game is late
			if(i.getDueBack().compareTo(d) < 0) {
				// this dvd/game is late...
				// add the necessary info for this dvd/game
				// to the output panel
				JPanel row = new JPanel();
				row.setLayout(new GridLayout(1, 4, 5, 3));
				row.add(new JLabel("Name: " + i.getNameOfRenter()));
				row.add(new JLabel("Title: " + i.getTitle()));
				if(i instanceof Game) {
					row.add(new JLabel("System: " + ((Game)i).getPlayer()));
				} else {
					row.add(new JLabel("DVD"));
				}
				// show number of days late
				row.add(new JLabel("Days late: "
					+ daysBetween(i.getDueBack().getTime(), d.getTime())
				));
				displayList.add(row);
				++count;
			}
		}
		displayList.setLayout(new GridLayout(count, 1, 5, 5));
		con.add(displayList, BorderLayout.CENTER);

		setVisible(true);
	}
	private int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }

}
