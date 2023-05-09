package lms.gui;

import lms.exceptions.FileFormatException;
import lms.io.GameLoader;
import lms.grid.GameGrid;

import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main application class which starts the application and sets the root frame.
 *
 * Provides an example class for demonstrating how to use the@overview tag in JavaDoc.
 *
 * overview /Users/uqtchri6/IdeaProjects/2023s1_assignment2/overview.html
 *
 * @provided
 */
public class MainApplication {
    /**
     * maintains the screen Width
     */
    private final int SCREEN_WIDTH;

    /**
     * maintains the screen height
     */
    private final int SCREEN_HEIGHT;

    /**
     * Instantiates the view and controller for this application.
     *
     * @param title  String setting the title for the panel
     * @param width  int setting the width for the panel
     * @param height int setting the height for the panel
     * @param save   String setting the save file to load
     */
    public MainApplication(String title, int width, int height, String save)
            throws FileFormatException {
        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;

        /* Defines the gameGrid */
        GameGrid gameGrid;

        /*
         * Try and load the save file into gameGrid object
         */
        try {

            /* Loads and initialises the gameGrid object from save file data */
            gameGrid = GameLoader.load(new FileReader(save));
        } catch (IOException e) {
            throw new FileFormatException(e);
        }

        /*
         * Created the main window and adds close operation
         */
        JFrame mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * Created the view and controller for this application
         */
        ViewModel viewModel = new ViewModel(mainFrame, gameGrid);
        final Controller controller = new Controller(viewModel); // Thread

        /* add frame prepared by the view to the mainFrame JFrame */
        mainFrame.add(viewModel.getPanel());

        /* set the mainFrame frame size */
        mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        /* make your design visible */
        mainFrame.setVisible(true);
        controller.run();
    }

    /**
     * Main method for the application.
     *
     * @param args String array of command line arguments
     */
    public static void main(String[] args) {

        /* This line can be used to short-cut args,
         * but you should actually use IntelliJ's
         * Debug Configurations to set the path as a command line argument. */
        //args = new String[]{"saves/grid1.txt"};

        if (args.length != 1) {
            System.err.println("Usage: save_file\n");
            System.err.println("You did not specify the names of the required save file"
                    + " from which to load.");
            System.err.println("To do this, you need to add the command line "
                    + "argument to your "
                    + "program in IntelliJ.");
            System.err.println("Go to \"Run > Edit Configurations\" \n"
                    + "(If there is no configuration, "
                    + "you will need to create a new Application configuration, "
                    + "setting MainApplication as the Main class.)\n"
                    + "Add the path to your file to the program arguments text box.\n");
            System.err.println("Example: saves/grid1.txt");
            System.exit(1);
        }
        try {
            new MainApplication("Logistics Puzzle", 800, 700, args[0]);
            // Width and height chosen with sufficient size to fit all example saves
        } catch (FileFormatException e) {
            System.err.println("File was incorrectly formatted");
            e.printStackTrace();
        }
    }

}
