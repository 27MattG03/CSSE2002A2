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
    private Path previous;
    private Path next;
    private Transport node;
    public Path(Path path) throws IllegalArgumentException {
        this.next = path.getNext();
        this.previous = path.getPrevious();
        this.node = path.getNode();
    }
    public Path(Transport node) throws IllegalArgumentException {
        if (node != null) {
            this.node = node;
            this.previous = null;
            this.next = null;

        } else {
            throw new IllegalArgumentException();
        }

    }
    public Path(Transport node, Path previous, Path next) {
        this.next = next;
        this.previous = previous;
        if (node != null) {
            this.node = node;
        } else {
            throw new IllegalArgumentException();
        }

    }
    public Path head() {
        if (this.previous == null) {
            return this;
        } else {
            return this.previous.head();
        }

    }
    public Path tail() {
        if (this.next == null) {
            return this;
        } else {
            return this.next.tail();
        }
    }
    public Path getPrevious() {
        return this.previous;
    }
    public Path getNext() {
        return this.next;
    }

    public void setPrevious(Path path){

        this.previous = path;
    }
    public void setNext(Path path) {
        this.next = path;
    }
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
    @Override
    public String toString () {
        StringBuilder out = new StringBuilder("START -> ");
        if (this.equals(head())) {
            Path current = this;
            out.append(head().getNode().toString());
            out.append(" -> ");

            while (!current.equals(tail())){
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

}

