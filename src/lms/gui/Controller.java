package lms.gui;

import lms.exceptions.BadStateException;
import lms.logistics.Path;
import lms.logistics.Transport;
import lms.grid.GameGrid;
import lms.grid.GridComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for the GUI.
 * <p>
 * Used to control the solution.View based on user input.
 * @version  1.2
 * @provided
 */
public class Controller {

    /**
     * View for the canvas application.
     */
    private final ViewModel viewModel;

    /**
     * Create a new Controller for the given view, adding ActionListener to
     * the view.
     *
     * @param viewModel The view to be managed by this controller.
     */
    public Controller(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * JavaDoc not provided since this is a given piece of code
     */
    public void run() {
        Timer timer = new Timer(1000, e -> {
            if (viewModel.isPaused()) {
                return;
            }

            GameGrid map = viewModel.getMap();
            List<Path> paths = new ArrayList<>();
            Map<Transport, Path> pathLog = new HashMap<>();

            for (GridComponent component : map.getGrid().values()) {

                if (!(component instanceof Transport transport)) {
                    continue;
                }
                if (pathLog.containsKey(transport)) {
                    continue;
                }

                List<Path> search = new ArrayList<>();
                List<Path> x = List.of(transport.getPath(), transport.getPath());
                search.add(x.get(0));

                for (Path path : search) {
                    if (!path.tail().equals(path)) {
                        continue;
                    }
                    Transport t = path.getNode();
                    while (true) {
                        pathLog.put(t, path);
                        if (path.getPrevious() == null) {
                            break;
                        }
                        Transport previousNode = path.getPrevious().getNode();
                        List<Path> x1 = List.of(
                                new Path(previousNode.getPath()),
                                new Path(previousNode.getPath())
                        );

                        t = previousNode;
                        x = x1;
                       // x.get(0).next(x.get(1).next()) ;//= path;

                        x.get(0).setNext(path);
                        x.get(1).setNext(path);


                        path = x.get(0);
                    }
                    paths.add(path.tail());
                }
            }

            System.out.println(paths.size());
            for (Path p : paths) {
                System.out.println(p);
                try {
                    p.applyAll(Transport::tick);
                } catch (BadStateException bse) {
                    System.err.println("Tick could not be processed:" + bse);
                }
            }
            viewModel.getCanvas().repaint();
        });
        timer.setInitialDelay(0);
        timer.setRepeats(true);
        timer.start();
    }

}
