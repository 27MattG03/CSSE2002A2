package lms.logistics.container;

import lms.exceptions.BadStateException;
import lms.exceptions.UnsupportedActionException;
import lms.logistics.Item;
import lms.logistics.Path;
import lms.logistics.Transport;

/**
 * The Receiver class represents a node in the fictional factory/machine
 * that is responsible for consuming or nullifying notes detected by its process.
 * It nullifies the state of the node and provides methods to interact with it.
 * This class inherits the Tickable interface and ensures that on each tick, the
 * node in focus is nullified.
 * <p>
 * The Receiver class is designed to work in conjunction with the Producer node in
 * the factory/machine to achieve the desired production outcome.
 * <p>
 * It can be instantiated by providing the ID and Item (key) parameters, to provide
 * parameters to its inherited super class (Container).
 * <p>
 * Example usage:
 * <p>
 * Receiver receiver = new Receiver(id, key);
 * <p>
 * Note that the setOutput and setInventory methods simply ensure that inherited
 * methods of the same name produce an error if called from the context of the
 * receiver.
 * <p>
 * The getInventory method simply returns null, as the Receiver does not
 * maintain an inventory.
 *
 * @author CSSE2002 Team
 * @version 1.1
 *
 * @provided
 */
public class Receiver extends Container {

    /**
     * The Receiver class represents a receiver object that receives items
     * in a given inventory. This class has a constructor that takes an
     * integer ID and an Item key.
     * <p>
     * The Receiver constructor creates a new Receiver object with the specified
     * ID and Item key. It calls the constructor of its superclass, which takes
     * an ID and key as parameters, to initialize the ID and key of the Receiver
     * object.
     * <p>
     * Example usage:
     * Receiver receiver = new Receiver(int, Item);
     *
     * @param id  the integer ID of the Receiver object
     * @param key the Item key of the Receiver object
     * @see Item
     * @see Container
     * @see Producer
     * @see Transport
     */
    public Receiver(int id, Item key) {
        super(id, key);
    }

    @Override
    public String getEncoding() {
        return "r";
    }

    /**
     * Rewrites the Transport implementation to throw
     * an unsupported exception if invoked
     *
     * @param item Item, that is dismissed as this method is not
     *             supported by the Receiver
     */
    @Override
    public void setInventory(Item item) {
        throw new UnsupportedActionException();
    }

    /**
     * Rewrites the Transport implementation to return a null if called
     *
     * @return Item of value null
     */
    @Override
    public Item getInventory() {
        return null; // always empty
    }

    /**
     * Rewrites the Transport implementation to throw
     * an unsupported exception if invoked
     *
     * @param output Path, that is dismissed as this method is not
     *               supported by the Receiver
     */
    @Override
    public void setOutput(Path output) {
        throw new UnsupportedActionException();
    }

    /**
     * Checks to ensure that the value at this iteration is not null and the key
     * does not equal the inventory of the superclass.
     *
     * throws BadStateException, or nullifies the inventory of the superclass if
     * no exception found
     */
    @Override
    public void tick() {
        Item inventory = super.getInventory();
        if (inventory != null && !getKey().equals(inventory)) {
            throw new BadStateException("Receiver inventory != key value");
        }
        super.setInventory(null);
    }

}
