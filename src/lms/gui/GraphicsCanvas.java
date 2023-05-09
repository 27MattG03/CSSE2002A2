package lms.gui;

import lms.logistics.Item;
import lms.logistics.Path;
import lms.logistics.Transport;
import lms.logistics.container.Receiver;
import lms.logistics.container.Producer;
import lms.grid.Coordinate;
import lms.grid.GameGrid;
import lms.grid.GridComponent;
import lms.grid.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * A JPanel that provides a graphics context for drawing the grid
 * This class is intended for use as a canvas for 2Dgraphics.
 *
 * <p>
 * Example output:
 * <br>
 * <img src="../resources/ExampleOutputGrid.png" width="300" height="300" alt="Screenshot of the method in action">
 *
 * @provided
 */
class GraphicsCanvas extends JPanel {

    /**
     * Stores the hexagons in a List of DrawnElements
     */
    private final List<DrawnElement> hexagons;

    /**
     * Stores the hover polygon
     */
    private Polygon hoverPolygon;


    /**
     * Hidden Class
     * @provided
     */
    private static class DrawnElement {

        /**
         * Stores the coordinate
         */
        private Coordinate coordinate;
        // [getCoordinate]

        /**
         * Stores the polygon
         */
        private Polygon polygon;
        // [getPolygon]

        /**
         * Stores the x coordinate
         */
        private int cordX;

        /**
         * Stores the y coordinate
         */
        private int cordY;

        /**
         * Stores the MapComponent
         */
        private GridComponent component;
        //  method [getComponent]

        public DrawnElement(Coordinate coordinate, Polygon polygon, int x, int y,
                            GridComponent component) {
            this.coordinate = coordinate;
            this.polygon = polygon;
            this.cordX = x;
            this.cordY = y;
            this.component = component;
        }
    }

    /**
     * Creates a new GraphicsCanvas with the specified preferred width and height.
     *
     * @param prefWidth  the preferred width of the canvas
     * @param prefHeight the preferred height of the canvas
     */
    public GraphicsCanvas(int prefWidth, int prefHeight) {
        super();
        this.setPreferredSize(new Dimension(prefWidth, prefHeight));
        hexagons = new ArrayList<>();
        hoverPolygon = null;
        addMouseMotionListener(new MouseAdapter() {

            /**
             * This is a method implementation for the mouseMoved method,
             * which is part of a MouseAdapter object being used in a
             * GraphicsCanvas class. The mouseMoved method is called when
             * the user moves the mouse within the canvas.
             * <p>
             * The purpose of this method is to determine whether the mouse
             * is currently hovering over any of the hexagons that have been
             * added to the GraphicsCanvas. Here's how it works:
             * <p>
             * hexagons.stream() - creates a stream of Hexagon objects that
             * have been added to the GraphicsCanvas.
             * .map(el -> el.polygon) - maps each Hexagon object to its corresponding
             * Polygon object. .filter(p -> p.contains(e.getPoint())) - filters the
             * stream to include only Polygon objects that contain the current mouse
             * point (e.getPoint()). .findFirst() returns an Optional containing the
             * first Polygon that was filtered, if one exists.
             * .ifPresentOrElse(p -> hoverPolygon = p, () -> hoverPolygon = null) -
             * sets the hoverPolygon variable to the first Polygon if it exists, or
             * null if it doesn't. The code is checking whether the mouse is currently
             * hovering over any of the hexagons on the canvas, and if so, it sets the
             * hoverPolygon variable to the corresponding Polygon. Finally, it calls
             * repaint() to update the canvas and display the hover state of the hexagon.
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                hexagons.stream()
                        .map(el -> el.polygon)
                        .filter(p -> p.contains(e.getPoint()))
                        .findFirst()
                        .ifPresentOrElse(p -> hoverPolygon = p, () -> hoverPolygon = null);
                repaint();
            }
        });
    }

    /**
     * Draws a game map by generating a hexagonal grid based on the MapComponents
     * of the provided GameGrid. The generated grid is centered on the JPanel that
     * this method is called from, unless its dimensions are (0,0), in which case
     * the grid is centered on the preferred size of the JPanel. The hexagonal
     * grid is generated using a slow algorithm that iterates over all the
     * MapComponents and their connections, drawing the hexagons on a canvas as it
     * goes.
     *
     * @param map the GameGrid to draw
     */
    public void drawMap(GameGrid map) {

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        if (x == 0 && y == 0) {
            Dimension dimensions = getPreferredSize();
            x = dimensions.width / 2;
            y = dimensions.height / 2;
        }

        Map<Coordinate, GridComponent> grid = map.getGrid();
        setLayout(null);

        List<Coordinate> tasklist = new ArrayList<>();

        Coordinate origin = new Coordinate();

        hexagons.add(new DrawnElement(origin, drawHexagon(x, y), x, y, grid.get(origin)));
        tasklist.add(origin);

        int size = getHexagonSize();
        int half = size / 2;
        int threeQuart = size * 3 / 4;


        // Incredibly slow algorithm
        // Improve if you want
        // Hard because generic Coordinate system and working off connections
        while (!tasklist.isEmpty()) {
            Coordinate target = tasklist.remove(0);
            grid.remove(target);
            // if item != null do something (drawing)

            DrawnElement element =
                    hexagons.stream()
                            .filter(e -> e.coordinate.equals(target))
                            .findFirst().orElseThrow(IllegalArgumentException::new);
            for (Coordinate c : grid.keySet()) {
                if (tasklist.contains(c)) {
                    continue;
                }
                if (target.isNeighbour(c)) {
                    Orientation direction = target.getDirection(c);
                    x = element.cordX;
                    y = element.cordY;
                    switch (direction) {
                        case TOP_LEFT -> {
                            x -= half;
                            y -= threeQuart;
                        }
                        case TOP_RIGHT -> {
                            x += half;
                            y -= threeQuart;
                        }
                        case RIGHT -> {
                            x += size;
                        }
                        case BOTTOM_RIGHT -> {
                            x += half;
                            y += threeQuart;
                        }
                        case BOTTOM_LEFT -> {
                            x -= half;
                            y += threeQuart;
                        }
                        case LEFT -> {
                            x -= size;
                        }
                    }
                    hexagons.add(new DrawnElement(c, drawHexagon(x, y), x, y,
                            grid.getOrDefault(c, () -> "ERROR")));
                    System.out.println("Drew " + c + " as " + grid.getOrDefault(c, () -> "ERROR")
                            .getEncoding() + " : from - " + target + " - as " + direction);
                    tasklist.add(c);
                }
            }
        }
    }


    /**
     * Draws a line between the specified coordinates and the middle of one of
     * the sides of the specified polygon, based on the specified orientation.
     * If the orientation is not one of the predefined values, the line is
     * drawn directly from the specified coordinates to the center of the polygon.
     *
     * @param graphics2D  the graphics context to use for drawing the line
     * @param polygon     the polygon to use for determining the middle point of
     *                    the line
     * @param x           the x-coordinate of the starting point of the line
     * @param y           the y-coordinate of the starting point of the line
     * @param orientation the orientation of the line relative to the polygon
     * @throws NullPointerException if the graphics2D or polygon parameter is null
     */
    private void drawLine(Graphics2D graphics2D, Polygon polygon, int x, int y,
                          Orientation orientation) {

        /* Determine the midpoint of the appropriate side of the polygon
           based on the specified orientation */
        int midX;
        int midY;
        switch (orientation) {
            case TOP_LEFT -> {
                midX = (polygon.xpoints[4] + polygon.xpoints[5]) / 2;
                midY = (polygon.ypoints[4] + polygon.ypoints[5]) / 2;
            }
            case TOP_RIGHT -> {
                midX = (polygon.xpoints[5] + polygon.xpoints[0]) / 2;
                midY = (polygon.ypoints[5] + polygon.ypoints[0]) / 2;
            }
            case RIGHT -> {
                midX = (polygon.xpoints[0] + polygon.xpoints[1]) / 2;
                midY = (polygon.ypoints[0] + polygon.ypoints[1]) / 2;
            }
            case BOTTOM_RIGHT -> {
                midX = (polygon.xpoints[1] + polygon.xpoints[2]) / 2;
                midY = (polygon.ypoints[1] + polygon.ypoints[2]) / 2;
            }
            case BOTTOM_LEFT -> {
                midX = (polygon.xpoints[2] + polygon.xpoints[3]) / 2;
                midY = (polygon.ypoints[2] + polygon.ypoints[3]) / 2;
            }
            case LEFT -> {
                midX = (polygon.xpoints[3] + polygon.xpoints[4]) / 2;
                midY = (polygon.ypoints[3] + polygon.ypoints[4]) / 2;
            }
            default -> {
                midX = x;
                midY = y;
            }
        }

        /* Draw the line between the specified starting point and the midpoint of the side */
        Stroke s = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawLine(x, y, midX, midY);
        graphics2D.setStroke(s);
    }

    /**
     * Returns the DrawnElement that contains a Transport component with the specified ID.
     *
     * @param id the ID of the Transport component to search for
     * @return the DrawnElement that contains the Transport component with the specified ID
     * @throws RuntimeException if no DrawnElement is found with the specified Transport ID
     */
    private DrawnElement getElementByTransportId(int id) {
        return hexagons.stream()
                .filter(el -> el.component instanceof Transport)
                .filter(el -> ((Transport) el.component).getId() == id)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Draws the element contents.
     * <p>
     *
     * @param element The element to draw.
     */
    public void drawShape(Graphics2D graphics2D, DrawnElement element) {
        GridComponent component = element.component;
        if (component instanceof Transport transport) {
            int x = element.cordX;
            int y = element.cordY;
            // Draw line
            Coordinate coordinate = element.coordinate;
            List<Path> nodes = new ArrayList<>();
            nodes.add(transport.getInput());
            nodes.add(transport.getOutput());

            for (Path node : nodes) {
                if (node == null) {
                    continue;
                }
                Transport item = node.getNode();
                Coordinate other = getElementByTransportId(item.getId()).coordinate;
                Orientation direction = coordinate.getDirection(other);
                drawLine(graphics2D, element.polygon, x, y, direction);
            }
            // Draw box
            if (component instanceof Receiver) {
                graphics2D.setPaint(Color.GREEN);
            } else if (component instanceof Producer) {
                graphics2D.setPaint(Color.MAGENTA);
            } else {
                graphics2D.setPaint(Color.BLACK);
            }
            graphics2D.fillRect(x - 5, y - 5, 11, 11);
            graphics2D.setPaint(Color.BLACK);
            // Draw item
            Item item = transport.getInventory();
            if (item != null) {
                graphics2D.setPaint(Color.RED);
                graphics2D.fillRect(x - 2, y - 2, 5, 5);
            }
        } else {
            Polygon hexagon = element.polygon;
            switch (component.getEncoding()) {
                case "w" -> {
                    graphics2D.setPaint(Color.BLACK);
                    graphics2D.fillPolygon(hexagon);
                }
                case "ERROR" -> {
                    graphics2D.setPaint(Color.RED);
                    graphics2D.fillPolygon(hexagon);
                }
                case "o" -> {
                }
                default -> {
                    System.out.println(component.getEncoding());
                    graphics2D.setPaint(Color.ORANGE);
                    graphics2D.fillPolygon(hexagon);
                }
            }
        }
    }

    /**
     * This is the main graphical canvas for drawing your objects, such as hexagons and such.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();

        for (DrawnElement shape : hexagons) {
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawPolygon(shape.polygon);
            drawShape(graphics2D, shape);

        }

        if (hoverPolygon != null) {
            graphics2D.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            graphics2D.fillPolygon(hoverPolygon);
        }

        graphics2D.dispose();
    }

    public int getHexagonSize() {
        return 60;
    }

    /**
     * Adds a Hexagon to draw.
     * / \
     * | |
     * \ /
     *
     * @param x The centre X coordinate of the Hexagon.
     * @param y The centre Y coordinate of the Hexagon.
     */
    private Polygon drawHexagon(int x, int y) {
        int size = getHexagonSize();
        int half = size / 2;
        int quart = size / 4;

        int[] pointsX = {x + half, x + half, x, x - half, x - half, x};
        int[] pointsY = {y - quart, y + quart, y + half, y + quart, y - quart, y - half};

        return new Polygon(pointsX, pointsY, pointsX.length);
    }
}