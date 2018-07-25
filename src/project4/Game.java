package project4;

import java.util.GregorianCalendar;

public class Game extends DVD {

    /** Type of system the game is for **/
    // Xbox360, XBox1, PS4, WiiU, NintendoSwitch
    private PlayerType player;

    public Game() {
        super();
        this.player = null;
    }

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

    public PlayerType getPlayer() {
        return this.player;
    }

    public void setPlayerType(PlayerType player) {
        // User input is parsed and checked in GUI
        this.player = player;
    }

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
