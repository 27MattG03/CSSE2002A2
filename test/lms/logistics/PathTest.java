package lms.logistics;

import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;

import static org.junit.Assert.*;

public class PathTest {
    private Path head = new Path(new Producer(1,new Item("HEAD")));
    private Path next1 = new Path(new Belt(2), head, null);
    private Path next2 = new Path(new Belt (3), next1, null);
    private Path tail = new Path(new Receiver(4, new Item("TAIL")),next2, null);


    private void initFullPath (){
        this.head.setNext(next1);
        this.next1.setNext(next2);
        this.next2.setNext(tail);
    }
    @org.junit.Test
    public void head() {
        initFullPath();
        assertEquals(this.next1.head(), this.head);
        assertEquals(this.next2.head(),this.head);
        assertEquals(this.head.head(),this.head);
        assertEquals(this.tail.head(),this.head);
    }

    @org.junit.Test
    public void tail() {
        initFullPath();
        assertEquals(this.next1.tail(), this.tail);
        assertEquals(this.next2.tail(),this.tail);
        assertEquals(this.head.tail(),this.tail);
        assertEquals(this.tail.tail(),this.tail);
    }

    @org.junit.Test
    public void getPrevious() {
        Path testPath = new Path (new Belt(42),next1, null);
        assertEquals(testPath.getPrevious(), next1);
    }

    @org.junit.Test
    public void getNext() {
        Path testPath = new Path (new Belt(42),null, next1);
        assertEquals(testPath.getNext(), next1);
    }

    @org.junit.Test
    public void setPrevious() {
        Path testPath = new Path (new Belt(42), next2, next1);
        Path testPath2 = new Path (testPath);
        Path testPath3 = new Path(new Belt(43));
        testPath.setPrevious(tail);
        testPath2.setPrevious(head);
        testPath3.setPrevious(testPath);
        assertEquals(testPath.getPrevious(), tail);
        assertEquals(testPath2.getPrevious(),head);
        assertEquals(testPath3.getPrevious(),testPath);
    }

    @org.junit.Test
    public void setNext() {
        Path testPath = new Path (new Belt(42), next2, next1);
        Path testPath2 = new Path (testPath);
        Path testPath3 = new Path(new Belt(43));
        testPath.setNext(tail);
        testPath2.setNext(head);
        testPath3.setNext(testPath);
        assertEquals(testPath.getNext(), tail);
        assertEquals(testPath2.getNext(),head);
        assertEquals(testPath3.getNext(),testPath);
    }

    @org.junit.Test
    public void getNode() {
        Belt testBelt = new Belt(42);
        Path testPath = new Path (testBelt, next2, next1);
        Path testPath2 = new Path (testPath);
        Path testPath3 = new Path(testBelt);
        assertEquals(testPath.getNode(), testBelt);
        assertEquals(testPath2.getNode(), testBelt);
        assertEquals(testPath3.getNode(), testBelt);

    }

    @org.junit.Test
    public void testToString() {

        initFullPath();
        String expected = "START -> <Producer-1> -> <Belt-2> -> <Belt-3> -> <Receiver-4> -> END";
        assertEquals(expected, this.next1.toString());
        assertEquals(expected, this.next2.toString());
        assertEquals(expected, this.tail.toString());
        assertEquals(expected, this.head.toString());

    }

    @org.junit.Test
    public void testEquals() {
        Belt testBelt1 = new Belt(42);
        Belt testBelt2 = new Belt(43);
        Path testPath1 = new Path(testBelt1,next1,next2);
        Path testPath2 = new Path(testBelt1,head, tail);
        Path testPath3 = new Path(testBelt2,next1,next2);

        assertEquals(testPath1,testPath2);
        assertNotEquals(testPath1,testPath3);
        assertNotEquals(testPath1, testBelt1);



    }
}