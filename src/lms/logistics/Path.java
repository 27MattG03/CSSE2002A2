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
}
