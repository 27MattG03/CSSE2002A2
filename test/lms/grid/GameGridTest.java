package lms.grid;

import lms.logistics.Item;
import lms.logistics.container.Producer;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class GameGridTest {

    @Test
    public void getGrid() {
        GameGrid grid = new GameGrid(1);
        assertEquals(7,grid.getGrid().size());
        assert grid.getGrid() instanceof Map<Coordinate, GridComponent>;
        Map<Coordinate,GridComponent> copyTest1 = grid.getGrid();
        Map<Coordinate,GridComponent> copyTest2 = grid.getGrid();
        assertNotSame(copyTest1,copyTest2);


    }


    @Test
    public void getRange() {
        GameGrid gridSmallRange = new GameGrid(2);
        GameGrid gridLargeRange = new GameGrid(10);
        assertEquals(gridSmallRange.getRange(),2);
        assertEquals(gridLargeRange.getRange(),10);
    }

    @Test
    public void setCoordinate() {
        GameGrid grid = new GameGrid(3);
        Coordinate origin = new Coordinate();
        Coordinate test = origin.getRight().getBottomRight();
        Producer prod = new Producer(1, new Item("TEST"));
        grid.setCoordinate(test,prod);
        assertEquals(prod, grid.getGrid().get(test));

    }
}