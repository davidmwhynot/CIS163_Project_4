package project4;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.util.Scanner;


public class RentGameDialog  extends JDialog implements ActionListener {

    /** List of available PlayerTypes that will appear in the
    playerList **/
    private final String[] SYSTEM_LIST = {"Xbox One", "Xbox 360",
    "PS4", "Nintendo Switch", "WiiU"};

    /** Text field where user enters the name of the rented game **/
    private JTextField titleTxt;

    /** JList for the user to choose the game system the game is for **/
    private JTextField playerTxt;

    /** Text field where user enters their own name **/
    private JTextField renterTxt;

    /** Text field that displays the date the game was rented on **/
    private JTextField rentedOnTxt;

    /** Text field that displays the date the game is due back **/
    private JTextField dueBackTxt;

    /** Button to confirm and submit rental dialog **/
    private JButton okButton;

    /** Button to cancel the current dialog **/
    private JButton cancelButton;

    /** closeStatus will be 'true' if the dialog closed properly **/
    private boolean closeStatus;

    private Container con;

    private Game unit;
    public RentGameDialog(JFrame parent, Game d) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        con = getContentPane();

        setTitle("Rent a Game:");
        closeStatus = false;
        setSize(400, 200);

        unit = d;
        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display text fields

        JPanel textPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();

        textPanel.setLayout(new GridLayout(6, 2));

        /* Text box for input of renter's name */
        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe", 30);
        textPanel.add(renterTxt);

        /* Text box for input of game title */
        textPanel.add(new JLabel("Title of Game:"));
        titleTxt = new JTextField("Avengers", 30);
        textPanel.add(titleTxt);

        /* Text box for input of game system */
        textPanel.add(new JLabel("Game System:"));
        playerTxt = new JTextField("Xbox One", 30);
        textPanel.add(playerTxt);

        /* Gets today's date */
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        /* Displays the date the game was rented on */
        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(df.format(date), 30);
        textPanel.add(rentedOnTxt);

        /* Calculates the date the game is due back */
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);  // number of days to add
        date = c.getTime();

        /* Displays the date the game is due to be returned */
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
        if (button == okButton) {
            // save the information in the object
            closeStatus = false;

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
                // User input for PlayerType parsed
                playerTxt.setText(playerTxt.getText().toLowerCase());

                if (playerTxt.getText().contains("xbox1")) {
                    unit.setPlayerType(PlayerType.XBox1);
                    closeStatus = true;
                } else if (playerTxt.getText().contains("xbox360")) {
                    unit.setPlayerType(PlayerType.Xbox360);
                    closeStatus = true;
                } else if (playerTxt.getText().contains("wiiu")) {
                    unit.setPlayerType(PlayerType.WiiU);
                    closeStatus = true;
                } else if (playerTxt.getText().contains("ps4")) {
                    unit.setPlayerType(PlayerType.PS4);
                    closeStatus = true;
                } else if (playerTxt.getText().contains("nintendoswitch")) {
                    unit.setPlayerType(PlayerType.NintendoSwitch);
                    closeStatus = true;
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Game System Not Found"
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Due date must be after bought date!"
                );
            }
            if(closeStatus) {
                // make the dialog disappear
                dispose();
            }
        }

        if(button == cancelButton) {
            closeStatus = false;
            dispose();
        }

    }

    /*******************************************************************
    * Converts a String input into a GregorianCalendar date
     * and returns it.
     * @param text is a date in the form of a string
     * @return GregorianCalendar date converted from text input. Will
     * return today's date if the input is invalid
    *******************************************************************/
    private GregorianCalendar textToCalendar(String text) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(parser.parse(text));
        } catch (ParseException pe) {
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
