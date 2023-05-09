package lms.gui;

import lms.grid.Coordinate;
import lms.grid.GameGrid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The View class for the Logistics Puzzle.
 * <p>
 * This class creates the GUI view and has methods which can update the view.
 *
 * @provided
 */
public class ViewModel {

    /**
     * isPaused is used to pause the game loop.
     */
    private boolean isPaused;

    /**
     * The map to draw
     */
    private final GameGrid map;

    /**
     * Root node of the scene graph, to which all the GUI elements are added.
     */
    private final JPanel rootPanel;

    /**
     * The canvas to draw on
     */
    private GraphicsCanvas canvas;

    /**
     * Get the map
     *
     * @return map to draw
     */
    public GameGrid getMap() {
        return map;
    }

    /**
     * Create the initial view for the GUI.
     * <p>
     * When a view is created, start building the initial scene graph,
     * by adding all the necessary components.
     * @param frame the frame to add the menu bar to and the root node to
     * @param map the map to draw
     */
    public ViewModel(JFrame frame, GameGrid map) {
        this.map = map;
        rootPanel = new JPanel();
        isPaused = false;
        addMenuBar(frame);
        addComponents();
    }

    /**
     * Get the main Panel of the GUI with any added components.
     * @return JPanel  - the main panel
     */
    public JPanel getPanel() {
        return rootPanel;
    }

    /**
     * Returns the canvas to interact with.
     * @return canvas to draw on
     */
    public GraphicsCanvas getCanvas() {
        return canvas;
    }

    /**
     * Get the pause status
     * @return paused state
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Add the menu bar to the JFrame
     * <p>
     */
    private void addMenuBar(JFrame frame) {

        /* Create an instance of a menuBar */
        final JMenuBar menu = new JMenuBar();

        /* Create a menu item for the menu bar */
        JMenu pausedMenu = new JMenu("Pause");

        /* Create a menu item for the menu bar */
        JMenuItem togglePause = new JMenuItem("Toggle pause", KeyEvent.VK_P);

        /* Add a shortcut to the menu item [P - key]*/
        togglePause.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.SHIFT_MASK));

        /* Add a listener to the menu item */
        togglePause.addActionListener(e -> {
            isPaused = !isPaused;
            System.out.println(isPaused);
        });

        /* Add the togglePause action to the menu bar */
        pausedMenu.add(togglePause);

        /* Add the pauseMenu item to the menuBar */
        menu.add(pausedMenu);

        /* Add the menu bar to the frame */
        frame.setJMenuBar(menu);
    }

    public static String COLOUR = "#336699"; //Chosen because the dev liked it.
    /**
     * Add all the GUI elements to the main layout.
     * <p>
     * This is where the scene graph is created.
     */
    private void addComponents() {
        JPanel centrePanel = new JPanel();

        /* Add padding, colour to the panel. */
        centrePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        centrePanel.setBackground(Color.WHITE);
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
        addComponents(centrePanel);



        rootPanel.add(centrePanel);
    }

    /**
     * Add all the GUI elements to the left container,
     * such as the canvas and the text fields
     *
     * @param box The container to which the elements are added.
     */
    private void addComponents(JPanel box) {

        /*
         * Add the canvas inside a JPanel, the Panel (canvasContainer) is used so
         * that a border can be added around the canvas and other items can be added later.
         */
        JPanel canvasContainer = new JPanel();
        GraphicsCanvas canvas = new GraphicsCanvas(600, 600);

        Coordinate origin = new Coordinate();
        if (map.getGrid().containsKey(origin)) {
            canvas.drawMap(map);
        } else {
            System.out.println("Coordinate map did not have an Origin Coordinate, nothing was "
                    + "drawn");
        }
        this.canvas = canvas;
        canvasContainer.add(canvas);
        canvasContainer.setBackground(Color.black);

        /* Add everything to the JPanel (which is passed as argument). */
        box.add(canvasContainer);
    }


}
