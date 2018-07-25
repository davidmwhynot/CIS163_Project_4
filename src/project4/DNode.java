package project4;

/***********************************************************************
DoubleLinkedList Node object. Stores a reference to a DVD and
reference to next and previous nodes.

@author Steve Lucas
@version 7/18/2018
***********************************************************************/

import java.io.Serializable;

public class DNode implements Serializable {
    /**
    * Reference to a DVD object.
    */
    DVD dvd;

    /**
    * Reference to the next node in the list
    */
    DNode next;

    /**
    * Reference to the previous node in the list.
    */
    DNode prev;


    /*******************************************************************
    * Default constructor. Creates a node with all field variables
    * set to null.
    *******************************************************************/
    public DNode() {
        // default constructor
        this.dvd = null;
        this.next = null;
        this.prev = null;
    }

    /*******************************************************************
    * Constructor accepting a DVD object as the only parameter. Sets
    * the node's dvd variable to given DVD, other variables set to null
    * @param DVD - Reference to a DVD object this node will represent
    *******************************************************************/
    public DNode(DVD dvd) {
        // constructor with no links set
        this.dvd = dvd;
        this.next = null;
        this.prev = null;
    }

    /*******************************************************************
    * Constructor accepting an input for each instance variable and
    * setting them to respective input.
    * @param DVD - Reference to a DVD object this node will reperesent
    * @param DNode next - Reference to the next node in the list
    * @param DNode prev - Reference to the previous node in the list
    *******************************************************************/
    public DNode(DVD dvd, DNode next, DNode prev) {
        // constructor with all fields set
        this.dvd = dvd;
        this.next = next;
        this.prev = prev;
    }

    /*******************************************************************
    * Sets this.next to given DNode reference. Next node in the list.
    * @param DNode nextNode - The node this.next will point to.
    *******************************************************************/
    public void setNext(DNode nextNode) {
        this.next = nextNode;
    }

    /*******************************************************************
    * Sets this.prev to given DNode reference.
    * @param DNode prevNode - Previous node in the list.
    *******************************************************************/
    public void setPrev(DNode prevNode) {
        this.prev = prevNode;
    }

    /*******************************************************************
    * Returns this nodes reference to the next node in the list.
    * @return Dnode next - returns value of this.next
    *******************************************************************/
    public DNode getNext() {
        return this.next;
    }

    /*******************************************************************
    * Returns this nodes reference to the previous node in the list.
    * @return Dnode prev - returns value of this.prev
    *******************************************************************/
    public DNode getPrev() {
        return this.prev;
    }

    /*******************************************************************
    * Returns data from this node.
    * @return DVD - data for this node
    *******************************************************************/
    public DVD getDVD() {
        return this.dvd;
    }

    /*******************************************************************
    * Sets the value of which DVD object this node will reference
    * @param DVD - DVD object that this node will reference
    * @return boolean - true if DVD set properly, false if failed
    *******************************************************************/
    public boolean setDVD(DVD dvd) {
        if (dvd != null) {
            this.dvd = dvd;
            return true;
        } else {
            return false;
        }
    }

}
