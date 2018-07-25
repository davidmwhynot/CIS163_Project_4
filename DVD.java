package project4;

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

    public DVD() {
        this.bought = null;
        this.dueBack = new GregorianCalendar();
        this.title = null;
        this.nameOfRenter = null;
    }

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

    public GregorianCalendar getBought() {
        return bought;
    }

    public GregorianCalendar getDueBack() {
        return dueBack;
    }

    public String getTitle() {
        return title;
    }

    public String getNameOfRenter() {
        return nameOfRenter;
    }

    public void setBought(GregorianCalendar bought) {
        this.bought = bought;
    }

    public void setDueBack(GregorianCalendar dueBack) {
        this.dueBack = dueBack;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNameOfRenter(String nameOfRenter) {
        this.nameOfRenter = nameOfRenter;
    }

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
