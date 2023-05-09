package lms.logistics.belts;

import lms.logistics.Transport;

/**
 * A class representing a Belt object and implements the Transport class
 * overriding the implemented Functional Interface MapComponent's method getEncoding().
 * and Tickable#tick()
 * @provided
 */
public class Belt extends Transport {

    /**
     * Constructs a new Belt object with the given integer id value.
     *
     * @param id the integer value to be provided to the superclass constructor
     */
    public Belt(int id) {
        super(id);
    }


    /**
     * Overrides encoding method returns the character string representing a
     * belt.
     *
     * @return String containing the specified symbol depicted in a grid.
     */
    @Override
    public String getEncoding() {
        return "b";
    }
}
