package lms.io;

import lms.exceptions.FileFormatException;
import lms.grid.Coordinate;
import lms.grid.GameGrid;
import lms.grid.GridComponent;
import lms.logistics.Item;
import lms.logistics.Transport;
import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * This class is responsible for loading a game from a specifically formatted fike.
 */

public class GameLoader extends Object {
    /**
     * This method loads a GameGrid object from a file.
     * @param reader the reader to read the file from.
     * @return the GameGrid object.
     * @throws IOException if there is an error reading the file.
     * @throws FileFormatException if the file is not formatted correctly.
     */
    public static GameGrid load(Reader reader)
            throws IOException,
            FileFormatException {
        BufferedReader buffReader = new BufferedReader(reader);
        int range = toInt(buffReader.readLine());
        GameGrid game = new GameGrid(range);
        processSeparator(buffReader);
        final int producers = toInt(buffReader.readLine());
        final int receivers = toInt(buffReader.readLine());
        processSeparator(buffReader);
        List<Item> producerKeys = new ArrayList<Item>();
        for (int i = 0; i < producers; i++) {
            String key = buffReader.readLine();
            producerKeys.add(new Item(key));
        }
        ListIterator<Item> producerIter = producerKeys.listIterator();
        processSeparator(buffReader);
        List<Item> receiverKeys = new ArrayList<Item>();
        for (int i = 0; i < receivers; i++) {
            String key = buffReader.readLine();
            receiverKeys.add(new Item(key));
        }
        ListIterator<Item> recieverIter = receiverKeys.listIterator();
        Coordinate origin = new Coordinate();
        for (int i = 0; i < range; i++) {
            origin = origin.getTopLeft();
        }
        Coordinate currentCoordinate = origin;
        processSeparator(buffReader);
        int count = 1;
        for (int i = 0; i < (range * 2) + 1; i++) {
            String line = buffReader.readLine();
            int rowCount = 0;
            char[] check = line.toCharArray();
            for (char c : check) {
                switch (c) {
                    case 'p':
                        game.setCoordinate(currentCoordinate,
                                new Producer(count, producerIter.next()));
                        currentCoordinate = currentCoordinate.getRight();
                        rowCount++;
                        count++;
                        break;
                    case 'w':
                        game.setCoordinate(currentCoordinate, () -> ("w"));
                        currentCoordinate = currentCoordinate.getRight();
                        rowCount++;
                        break;
                    case 'r':
                        game.setCoordinate(currentCoordinate,
                                new Receiver(count, recieverIter.next()));
                        currentCoordinate = currentCoordinate.getRight();
                        count++;
                        rowCount++;
                        break;
                    case 'o':
                        game.setCoordinate(currentCoordinate, () -> ("o"));
                        currentCoordinate = currentCoordinate.getRight();
                        rowCount++;
                        break;
                    case 'b':
                        game.setCoordinate(currentCoordinate, new Belt(count));
                        currentCoordinate = currentCoordinate.getRight();
                        rowCount++;
                        count++;
                        break;
                    case ' ', '\n':
                        break;
                    default:
                        throw new FileFormatException();
                }
            }
            if (i < range) {
                origin = origin.getBottomLeft();
                if (rowCount != range + i + 1) {
                    throw new FileFormatException();
                }
            } else {
                origin = origin.getBottomRight();
                if (rowCount != 3 * range + 1 - i) {
                    throw new FileFormatException();
                }
            }
            currentCoordinate = origin;
        }
        processSeparator(buffReader);
        return processPath(game, buffReader);
    }

    /**
     * This method converts a string to an integer.
     * @param s the string to be converted.
     * @return the integer.
     * @throws FileFormatException if the string cannot be converted.
     */
    private static int toInt(String s)
            throws FileFormatException {
        try {
            return Integer.parseInt(s);

        } catch (Exception e) {
            throw new FileFormatException();
        }
    }

    /**
     * This method gets an element from the grid by its id.
     * @param id the id of the element.
     * @param grid the GameGrid object.
     * @return the element.
     * @throws IllegalArgumentException if the id is not found.
     */
    private static Transport getGridById(int id, GameGrid grid) throws IllegalArgumentException {
        Iterator<GridComponent> iter = grid.getGrid().values().iterator();
        GridComponent component;
        while (iter.hasNext()) {
            component = iter.next();
            if (component instanceof Transport) {
                if (((Transport) component).getId() == id) {
                    return (Transport) component;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * This method processes the separator between sections in the file.
     * @param reader the reader to read the file from.
     * @throws FileFormatException if the separator is not correct.
     */
    private static void processSeparator(BufferedReader reader) throws FileFormatException {
        String line;
        String sep;
        try {
            line = reader.readLine();
            sep = line.substring(0, 5);
        } catch (Exception e) {
            throw new FileFormatException();
        }
        if (!(sep.equals("_____"))) {
            throw new FileFormatException();
        }
    }

    /**
     * This method processes the path section of the file.
     * @param game the GameGrid object.
     * @param buffReader the reader to read the file from.
     * @return the completed GameGrid object.
     * @throws FileFormatException if the path is not formatted correctly.
     * @throws IOException if there is an error reading the file.
     */
    private static GameGrid processPath(GameGrid game, BufferedReader buffReader)
            throws FileFormatException, IOException {
        String buffCheck;
        while ((buffCheck = buffReader.readLine()) != null) {
            StringBuilder idBuild = new StringBuilder();
            StringBuilder idNextBuild = new StringBuilder();
            StringBuilder idPreviousBuild = new StringBuilder();
            char[] pathDetailsBuff = buffCheck.toCharArray();
            List<Character> pathDetails = new ArrayList<Character>();
            for (char c : pathDetailsBuff) {
                pathDetails.add(c);
            }
            ListIterator<Character> pathDetailsIterator = pathDetails.listIterator();
            boolean preDash = true;
            boolean preComma = true;
            while (pathDetailsIterator.hasNext()) {
                Character check = pathDetailsIterator.next();
                if (Character.isDigit(check)) {
                    if (preDash && preComma) {
                        idBuild.append(check);
                    } else if (preComma && !preDash) {
                        idPreviousBuild.append(check);
                    } else if (!preComma && !preDash) {
                        if (!check.equals('\n')) {
                            idNextBuild.append(check);
                        }
                    } else {
                        throw new FileFormatException();
                    }

                } else if (!Character.isDigit(check) && check.equals('-') && preDash) {
                    preDash = false;

                } else if (!Character.isDigit(check) && check.equals(',') && preComma) {
                    preComma = false;

                } else {
                    throw new FileFormatException();
                }
            }
            if (preDash == true) {
                throw new FileFormatException();
            }
            int id = Integer.valueOf(idBuild.toString());
            int idPrevious;
            int idNext;

            if (idNextBuild.isEmpty()) {
                idNext = 0;
            } else {
                idNext = Integer.valueOf(idNextBuild.toString());
            }
            if (idPreviousBuild.isEmpty()) {
                idPrevious = 0;
            } else {
                idPrevious = Integer.valueOf(idPreviousBuild.toString());
            }
            if (id == idPrevious || idNext == id || idNext == idPrevious) {
                throw new FileFormatException();
            }
            Transport component;
            Transport componentPrevious;
            Transport componentNext;
            try {
                component = getGridById(id, game);
                if (idPrevious != 0) {
                    componentPrevious = getGridById(idPrevious, game);
                } else {
                    componentPrevious = null;
                }
                if (idNext != 0) {
                    componentNext = getGridById(idNext, game);
                } else {
                    componentNext = null;
                }
            } catch (Exception e) {
                throw new FileFormatException();
            }

            if (component instanceof Producer && componentPrevious !=  null) {
                component.getPath().setNext(componentPrevious.getPath());
                componentPrevious.getPath().setPrevious(component.getPath());

            } else if (component instanceof Receiver && componentPrevious != null) {
                component.getPath().setPrevious(componentPrevious.getPath());
                componentPrevious.getPath().setNext(component.getPath());

            } else if (component instanceof Belt) {
                if (componentPrevious != null) {
                    component.getPath().setPrevious(componentPrevious.getPath());
                    componentPrevious.getPath().setNext(component.getPath());
                }
                if (componentNext != null) {
                    component.getPath().setNext(componentNext.getPath());
                    componentNext.getPath().setPrevious(component.getPath());
                }
            } else {
                throw new FileFormatException();
            }
        }
        return game;
    }
}
