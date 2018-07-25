package project4;

/***********************************************************************
Game extends DVD and is a representation of a game that is currently
rented out.

@author David Whynot
@version 7/25/2018
***********************************************************************/

import java.util.GregorianCalendar;

public class Game extends DVD {

    /** Type of system the game is for **/
    // Xbox360, XBox1, PS4, WiiU, NintendoSwitch
    private PlayerType player;

    /*******************************************************************
    Default constructor. Calls the default superclass constructor and
    sets field variables to null.
    *******************************************************************/
    public Game() {
        super();
        this.player = null;
    }

    /*******************************************************************
    Creates a Game rental with the given input values.
    @param bought the date the game rental was purchased
    @param dueBack the date the game rental is due back
    @param title the name of the game that was rented
    @param name the name of the individual who rented the game
    @param system the type of console the game is for
    *******************************************************************/
    public Game(
    GregorianCalendar bought,
    GregorianCalendar dueBack,
    String title,
    String name,
    PlayerType system
    ) {
        super(bought, dueBack, title, name);
        this.player = system;
    }

    /*******************************************************************
    Gets the console the game is for
    @return the console the game is for
    *******************************************************************/
    public PlayerType getPlayer() {
        return this.player;
    }

    /*******************************************************************
    Sets the console the game is for
    @param player the console the game is for
    *******************************************************************/
    public void setPlayerType(PlayerType player) {
        // User input is parsed and checked in GUI
        this.player = player;
    }

    /*******************************************************************
    Computes and return the cost of the game rental if returned on the
    given date.
    @param date the date the rental is returned on
    @return the cost of the rental
    *******************************************************************/
    public double getCost(GregorianCalendar date) {
        // "date" is the date the game is being returned on
        // cost is 5.00 if on time, 15.00 if late
        if(this.dueBack.compareTo(date) < 0) { // dvd is late
            return 15.0;
        } else {
            return 5.0;
        }
    }

}
