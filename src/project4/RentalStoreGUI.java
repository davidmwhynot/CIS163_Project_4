package project4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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

    /**
    * Holds the list engine
    */
    private RentalStore list;

    /**
    * Holds JListArea
    */
    private JList JListArea;

    /** Scroll pane */
    //private JScrollPane scrollList;

    public RentalStoreGUI() {

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

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
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
        exitItem.addActionListener(this);
        rentDVD.addActionListener(this);
        rentGame.addActionListener(this);
        returnItem.addActionListener(this);
        lateReport.addActionListener(this);

        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding list to the GUI1024
        list = new RentalStore();
        JListArea = new JList(list);
        add(JListArea);
        //  JListArea.setVisible(true);

        setVisible(true);
        setSize(500, 500);
        //  setSize(new Dimension (550,400));
        //  setMinimumSize(new Dimension(550,400));
        //  setMaximumSize(new Dimension(550,400));

    }

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
                    list.loadFromSerializable(filename);
                } else {
                    list.loadFromText(filename);
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
                    list.saveAsSerializable(filename);
                } else {
                    list.saveAsText(filename);
                }
            }
        }

        if (src == exitItem) {
            System.exit(0);
        }

        // Action menu options
        // XXX rent DVD XXX
        if (src == rentDVD) {
            DVD dvd = new DVD();
            RentDVDDialog dialog = new RentDVDDialog(this, dvd);
            if(dialog.closeOK()) {
                list.add(dvd);
            }
        }
        // XXX rent Game XXX
        if (src == rentGame) {
            Game game = new Game();
            RentGameDialog dialog = new RentGameDialog(this, game);
            if(dialog.closeOK()) {
                list.add(game);
            }
        }

        // XXX return XXX
        if (src == returnItem) {
            int index = JListArea.getSelectedIndex();
            if(index < 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "Please select a unit to return from the list."
                );
            } else {
                GregorianCalendar date = new GregorianCalendar();
                String inputDate = JOptionPane.showInputDialog(
                    "Enter return date (MM/DD/YYYY):"
                );
                SimpleDateFormat df = new SimpleDateFormat(
                    "MM/dd/yyyy"
                );
                try {
                    Date newDate = df.parse(inputDate);
                    date.setTime(newDate);

                    DVD unit = list.get(index);
                    JOptionPane.showMessageDialog(
                        null,
                        "Thanks " + unit.getNameOfRenter()
                        + " for returning " + unit.getTitle()
                        + ", you owe: " + unit.getCost(date)
                        + " dollars"
                    );
                    list.remove(index);
                }
                catch (ParseException pe) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Could not parse input date!"
                    );
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
                    list
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

    public static void main(String[] args) {
        new RentalStoreGUI();
    }

}
