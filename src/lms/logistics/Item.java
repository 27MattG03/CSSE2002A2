package lms.logistics;

/**
 * An item that can be stored in a container.
 */
public class Item {
    private String name;

    /**
     * Constructs an Item with the specified name.
     * @param name The name of the item.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public Item(String name) throws IllegalArgumentException {
        if (name != null && name != "") {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Determines if this item is equal to another object.
     * @param o The object to compare to.
     * @return True if the object is an item, is not null and has the same hashcode as this item.
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Item && (o.hashCode() == this.hashCode())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines the hashcode of this item based on it's name.
     * @return The hashcode of this item.
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Returns a string representation of this item i.e. its name.
     * @return The name of this item.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

