package lms.logistics;

import lms.grid.GridComponent;
import lms.utility.Tickable;

/**
 * An abstract class that maintains an Item type and its Path (node,
 * next and previous), with the abstract method getEncoding()
 * its id (int) and provides an implementation of tick() from the Tickable
 * object and MapComponent (which is a functional interface with method
 * getEncoding().
 * @see Item
 * @see Path
 * @version 1.2
 *
 * @provided
 */
public abstract class Transport implements Tickable, GridComponent {

    /**
     * stores the name of an Item that is either produced, receiver
     * ot transported
     */
    private Item inventory;

    /**
     * maintains the node and its previous and next node connection
     */
    private final Path path;

    /**
     * Specific integer ID for the Transport node
     */
    private final int id;

    /**
     * Constructor providing unique ID
     * Provides details for each node and its connected nodes
     *
     * @param id is the name for the Transport object
     * @requires id is unique
     * @ensures GUI will work correctly
     */
    public Transport(int id) {
        this.id = id;
        this.inventory = null;
        this.path = new Path(this); // Where it all happens
    }

    /**
     * accessor method to get the instance id
     * @return id int of the object
     */
    public int getId() {
        return id;
    }

    /**
     * returns the previous path node (input)
     * @return Path previous value node
     */
    public Path getInput() {
        return path.getPrevious();
    }

    /**
     /**
     * returns the previous path node (output)
     * @return Path next value node
     *
     */
    public Path getOutput() {
        return path.getNext();
    }

    /**
     * sets the path input
     * @param input Path
     */
    public void setInput(Path input) {
        path.setPrevious(input);
    }

    /**
     * set the value of the next node connected to the current instance
     * @param output Path of the next node (if it exists)
     */
    public void setOutput(Path output) {
        path.setNext(output);
    }

    /**
     * get the name of the Item
     * @return Item, containing the String name of the object
     */
    public Item getInventory() {
        return inventory;
    }

    /**
     * set the name of the node
     * @param inventory Item, the containing the name of the node
     */
    public void setInventory(Item inventory) {
        this.inventory = inventory;
    }

    /**
     * get the Path object, containing node, next and previous pathways
     * @return Path
     */
    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.format("<%s-%d>", getClass().getSimpleName(), id);
    }

    /**
     * Propagate through the path and move inventory along where there are empty inventory spaces.
     */
    @Override
    public void tick() {
        if (inventory != null) {     /* if is NOT empty */
            Path path = getPath();   /* get the next item in the path */
            if (path.getNext() == null) { /* if there's no item then stop */
                return;
            }
            Transport nextNode = path.getNext().getNode(); /* if there's a next item and */

            if (nextNode.inventory == null) {         /* its inventory is empty */
                nextNode.inventory = inventory;       /* transfer inventory to the next inventory */
                inventory = null;                     /* and remove inventory from self */
            }
        }
    }
}
