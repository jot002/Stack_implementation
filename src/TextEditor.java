/*
    Name: Jonathan Tran
    PID:  A15967290
 */

/**
 * TextEditor is created to replicate a text editor. It contains a string
 * that is changed using delete, insert, and caseConvert. It will
 * change the string and update it while keeping a history log.
 * @author Jonathan Tran
 * @since  10/11/21
 */
public class TextEditor {

    /* instance variables */
    private String text;
    private IntStack undo;
    private StringStack deletedText;
    private IntStack redo;
    private StringStack insertedText;

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CAP = 100;
    private static final int DOUBLE = 2;

    /**
     * This constructor initializes a text editor
     * by initializing the text and the stacks you need to use.
     */
    public TextEditor() {
        this.text = "";
        this.undo = new IntStack(CAP);
        this.deletedText = new StringStack(CAP);
        this.redo = new IntStack(CAP);
        this.insertedText = new StringStack(CAP);
    }

    /**
     * This method returns the text
     * @return the string text
     */
    public String getText() {
        return this.text;
    }

    /**
     * This method returns the length of text
     * @return the length of the string
     */
    public int length() {
        return this.text.length();
    }

    /**
     * This method clears the stack and sets the capacity to the
     * original size
     * @param i an int the represents the start
     * @param j an int the represents the end
     * @exception IllegalArgumentException if i or j is out of bound,
     * or i is not smaller than j
     */
    public void caseConvert(int i, int j) {
        if (i >= j) {
            throw new IllegalArgumentException();
        }
        if (i < 0 || j > this.text.length()) {
            throw new IllegalArgumentException();
        }
        int constantI = i;
        int constantJ = j;
        StringBuilder newText = new StringBuilder(this.text);
        while (i < j) {
            // iterates through the string from
            // index i inclusive to j exclusive
            char letter = newText.charAt(i);
            // skip to next char is it is not a letter
            if (Character.isLetter(letter) == false) {
                i++;
            }
            // checks if it is lowercase
            else if (Character.isLowerCase(letter)){
                int index = LOWER.indexOf(letter);
                // switches it to upper case
                newText.setCharAt(i, UPPER.charAt(index));
                i++;
            }
            // checks if it is uppercase
            else {
                int index = UPPER.indexOf(letter);
                // switches it to lower case
                newText.setCharAt(i, LOWER.charAt(index));
                i++;
            }
        }
        this.text = newText.toString();
        this.redo.clear();
        // updates the undo stack with the steps
        int[] undoItems = new int[]{constantI, constantJ, 0};
        undo.multiPush(undoItems);
    }

    /**
     * This method inserts the input into the text
     * @param i an int the represents the start
     * @param input a string that is added into the text
     * @exception NullPointerException if input is null
     * @exception IllegalArgumentException if i is out of bounds
     */
    public void insert(int i, String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        if (i < 0 || i > this.text.length()) {
            throw new IllegalArgumentException();
        }
        // if i is anywhere in the string
        if (i > 0) {
            // get the substrings
            String beginning = this.text.substring(0,i);
            String end = this.text.substring(i);
            this.text = beginning + input + end;
        }
        // if i is at the beginning, add input before the string
        else {
            this.text = input + this.text;
        }
        redo.clear();
        // adds the steps of the operation into the undo stack
        int[] undoItems = new int[]{i, input.length() + i, 1};
        undo.multiPush(undoItems);
    }

    /**
     * This method inserts the input into the text
     * @param i an int the represents the start
     * @param j an int the represents the end
     * @exception IllegalArgumentException if i or j is out of bound,
     * or i is not smaller than j
     */
    public void delete(int i, int j) {
        if (i >= j) {
            throw new IllegalArgumentException();
        }
        if (i < 0 || j > text.length()) {
            throw new IllegalArgumentException();
        }
        String deletedLetters;
        // if i is anywhere in the string
        if (i > 0) {
            // get the substrings
            String beginning = this.text.substring(0, i);
            String end = this.text.substring(j);
            // these are the deleted characters
            deletedLetters = this.text.substring(i,j);
            this.text = beginning + end;
        }
        // if i = 0
        else {
            // delete everything up to index j
            deletedLetters = this.text.substring(0,j);
            this.text = this.text.substring(j);
        }
        redo.clear();
        // pushes the delete operation into undo stack
        int[] undoItems = new int[]{i, j, DOUBLE};
        undo.multiPush(undoItems);
        // push the deleted letters into the deletedText
        deletedText.push(deletedLetters);
    }

    /**
     * This method will undo the previous move
     * @return a boolean. false if theres no operations to undo/true if
     * there is
     */
    public boolean undo() {
        // nothing to undo
        if (undo.isEmpty()) {
            return false;
        }
        // get the operation, j, and i
        int typeOp = undo.pop();
        int j = undo.pop();
        int i = undo.pop();
        // if it was deleted, now insert it
        if (typeOp == DOUBLE) {
            insert(i, this.deletedText.pop());
        } // if it was inserted, now delete
        else if (typeOp == 1) {
            this.insertedText.push(this.text.substring(i, j));
            delete(i, j);
        }
        // if it was case converted, convert it back
        else {
            caseConvert(i, j);
        }
        //push operation to the redo stack
        int[] redoItems = new int[]{i, j, typeOp};
        redo.multiPush(redoItems);
        return true;
    }

    /**
     * This method will redo the previous undo move
     * @return a boolean. false if theres no operations to undo/true if
     * there is
     */
    public boolean redo() {
        // nothing to redo
        if (redo.isEmpty()) {
            return false;
        }
        // get the operation, j, and i
        int typeOp = redo.pop();
        int j = redo.pop();
        int i = redo.pop();
        // if the operation is 2, it will be deleting
        if (typeOp == DOUBLE) {
            deletedText.push(text.substring(i,j));
            delete(i,j);
        } // if the operation is 1, it will insert
        else if (typeOp == 1) {
            insert(i, insertedText.pop());
        } // if the operation is 0, case convert it back
        else {
            caseConvert(i, j);
        }
        //push operation to undo stack
        int[] undoItems = new int[]{i, j, typeOp};
        undo.multiPush(undoItems);
        return true;
    }

//    public static void main(String[] args) {
//        TextEditor test = new TextEditor();
//        test.insert(0,"C");
//        test.insert(0,"s");
//        test.insert(0,"d");
//        test.insert(0,".");
//        test.insert(0,"c");
//        test.insert(0,"S");
//        test.insert(0,"d");
//        test.insert(0,".");
//        test.insert(0,"c");
//        test.insert(0,"s");
//        test.insert(0,"D");
//        test.caseConvert(1,4);
//        test.insert(11, "CSE");
//        test.delete(0,11);
//        test.undo();
//        test.redo();
//        test.undo();
//        System.out.println(test.text);
//    }
}
