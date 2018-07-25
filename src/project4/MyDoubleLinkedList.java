package project4;

/***********************************************************************
* DoubleLinkedList class for RentalStore to save DVDs to a list.
* Uses DNode class as nodes.

@author Steve Lucas
@version 7/18/2018
***********************************************************************/

import java.io.Serializable;

public class MyDoubleLinkedList<T> implements Serializable {
    /**
    * Double linked list node at the top of the stack
    */
    private DNode top;

    /**
    * Double linked list node at the bottom of the stack
    */
    private DNode tail;

    /*******************************************************************
    *Default constructor, creates a list with a size of 0 and no nodes
    *Nodes are added to a list after it is constructed.
    ******************************************************************/
    public MyDoubleLinkedList() {
        // default constructor
        top = null;
        tail = null;
    }

    /******************************************************************
    * Get the size of the linked list (how many nodes are in it)
    * @return int - size of the linked list
    *****************************************************************/
    public int size() {
        int count = 0;
        DNode temp = top;
        while(temp != null) {
            temp = temp.getNext();
            ++count;
        }
        return count;
    }

    /******************************************************************
    * Removes all items of the list. DOES NOT THROW EXCEPTIONS OR
    * RETURN BOOLEAN.
    *****************************************************************/
    public void clear() {
        // remove all top of list
        this.setTop(null);
    }

    /******************************************************************
    * Adds a node to the end of the stack. If the stack is empty,
    * the push method is called to create a new node.
    * @param dvd - will be the value of the dvd field for new node
    * @throws NullPointerException if tail is not assigned and it
    * should be (if the list is not empty)
    *****************************************************************/
    public void add(DVD dvd) {
        if (this.getTail() == null) {
            //list is not empty, tail should have been assigned already
            if (this.size() > 0) {
                System.out.println("add method: "
                + "Tail assignment missing somewhere");
                throw new NullPointerException();
            }
            //list is empty
            else
                this.push(dvd);
        }
        //tail references a node, jump to that node and add after it
        else {
            // newNode.next -> null, newNode.prev -> current tail
            DNode newNode = new DNode(dvd, null, this.getTail());
            //current tail next -> newNode
            this.getTail().setNext(newNode);
            //newNode becomes tail
            this.setTail(newNode);
        }

    }
    /******************************************************************
    * Inserts a node at the top of the stack. If the stack is empty,
    * creates a new node and it becomes head and tail.
    * @param dvd - will become value of dvd field of new node
    *****************************************************************/
    public void push(DVD dvd) {
        // if DVD is null, throw exception
        if (dvd == null)
            throw new NullPointerException();
        // first node in the stack
        if (this.top == null) {
            DNode newNode = new DNode(dvd);
            this.setTop(newNode);
            this.setTail(newNode);
        }
        // Other nodes already exist, add to top
        else {
            // newNode.next -> current top, newNode.prev -> null
            DNode newNode = new DNode(dvd, this.getTop(), null);
            //sets currents top's previous -> newNode
            this.getTop().setPrev(newNode);
            this.setTop(newNode); // new node becomes top
        }
    }

    /******************************************************************
    * Removes node at given index from the stack.
    * @param index - index of the node to be removed.
    * @return Object - data from the removed node or an exception
    *****************************************************************/
    public Object remove(int index) {

        // index is out of range
        if (index < 0 || index >= this.size())
            return new IllegalArgumentException();

        // Instantiates a variable for a temp node at top (index 0)
        DNode temp = this.getTop();
        // iterate through the list to given index
        for (int a = 0; a <= index; a++) {
            if (temp == null) {
                // no node at given index. Return exception.
                return new NullPointerException();
            }
            DVD data = temp.getDVD();
            // reached given index and there is a node to remove
            if (a == index) {
                // prev.next -> temp.next
                if(temp.getPrev() != null) {
                    temp.getPrev().setNext(temp.getNext());
                    if (temp.getNext() != null) {
                        // temp.next references a node (not null/end of list)
                        temp.getNext().setPrev(temp.getPrev());
                        // next.prev -> temp.prev
                    } else {
                        // temp is tail, so prev becomes tail
                        this.setTail(temp.getPrev());
                    }
                } else {
                    if(temp.getNext() != null) {
                        top = temp.getNext();
                        top.setPrev(null);
                    } else {
                        top = null;
                        tail = null;
                    }
                }
                return data; //no exception to return
            } else {
                temp = temp.getNext();
            }
        }
        return null; // should always return before here.
    }


    /******************************************************************
    * Removes all nodes in the list by setting top to null.
    * Garbage collection then automatically deletes the list.
    * @param DNode top - top node of the list
    * @return boolean - true if 1 or more items removed, else false.
    *****************************************************************/
    public boolean removeAll(DNode top) {
        // if top is a null reference, return false.
        if (this.getTop() == null)
            return false;

        else { // Top of list set to null, deleting the list
            this.setTop(null);
            return true; // return true, at least 1 node deleted.
        }
    }

    /******************************************************************
    * Returns the node at the given index.
    * @param int index - index of the node to return
    * @return DVD - data from node at given index in the list
    * @throws IllegalArgumentException - index < 0 or index >= size
    *****************************************************************/
    public DVD get(int index) {
        // invalid input
        if (index < 0 || index >= this.size())
            throw new IllegalArgumentException();

        // valid input, iterate through list to index
        else {
            // index is closer to 0 than tail, start at 0.
            if (index <= this.size() - index) {
                // temp node that starts at top (index 0)
                DNode temp = this.getTop();

                // if index is closer to 0, start at index 0
                for(int a=0; a<=index; a++) {
                    if (a == index)
                        return temp.getDVD();

                    // if there is a null node before the end of the
                    // list (should never happen)
                    if (temp.getNext() == null)
                        throw new NullPointerException();

                    // iterate to the next node in the list
                    temp = temp.getNext();
                }
            } else {
                // if index is closer to this.size, start at the tail
                DNode temp = this.getTail(); // temp starts at tail
                for(int a = this.size() - 1; a >= 0; a--) {
                    if (a == index)
                        return temp.getDVD();

                    // if there is a null node before the end of the
                    // list (should never happen)
                    if (temp.getNext() == null)
                        throw new NullPointerException();

                    // iterate to the previous node in the list
                    temp = temp.getPrev();
                }
            }

            // error, the loop ended before index and no exceptions
            // were thrown. Shouldn't happen.
            return null;
        }
    }

    /******************************************************************
    *Returns index of the given node
    *@param DNode - The node you are trying to find
    *@return int index - Index of the node you are trying to find.
    *Returns -1 if the node could not be found.
    *****************************************************************/
    public int find(DNode node) {
        //temp node starting at top (index 0)
        DNode temp = this.getTop();
        int i=0; //counts iterations (current index)

        //loops until it reaches the end of the list
        while(temp != null) {
            if (temp == node)
            return i; //match found, return index of node
            temp = temp.getNext();
            i++;
        }
        return -1; // given node not found in list.
    }

    /******************************************************************
    *Returns the reference to the Top node of the stack.
    *@return DNode - Node at the top of the stack
    *****************************************************************/
    public DNode getTop() {
        return this.top;
    }

    /******************************************************************
    *Returns the reference to the Tail (last) node of the stack
    *@return DNode - Node at the end of the stack
    *****************************************************************/
    public DNode getTail() {
        return this.tail;
    }

    /******************************************************************
    *Sets the value for the lists' Top node reference
    *@param DNode NewTop - Node that will become the new top
    *****************************************************************/
    public void setTop(DNode newTop) {
        this.top = newTop;
    }

    /******************************************************************
    *Sets the value for the lists' Tail node reference
    *@param DNode NewTail - Node that will become the new tail
    *****************************************************************/
    public void setTail(DNode newTail) {
        this.tail = newTail;
    }

}
