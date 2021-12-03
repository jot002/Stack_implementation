import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class IntStackTest {

    @Test(expected = IllegalArgumentException .class)
    public void Constructor1() {
        IntStack testStack1 = new IntStack(3, .70, .20);
    }
    @Test(expected = IllegalArgumentException .class)
    public void Constructor11() {
        IntStack testStack1 = new IntStack(5, .2, .20);
    }
    @Test(expected = IllegalArgumentException .class)
    public void Constructor12() {
        IntStack testStack1 = new IntStack(5, .70, .90);
    }
    @Test(expected = IllegalArgumentException .class)
    public void Constructor2() {
        IntStack testStack = new IntStack(2, .70);
    }
    @Test(expected = IllegalArgumentException .class)
    public void Constructor21() {
        IntStack testStack = new IntStack(6, .33);
    }
    @Test(expected = IllegalArgumentException .class)
    public void Constructor3() {
        IntStack testStack = new IntStack(1);
    }

    @org.junit.Test
    public void isEmpty() {
        IntStack testStack1 = new IntStack(7, .70, .20);
        assertEquals(true, testStack1.isEmpty());
        IntStack testStack2 = new IntStack(12, .83);
        assertEquals(true, testStack2.isEmpty());
        IntStack testStack3 = new IntStack(8);
        assertEquals(true, testStack3.isEmpty());
        testStack3.push(12);
        assertEquals(false, testStack3.isEmpty());
    }

    @org.junit.Test
    public void clear() {
        IntStack testStack1 = new IntStack(7, .70, .20);
        testStack1.push(53);
        testStack1.clear();
        assertEquals(true, testStack1.isEmpty());
        IntStack testStack2 = new IntStack(12, .83);
        testStack2.clear();
        assertEquals(0, testStack2.size());
        IntStack testStack3 = new IntStack(8);
        testStack3.push(53);
        testStack3.push(44);
        assertEquals(44, testStack3.peek());
        assertEquals(2, testStack3.size());
        testStack3.clear();
        assertEquals(0, testStack3.size());
    }

    @org.junit.Test
    public void size() {
        IntStack testStack1 = new IntStack(7, .70, .20);
        assertEquals(0, testStack1.size());
        IntStack testStack2 = new IntStack(12, .83);
        assertEquals(0, testStack2.size());
        IntStack testStack3 = new IntStack(8);
        assertEquals(0, testStack3.size());
        testStack3.push(5);
        testStack3.push(67);
        assertEquals(2, testStack3.size());
    }

    @org.junit.Test
    public void capacity() {
        IntStack testStack1 = new IntStack(7, .70, .20);
        assertEquals(7, testStack1.capacity());
        IntStack testStack2 = new IntStack(305, .83);
        assertEquals(305, testStack2.capacity());
        IntStack testStack3 = new IntStack(9);
        assertEquals(9, testStack3.capacity());
        testStack3.push(12);
        assertEquals(9, testStack3.capacity());
        testStack3.clear();
        assertEquals(true, testStack3.isEmpty());
        assertEquals(9, testStack3.capacity());
    }

    @org.junit.Test
    public void peek() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.push(5);
        assertEquals(5, testStack1.peek());
        testStack1.push(7);
        assertEquals(7, testStack1.peek());
        testStack1.push(8);
        assertEquals(8, testStack1.peek());
        IntStack testStack2 = new IntStack(5, .70);
        testStack2.push(5);
        assertEquals(5, testStack2.peek());
        testStack2.push(7);
        assertEquals(7, testStack2.peek());
    }

    @Test(expected = EmptyStackException.class)
    public void TestPeek() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.peek();
    }

    @org.junit.Test
    public void push() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        assertEquals(3,testStack1.size());
        assertEquals(5,testStack1.capacity());
        testStack1.push(1);
        assertEquals(4,testStack1.size());
        assertEquals(5,testStack1.capacity());
        testStack1.push(1);
        assertEquals(10,testStack1.capacity());
        assertEquals(5,testStack1.size());
        testStack1.push(1);
        assertEquals(10,testStack1.capacity());
        assertEquals(6,testStack1.size());
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        assertEquals(20,testStack1.capacity());
        assertEquals(10,testStack1.size());
    }

    @org.junit.Test
    public void pop() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        testStack1.push(1);
        assertEquals(10,testStack1.capacity());
        testStack1.pop();
        testStack1.pop();
        assertEquals(10,testStack1.capacity());
        assertEquals(3,testStack1.size());
        testStack1.pop();
        assertEquals(2,testStack1.size());
        testStack1.pop();
        assertEquals(5,testStack1.capacity());
        assertEquals(1,testStack1.size());
    }
    @Test(expected = EmptyStackException.class)
    public void TestPop() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.pop();
    }

    @org.junit.Test
    public void multiPush() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        int[] tempArray = {1,2,3,4,5,6,7,8};
        testStack1.multiPush(tempArray);
        assertEquals(20,testStack1.capacity());
        assertEquals(8,testStack1.size());
        assertEquals(8, testStack1.peek());
        IntStack testStack2 = new IntStack(5);
        int[] tempArray2 = {1,2};
        testStack2.multiPush(tempArray2);
        assertEquals(5,testStack2.capacity());
        assertEquals(2,testStack2.size());
        assertEquals(2, testStack2.peek());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestMP1() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.multiPush(null);
    }

    @org.junit.Test
    public void multiPop() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        int[] tempArray = {1,2,3,4,5,6,7,8};
        testStack1.multiPush(tempArray);
        int[] results = {8,7,6,5};
        int[] test = testStack1.multiPop(4);
        assertEquals(4, testStack1.size());
        assertEquals(4, testStack1.peek());
        IntStack testStack2 = new IntStack(8,.70);
        int[] tempArray2 = {1,2,3,4};
        testStack2.multiPush(tempArray2);
        assertEquals(4, testStack2.size());
        assertEquals(4, testStack2.peek());
        testStack2.multiPop(3);
        assertEquals(1, testStack2.size());
        assertEquals(1, testStack2.peek());
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestMP() {
        IntStack testStack1 = new IntStack(5, .70, .20);
        testStack1.multiPop(-1);
    }
}