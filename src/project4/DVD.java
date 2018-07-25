package project4;

/***********************************************************************
DVD represents and stores the data for a currently rented out dvd.

@author David Whynot
@version 7/25/2018
***********************************************************************/

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DVD implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The date the DVD was rented **/
    protected GregorianCalendar bought;

    /** The date the DVD is due back **/
    protected GregorianCalendar dueBack;

    /** The name of the DVD **/
    protected String title;

    /** Name of the user that rented the DVD **/
    protected String nameOfRenter;
    /*******************************************************************
    Default constructor creates a DVD with null/default field values.
    *******************************************************************/
    public DVD() {
        this.bought = null;
        this.dueBack = new GregorianCalendar();
        this.title = null;
        this.nameOfRenter = null;
    }

    /*******************************************************************
    Constructor creates a dvd with the given date values, name, and
    title.
    @param bought the date the DVD rental was purchased
    @param dueBack the date the DVD rental is due back
    @param title the name of the DVD that was rented
    @param name the name of the individual who rented the DVD
    *******************************************************************/
    public DVD(
    GregorianCalendar bought,
    GregorianCalendar dueBack,
    String title,
    String name
    ) {
        this.bought = bought;
        this.dueBack = dueBack;
        this.title = title;
        this.nameOfRenter = name;
    }

    /*******************************************************************
    Gets the day the rental was purchased.
    @return the day the rental was purchased.
    *******************************************************************/
    public GregorianCalendar getBought() {
        return bought;
    }

    /*******************************************************************
    Gets the day the rental is due back
    @return the day the rental is due back
    *******************************************************************/
    public GregorianCalendar getDueBack() {
        return dueBack;
    }

    /*******************************************************************
    Gets the title of the DVD that was rented
    @return the title of the DVD that was rented
    *******************************************************************/
    public String getTitle() {
        return title;
    }

    /*******************************************************************
    Gets the name individual who rented the DVD
    @return the name of the renter
    *******************************************************************/
    public String getNameOfRenter() {
        return nameOfRenter;
    }

    /*******************************************************************
    Sets the date the rental was purchased
    @param bought the date the rental was purchased
    *******************************************************************/
    public void setBought(GregorianCalendar bought) {
        this.bought = bought;
    }

    /*******************************************************************
    Sets the date the rental is due back on
    @param dueBack the date the rental is due back on
    *******************************************************************/
    public void setDueBack(GregorianCalendar dueBack) {
        this.dueBack = dueBack;
    }

    /*******************************************************************
    Sets the title of the unit
    @param title the title of the unit
    *******************************************************************/
    public void setTitle(String title) {
        this.title = title;
    }

    /*******************************************************************
    Sets the name of the individual who rented the unit
    @param title the name of the individual who rented the unit
    *******************************************************************/
    public void setNameOfRenter(String nameOfRenter) {
        this.nameOfRenter = nameOfRenter;
    }


    /*******************************************************************
    Computes the cost of the rental if returned on the given date.
    @param date the date of the return of this item
    @return the fee for the rental
    *******************************************************************/
    public double getCost(GregorianCalendar date) {
        // "date" is the date the dvd is being returned on
        // cost is 1.20 if on time, 3.20 if late
        if(this.dueBack.compareTo(date) < 0) { // dvd is late
            return 3.20;
        } else {
            return 2.0;
        }
    }
}
