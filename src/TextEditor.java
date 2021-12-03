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
    private String lower = "abcdefghijklmnopqrstuvwxyz";
    private String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder newText = new StringBuilder(this.text);
        while (i < j) {
            if (Character.isLetter(newText.charAt(i)) == false) {
                i++;
                continue;
            }
            else if (Character.isLowerCase(newText.charAt(i))){
                newText.setCharAt(i, upper.charAt(lower.indexOf(newText.charAt(i))));
                i++;
            }
            else {
                newText.setCharAt(i, lower.charAt((upper.indexOf(newText.charAt(i)))));
                i++;
            }
        }

        this.text = newText.toString();
        this.redo.clear();
        int[] undoItems = new int[]{i, j, 0};
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
        if (i > 0) {
            String beginning = this.text.substring(0,i);
            String end = this.text.substring(i);
            this.text = beginning + input + end;
        }
        else {
            this.text = input + this.text;
        }
        redo.clear();
        undo.multiPush(new int[]{i, input.length() + i, 1});
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
        if (i > 0) {
            String beginning = this.text.substring(0, i);
            String end = this.text.substring(j);
            deletedLetters = this.text.substring(i,j);
            this.text = beginning + end;
        }
        else {
            deletedLetters = this.text.substring(0,j);
            this.text = this.text.substring(j);
        }
        redo.clear();
        int[] undoItems = new int[]{i, j, DOUBLE};
        undo.multiPush(undoItems);
        deletedText.push(deletedLetters);
    }

    /**
     * This method will undo the previous move
     * @return a boolean. false if theres no operations to undo/true if
     * there is
     */
    public boolean undo() {
        if (undo.isEmpty()) {
            return false;
        }
        int typeOp = undo.pop();
        int j = undo.pop();
        int i = undo.pop();

        //performs reverse of whatever operation was performed
        if (typeOp == DOUBLE) {
            insert(i, this.deletedText.pop());

        } else if (typeOp == 1) {
            this.insertedText.push(this.text.substring(i, j));
            delete(i, j);
        }
        else {
            caseConvert(i, j);
        }

//        //clears undo for undone operation so undo doesn't undo itself
//        for (i = 0; i <= DOUBLE; i++) {
//            undo.pop();
//        }

        //push operation to redo stack
        redo.multiPush(new int[]{i, j, typeOp});
        return true;
    }

    /**
     * This method will redo the previous undo move
     * @return a boolean. false if theres no operations to undo/true if
     * there is
     */
    public boolean redo() {
        if (redo.isEmpty()) {
            return false;
        }

        /*gets the top 3 values in the redo stack which indiciates the operation performed and
        indices where operation was performed */
        int typeOp = redo.pop();
        int j = redo.pop();
        int i = redo.pop();

        //performs reverse of whatever operation was performed
        if (typeOp == 2) {
            deletedText.push(text.substring(i,j));
            delete(i,j);
        } else if (typeOp == 1) {
            insert(i, insertedText.pop());
        } else {
            caseConvert(i, j);
        }
        //clears undo for undone operation so undo doesn't undo itself
        for (i = 0; i <= DOUBLE; i++) {
            undo.pop();
        }

        //push operation to undo stack
        undo.multiPush(new int[]{i, j, typeOp});
        return true;
    }

    public static void main(String[] args) {
        TextEditor test = new TextEditor();
        test.insert(0,"C");
        test.insert(0,"s");
        test.insert(0,"d");
        test.insert(0,".");
        test.insert(0,"c");
        test.insert(0,"S");
        test.insert(0,"d");
        test.insert(0,".");
        test.insert(0,"c");
        test.insert(0,"s");
        test.insert(0,"D");
//        test.caseConvert(1,4);
        test.insert(11, "CSE");
//        test.delete(0,11);
        test.undo();
        test.redo();
        System.out.println(test.text);
    }
}
