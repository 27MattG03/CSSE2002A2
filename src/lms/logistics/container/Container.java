package lms.logistics.container;

import lms.logistics.Item;
import lms.logistics.Transport;

/**
 * Container extends Transport and passes tick and getEncoding methods up the chain.
 * provides a getKey() method providing access to the containers inventory.
 *
 * @provided
 */
public abstract class Container extends Transport {

    /**
     * The key used for accessing this container's inventory.
     */
    private final Item key;

    /**
     * Constructs a new Container object with the specified ID and key.
     *
     * @param id  the ID of the container
     * @param key the key to use for accessing the container's inventory
     * @throws IllegalArgumentException if the key is null
     */
    public Container(int id, Item key) throws IllegalArgumentException {
        super(id);
        if (key == null) {
            throw new IllegalArgumentException("Bad key");
        }
        super.setInventory(key);
        this.key = key;
    }

    /**
     * Manages the key used for accessing this container's inventory.
     * @return the key used for accessing this container's inventory
     */
    public Item getKey() {
        return key;
    }


}
