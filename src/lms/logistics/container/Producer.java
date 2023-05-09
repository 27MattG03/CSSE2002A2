package lms.logistics.container;

import lms.exceptions.UnsupportedActionException;
import lms.logistics.Item;
import lms.logistics.Path;

/**
 * The Producer class represents a node in the fictional factory/machine
 * that is responsible for producing new items.
 * This class inherits the Tickable interface and ensures that on each second tick,
 * a new item is created.
 * <p>
 * The Producer class is designed to work in conjunction with the Receiver node in
 * the factory/machine to achieve the desired production outcome.
 * <p>
 * It can be instantiated by providing the ID and Item (key) parameters, to provide
 * parameters to its inherited super class (Container).
 * <p>
 * Example usage:
 * <p>
 * Producer producer = new Producer(id, key);
 * <p>
 * Note that the setInput and setInventory methods simply ensure that inherited
 * methods of the same name produce an error if called from the context of the
 * Producer.
 * <p>
 *
 * @author CSSE2002 Team
 * @version 1.0
 *
 * @provided
 */
public class Producer extends Container {

    private int counter;

    public Producer(int id, Item key) {
        super(id, key);
        counter = 0;
    }

    @Override
    public String getEncoding() {
        return "p";
    }

    @Override
    public void setInventory(Item inventory) {
        throw new UnsupportedActionException();
    }

    @Override
    public void setInput(Path input) {
        throw new UnsupportedActionException();
    }

    @Override
    public void tick() {
        if (++counter % 2 == 1) {
            super.tick();
            super.setInventory(getKey());
        }
    }
}
