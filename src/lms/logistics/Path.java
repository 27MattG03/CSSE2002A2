package lms.logistics;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;

/**
 * Maintains a doubly linked list to maintain the links for each node.
 * Has previous and next item.
 * The path can't have an empty node, as it will throw an illegal
 * argument exception.
 * @version 1.0
 * @ass2
 */
public class Path {
    /** The previous path in the path chain. */
    private Path previous;
    /** The next path in the path chain. */
    private Path next;
    /** The transport node of this path. */
    private Transport node;

    /**
     * Constructs a path from another path.
     * @param path The path to copy.
     * @throws IllegalArgumentException If the paths node is null.
     */
    public Path(Path path) throws IllegalArgumentException {
        this.next = path.getNext();
        this.previous = path.getPrevious();
        this.node = path.getNode();
    }

    /**
     * Constructs a path from a node sets previous and next path to null.
     * @param node The transport node to construct the path from.
     * @throws IllegalArgumentException If the node is null.
     */
    public Path(Transport node) throws IllegalArgumentException {
        if (node != null) {
            this.node = node;
            this.previous = null;
            this.next = null;

        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Constructs a path from a transport node, previous path and next path.
     * @param node The transport node of this path.
     * @param previous The previous path.
     * @param next The next path.
     */
    public Path(Transport node, Path previous, Path next) throws IllegalArgumentException {
        this.next = next;
        this.previous = previous;
        if (node != null) {
            this.node = node;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the first Path in the path chain.
     * @return The first Path in the path chain.
     */
    public Path head() {
        if (this.previous == null) {
            return this;
        } else {
            return this.previous.head();
        }
    }

    /**
     * Returns the last Path in the path chain.
     * @return The last Path in the path chain.
     */
    public Path tail() {
        if (this.next == null) {
            return this;
        } else {
            return this.next.tail();
        }
    }

    /**
     * Returns the previous Path in the path chain.
     * @return The previous Path in the path chain.
     */
    public Path getPrevious() {
        return this.previous;
    }

    /**
     * Returns the next Path in the path chain.
     * @return The next Path in the path chain.
     */
    public Path getNext() {
        return this.next;
    }

    /**
     * Sets the previous Path in the path chain.
     * @param path The previous Path in the path chain.
     */
    public void setPrevious(Path path) {
        this.previous = path;
    }

    /**
     * Sets the next Path in the path chain.
     * @param path The next Path in the path chain.
     */
    public void setNext(Path path) {
        this.next = path;
    }

    /**
     * Returns the transport node of this path.
     * @return The transport node of this path.
     */
    public Transport getNode() {
        return this.node;
    }

    /**
     * This method takes a Transport Consumer,
     * using the Consumer&lt;T&gt; functional interface from java.util.
     * It finds the tail of the path and calls
     * Consumer&lt;T&gt;'s accept() method with the tail node as an argument.
     * Then it traverses the Path until the head is reached,
     * calling accept() on all nodes.
     *
     * This is how we call the tick method for all the different transport items.
     *
     * @param consumer Consumer&lt;Transport&gt;
     * @see java.util.function.Consumer
     * @provided
     */
    public void applyAll(Consumer<Transport> consumer) {
        Path path = tail(); // IMPORTANT: go backwards to aid tick
        do {
            consumer.accept(path.node);
            path = path.previous;
        } while (path != null);
    }

    /**
     * Returns a String representation of the path chain.
     * Example: START -> A -> B -> C -> END
     * @return A String representation of the path chain.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("START -> ");
        // Go to head
        if (this.equals(head())) {
            Path current = this;
            out.append(head().getNode().toString());
            out.append(" -> ");
            while (!current.equals(tail())) {
                current = current.getNext();
                out.append(current.getNode().toString());
                out.append(" -> ");
            }
            out.append("END");

        } else {
            return head().toString();
        }
        return out.toString();

    }

    /**
     * Determines if the object is equal to this path.
     * Equal if they have the same node and the object is a Path.
     * @param obj The object to compare to.
     * @return True if the object is equal to this path.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Path && ((Path) obj).getNode() == getNode()) {
            return true;
        } else {
            return false;
        }
    }
}

