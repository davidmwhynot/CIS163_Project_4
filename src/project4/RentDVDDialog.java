package project4;

/***********************************************************************
RentDVDDialog is the popup menu for creating a new DVD rental.

@author David Whynot
@version 7/25/2018
***********************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class RentDVDDialog extends JDialog implements ActionListener {

    private JTextField titleTxt;
    private JTextField renterTxt;
    private JTextField rentedOnTxt;
    private JTextField dueBackTxt;

    private JButton okButton;
    private JButton cancelButton;
    private boolean closeStatus;

    private Container con;
    private JPanel textPanel;

    private DVD unit;

    public RentDVDDialog(JFrame parent, DVD d) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        con = getContentPane();

        setTitle("Rent a DVD:");
        closeStatus = false;
        setSize(400, 200);

        unit = d;
        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display text fields

        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6, 2));

        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe", 30);
        textPanel.add(renterTxt);

        textPanel.add(new JLabel("Title of DVD:"));
        titleTxt = new JTextField("Avengers", 30);
        textPanel.add(titleTxt);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(df.format(date), 30);
        textPanel.add(rentedOnTxt);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1); // number of days to add
        date = c.getTime();

        textPanel.add(new JLabel("Due Back: "));
        dueBackTxt = new JTextField(df.format(date), 15);
        textPanel.add(dueBackTxt);

        con.add(textPanel, BorderLayout.CENTER);

        // Instantiate and display two buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        con.add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setSize(300, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        // if OK clicked the fill the object
        closeStatus = true;
        if (button == okButton) {
            // save the information in the object

            unit.setNameOfRenter(renterTxt.getText());
            unit.setTitle(titleTxt.getText());

            GregorianCalendar boughtCal = textToCalendar(
                rentedOnTxt.getText()
            );
            GregorianCalendar dueCal = textToCalendar(
                dueBackTxt.getText()
            );

            unit.setBought(boughtCal);

            // validate due date
            if(!(dueCal.compareTo(boughtCal) < 0)) {
                unit.setDueBack(dueCal);
                closeStatus = true;
                // make the dialog disappear
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Due date must be after bought date!"
                );
            }

        }

        if(button == cancelButton) {
            closeStatus = false;
            dispose();
        }

    }

    /*******************************************************************
     * Converts a String input into a GregorianCalendar date and
     * returns it.
     * @param text is a date in the form of a string
     * @return GregorianCalendar date converted from text input. Will
     * return today's date if the input is invalid
    *******************************************************************/
    private GregorianCalendar textToCalendar(String text) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(parser.parse(text));
        }
        catch (ParseException pe) {
            JOptionPane.showMessageDialog(
                null,
                "Could not parse input date!"
            );
            return new GregorianCalendar();
        }
        return cal;
    }

    public boolean closeOK() {
        return closeStatus;
    }
}
