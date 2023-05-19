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


public class GameLoader extends Object {

    public static GameGrid load(Reader reader)
            throws IOException,
            FileFormatException {
        BufferedReader buffReader = new BufferedReader(reader);


        int range = toInt(buffReader.readLine());

        GameGrid game = new GameGrid(range);
        buffReader.readLine();
        int producers = toInt(buffReader.readLine());
        int receivers = toInt(buffReader.readLine());
        buffReader.readLine();
        List<Item> producerKeys = new ArrayList<Item>();
        for(int i = 0; i < producers; i++){
            String key = buffReader.readLine();
            producerKeys.add(new Item(key));
        }
        ListIterator<Item> producerIter = producerKeys.listIterator();
       buffReader.readLine();
        List<Item> receiverKeys = new ArrayList<Item>();
        for(int i = 0; i < receivers; i++){
            String key = buffReader.readLine();
            receiverKeys.add(new Item(key));
        }
        ListIterator<Item> recieverIter = receiverKeys.listIterator();
        Coordinate origin = new Coordinate();
        for (int i = 0; i<range; i++) {
            origin = origin.getTopLeft();
        }
        Coordinate currentCoordinate = origin;
        buffReader.readLine();
        int count = 1;
        for(int i = 0;  i < (range*2)+1; i++) {
            String line = buffReader.readLine();

            char check[] = line.toCharArray();
            for (char c: check) {
                switch (c) {
                    case 'p':
                        game.setCoordinate(currentCoordinate, new Producer(count,producerIter.next()));
                        currentCoordinate = currentCoordinate.getRight();
                        count++;
                        break;
                    case 'w':
                        game.setCoordinate(currentCoordinate,  () -> ("w"));
                        currentCoordinate = currentCoordinate.getRight();
                        break;
                    case 'r':
                        game.setCoordinate(currentCoordinate, new Receiver(count,recieverIter.next()));
                        currentCoordinate = currentCoordinate.getRight();
                        count++;
                        break;
                    case 'o':
                        game.setCoordinate(currentCoordinate, () -> ("o"));
                        currentCoordinate = currentCoordinate.getRight();
                        break;
                    case 'b':
                        game.setCoordinate(currentCoordinate, new Belt(count));
                        currentCoordinate = currentCoordinate.getRight();
                        count++;
                        break;
                    case ' ':
                        break;

                }
            }
            if (i < range) {
                origin = origin.getBottomLeft();
            } else {
                origin = origin.getBottomRight();
            }
            currentCoordinate = origin;
        }
        buffReader.readLine();

        while(buffReader.ready()) {
            StringBuilder idBuild = new StringBuilder();
            StringBuilder idNextBuild = new StringBuilder();
            StringBuilder idPreviousBuild = new StringBuilder();
            char[] pathDetailsBuff = buffReader.readLine().toCharArray();
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
                idPrevious= Integer.valueOf(idPreviousBuild.toString());
            }
            if (id == idPrevious || idNext == id || idNext == idPrevious) {
                throw new FileFormatException();
            }
            Transport component;
            Transport componentPrevious;
            Transport componentNext;
            try {
                component = getGridByID(id, game);
                if (idPrevious != 0) {
                    componentPrevious = getGridByID(idPrevious, game);
                } else {
                    componentPrevious = null;
                }
                if (idNext != 0) {
                    componentNext = getGridByID(idNext, game);
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
    private static int toInt(String s)
            throws FileFormatException {
        try {
            return Integer.parseInt(s);

        } catch (Exception e) {
            throw new FileFormatException();
        }
    }
    private static Transport getGridByID  (int id, GameGrid grid) throws IllegalArgumentException {
        Iterator<GridComponent> iter = grid.getGrid().values().iterator();
        GridComponent component;
        while (iter.hasNext()) {
             component = iter.next();
             if(component instanceof Transport){
                 if(((Transport) component).getId() == id) {
                     return (Transport) component;
                 }
             }
        }
        throw new IllegalArgumentException();
    }







}
