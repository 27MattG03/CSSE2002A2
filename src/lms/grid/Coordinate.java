package lms.grid;


import java.util.*;

/**
 * This is the Coordinate class, which is used to represent coordinates on a hexagonal grid.
 * (If you have read the GameLoader class,
 * this is not the same as the positional id which is generated when parsing nodes.)
 *
 * This class represents a hexagon in
 * <a href="https://www.redblobgames.com/grids/hexagons/#basics">"point-top orientation"</a>:
 * <pre>{@code
 * Top Left      / \  Right Top
 * Left          | |  Right
 * Bottom Left   \ /  Bottom Right
 * }</pre>
 * <p>
 * <a href="https://www.redblobgames.com/grids/hexagons/#coordinates">
 *     There are multiple ways to mathematically represent a hexagonal grid.</a>
 *
 * Because programming with classes allows you to hide abstractions from other classes
 * using private methods and variables,
 * the implementation approach "doesn't matter". </p>
 * <p>When loading a grid, you will elect a point as the origin,
 * and then use the methods provided to navigate around and generate new Coordinates.</p>
 *
 * @version 1.1
 * @provided
 */
public class Coordinate {

    /**
     * The orientation of the coordinate system
     */
    private final int cordQ;

    /**
     * The orientation of the coordinate system
     */
    private final int cordR;

    /**
     * The orientation of the coordinate system
     */
    private final int cordS;

    /**
     * A Map that tracks how to calculate each of the cardinal directions of the coordinate system.
     */
    private static final Map<Orientation, Coordinate> directions = Map.ofEntries(
            Map.entry(Orientation.TOP_LEFT, new Coordinate(0, -1, +1)),
            Map.entry(Orientation.TOP_RIGHT, new Coordinate(+1, -1, 0)),
            Map.entry(Orientation.RIGHT, new Coordinate(+1, 0, -1)),
            Map.entry(Orientation.BOTTOM_RIGHT, new Coordinate(0, +1, -1)),
            Map.entry(Orientation.BOTTOM_LEFT, new Coordinate(-1, +1, 0)),
            Map.entry(Orientation.LEFT, new Coordinate(-1, 0, +1))
    );

    /**
     * This should be the origin Coordinate,
     * A special coordinate that must exist in every game map.
     * Only Coordinates that have a direct connection (that is, not isolated)
     * will be drawn onto the game map.
     */
    public Coordinate() {
        this(0, 0);
    }

    /***
     * Creates a Coordinate with three components.
     * @param x first arg
     * @param y second arg
     * @param z third arg
     */
    public Coordinate(int x, int y, int z) {
        this.cordQ = x;
        this.cordR = y;
        this.cordS = z;
    }

    /***
     * Creates a Coordinate with two components, calcuating the third coordinate.
     * @param x first arg
     * @param y second arg, z is defined as -x - y
     */
    public Coordinate(int x, int y) {
        this(x, y, -x - y);
    }


    /**
     * Returns the hash code for this Coordinate object.
     *
     * @return the hash code for this Coordinate object.
     */
    public int hashCode() {
        return Objects.hash(cordQ, cordR, cordS);
    }

    /**
     * Compares Object, Class and coordinates for equality
     *
     * @param o The external object being compared
     * @return boolean of comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinate that = (Coordinate) o;
        return this.cordQ == that.cordQ && this.cordR == that.cordR && this.cordS == that.cordS;
    }

    /**
     * Returns the coordinate that is one step to the left of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the left of the current coordinate.
     */
    public Coordinate getLeft() {
        return add(directions.get(Orientation.LEFT));
    }

    /**
     * Returns the coordinate that is one step to the right of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the right of the current coordinate.
     */
    public Coordinate getRight() {
        return add(directions.get(Orientation.RIGHT));
    }

    /**
     * Returns the coordinate that is one step to the top-left of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the top-left of the current coordinate.
     */
    public Coordinate getTopLeft() {
        return add(directions.get(Orientation.TOP_LEFT));
    }

    /**
     * Returns the coordinate that is one step to the top-right of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the top-right of the current coordinate.
     */
    public Coordinate getTopRight() {
        return add(directions.get(Orientation.TOP_RIGHT));
    }

    /**
     * Returns the coordinate that is one step to the bottom-left of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the bottom-left of the current coordinate.
     */
    public Coordinate getBottomLeft() {
        return add(directions.get(Orientation.BOTTOM_LEFT));
    }

    /**
     * Returns the coordinate that is one step to the bottom-right of
     * the current coordinate in the given directions.
     *
     * @return The coordinate that is one step
     * to the bottom-right of the current coordinate.
     */
    public Coordinate getBottomRight() {
        return add(directions.get(Orientation.BOTTOM_RIGHT));
    }

    /**
     * Returns a new Coordinate object that is the result of
     * adding the given vector to this Coordinate object.
     *
     * @param vector the vector to add to this Coordinate object.
     * @return a new Coordinate object that is the result of
     * adding the given vector to this Coordinate object.
     */
    private Coordinate add(Coordinate vector) {
        return new Coordinate(cordQ + vector.cordQ, cordR + vector.cordR, cordS + vector.cordS);

    }

    /**
     * Returns a new Coordinate object that is the result of
     * subtracting the given vector from this Coordinate object.
     *
     * @param vector the vector to subtract from this Coordinate object.
     * @return a new Coordinate object that is the result of
     * subtracting the given vector from this Coordinate object.
     */
    private Coordinate subtract(Coordinate vector) {
        return new Coordinate(cordQ - vector.cordQ, cordR - vector.cordR, cordS - vector.cordS);
    }


    /**
     * Returns true if the given Coordinate object is a
     * neighbour of this Coordinate object, false otherwise.
     *
     * @param coordinate the Coordinate object to check for
     *                   neighbourliness.
     * @return true if the given Coordinate object is a
     * neighbour of this Coordinate object, false otherwise.
     */
    public boolean isNeighbour(Coordinate coordinate) {
        Coordinate vector = subtract(coordinate);
        return directions.containsValue(vector);
    }

    /**
     * Gets the direction required to travel to get from this coordinate to the reference coordinate
     *
     * @param coordinate reference to be examined
     * @return Orientation direction of travel
     * @requires isNeighbour(coordinate) == true || you can travel from this coordinate to the
     * other in a straight line
     * @ensures Orientation returns is correct as a direct relation
     */
    public Orientation getDirection(Coordinate coordinate) {

        /*
         * vector is the direction you would need to travel to get
         * from this coordinate to the next
         */
        Coordinate vector = normalise(coordinate.subtract(this));

        List<Orientation> ors = directions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(vector))
                .map(Map.Entry::getKey)
                .toList();
        if (ors.size() != 1) {
            throw new RuntimeException("Got incorrect directions from one coordinate");
        }
        return ors.get(0);
    }

    /**
     * Returns the normalised coordinate
     *
     * @param vector Coordinate (a) q r s
     * @return Coordinate normalised (for getDirection)
     */
    private Coordinate normalise(Coordinate vector) {
        return new Coordinate(
                vector.cordQ / Math.max(Math.abs(vector.cordQ), 1),
                vector.cordR / Math.max(Math.abs(vector.cordR), 1),
                vector.cordS / Math.max(Math.abs(vector.cordS), 1)
        );
    }




}