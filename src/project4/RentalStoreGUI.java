package project4;

/***********************************************************************
RentalStoreGUI is the front end for the rental store application. Is a
JFrame with a menu bar (for various actions and user interaction) and a
table (for displaying active rentals). Allows the user to create new
rentals and return existing rentals.

@author David Whynot
@version 7/25/2018
***********************************************************************/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;

public class RentalStoreGUI extends JFrame implements ActionListener {

    /**
    * Holds menu bar
    */
    private JMenuBar menus;

    /**
    * menus in the menu bar
    */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /**
    * menu items in each of the menus
    */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;
    private JMenuItem rentDVD;
    private JMenuItem rentGame;
    private JMenuItem returnItem;
    private JMenuItem lateReport;
    private JMenuItem undo;



    /**
    * Holds the table engine
    */
    private RentalStore table;

    /**
    * Holds JTable
    */
    private JTable JTableArea;

    /**
    * Holds previous actions for undo
    */
    private ArrayList<Action> actions;

    /*******************************************************************
     * Default Constructor for the RentalStore GUI
    *******************************************************************/
    public RentalStoreGUI() {

        // initialize actions array
        actions = new ArrayList<Action>();

        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open Database");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save Database");
        openTextItem = new JMenuItem("Open Text");
        saveTextItem = new JMenuItem("Save Text");
        rentDVD = new JMenuItem("Rent DVD");
        rentGame = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");
        lateReport = new JMenuItem("Create Late Report");
        undo = new JMenuItem("Undo");

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        actionMenu.add(undo);
        actionMenu.addSeparator();
        actionMenu.add(rentDVD);
        actionMenu.add(rentGame);
        actionMenu.addSeparator();
        actionMenu.add(returnItem);
        actionMenu.addSeparator();
        actionMenu.add(lateReport);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);
        exitItem.addActionListener(this);
        rentDVD.addActionListener(this);
        rentGame.addActionListener(this);
        returnItem.addActionListener(this);
        lateReport.addActionListener(this);
        undo.addActionListener(this);

        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding table to the GUI1024
        table = new RentalStore();
        JTableArea = new JTable(table);
        add(new JScrollPane(JTableArea));

        setVisible(true);
        setSize(500, 500);

    }

    /*******************************************************************
     * Event handler method.
     * @param e - the actionevent that was taken by the user that we are
     * going to handle
    *******************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        // MenuBar options
        // File menu options
        if (src == openSerItem || src == openTextItem) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename =
                    chooser.getSelectedFile().getAbsolutePath();
                if (src == openSerItem) {
                    table.loadFromSerializable(filename);
                } else {
                    table.loadFromText(filename);
                }
            }
        }

        if (src == saveSerItem || src == saveTextItem) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename =
                    chooser.getSelectedFile().getAbsolutePath();
                if (src == saveSerItem) {
                    table.saveAsSerializable(filename);
                } else {
                    table.saveAsText(filename);
                }
            }
        }

        if (src == exitItem) {
            System.exit(0);
        }

        // Action menu options
        // XXX undo XXX
        if (src == undo) {
            undo();
        }
        // XXX rent DVD XXX
        if (src == rentDVD) {
            DVD dvd = new DVD();
            RentDVDDialog dialog = new RentDVDDialog(this, dvd);
            if(dialog.closeOK()) {
                table.add(dvd);
                actions.add(new Action(ActionType.RENT, dvd));
            }
        }
        // XXX rent Game XXX
        if (src == rentGame) {
            Game game = new Game();
            RentGameDialog dialog = new RentGameDialog(this, game);
            if(dialog.closeOK()) {
                table.add(game);
                actions.add(new Action(ActionType.RENT, game));
            }
        }

        // XXX return XXX
        if (src == returnItem) {
            int index = JTableArea.getSelectedRow();
            if(index < 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "Please select a unit to return from the table."
                );
            } else {
                try {
                    GregorianCalendar date = textToCalendar(
                        JOptionPane.showInputDialog(
                            "Enter return date (MM/DD/YYYY):"
                        )
                    );
                    DVD unit = table.get(index);

                    if(unit.getBought().compareTo(date) <= 0) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Thanks " + unit.getNameOfRenter()
                            + " for returning " + unit.getTitle()
                            + ", you owe: " + unit.getCost(date)
                            + " dollars"
                        );
                        actions.add(new Action(
                            ActionType.RETURN,
                            unit
                        ));
                        table.remove(index);
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "Return date must be after bought date!"
                        );
                    }
                }
                catch(NullPointerException npe) {
                    npe.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "Return cancelled."
                    );
                }
            }
        }

        // XXX late report XXX
        if(src == lateReport) {
            GregorianCalendar date = new GregorianCalendar();
            String inputDate = JOptionPane.showInputDialog(
                "Enter report date (MM/DD/YYYY):"
            );
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date newDate = df.parse(inputDate);
                date.setTime(newDate);
                LateReportModal reportModal = new LateReportModal(
                    this,
                    date,
                    table
                );
            }
            catch (ParseException pe) {
                JOptionPane.showMessageDialog(
                    null,
                    "Could not parse input date!"
                );
            }
            catch(NullPointerException npe) {
                JOptionPane.showMessageDialog(
                    null,
                    "Report generation cancelled."
                );
            }
        }
    }

    /*******************************************************************
     * Undo a rental or return
    *******************************************************************/
    private void undo() {
        if(actions.size() > 0) {
            Action action = actions.get(actions.size() - 1);
            actions.remove(actions.size() - 1);
            if(action.getType() == ActionType.RENT) {
                undoRent(action);
            } else if(action.getType() == ActionType.RETURN) {
                undoReturn(action);
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Error with undo: no action type."
                );
            }
        }
    }

    /*******************************************************************
     * Undo a rental
     * @param action - the action object we want to undo
    *******************************************************************/
    private void undoRent(Action action) {
        if(action.getAction() == table.get(table.getSize() - 1)) {
            table.remove(table.getSize()-1);
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Error with undo: action does not match unit in table."
            );
        }
    }

    /*******************************************************************
     * Undo a return
     * @param action - the action object we want to undo
    *******************************************************************/
    private void undoReturn(Action action) {
        table.add((DVD)action.getAction());
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

    public static void main(String[] args) {
        new RentalStoreGUI();
    }

}

class Action {
    private ActionType type;
    private Object action;
    public Action() {
        type = null;
        action = null;
    }
    public Action(ActionType type) {
        this.type = type;
        this.action = null;
    }
    public Action(ActionType type, Object action) {
        this.type = type;
        this.action = action;
    }
    public void setType(ActionType type) {
        this.type = type;
    }
    public void setAction(Object action) {
        this.action = action;
    }
    public ActionType getType() {
        return type;
    }
    public Object getAction() {
        return action;
    }
}

enum ActionType {
    RENT, RETURN
}
