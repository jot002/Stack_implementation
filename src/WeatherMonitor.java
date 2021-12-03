/*
    Name: Jonathan Tran
    PID:  A15967290
 */

/**
 * WeatherMonitor is created to keep track of daily temperatures and
 * see how many consecutive days the current temperatuer is hotter than
 * the other days.
 * @author Jonathan Tran
 * @since  10/11/21
 */

public class WeatherMonitor {
    private int lastTemp;
    private int nConsec;
    private IntStack dailyTemp;
    private IntStack tempStack;
    private static final int CAP = 100;

    /**
     * This constructor sets the nConsec to 0, lastTemp to 0, and sets
     * dailyTemp and tempStack to both IntStack objects.
     */
    public WeatherMonitor() {
        dailyTemp = new IntStack(CAP);
        tempStack = new IntStack(CAP);
        nConsec = 0;
        lastTemp = 0;
    }

    /**
     * This method returns the total number of days that have a
     * temperature greater than the previous days.
     * @param temp int that is the temperature
     * @return an int that shows the total number of days
     */
    public int numDays(int temp) {
        this.nConsec = 0;
        // push temp if the stack is empty
        if (dailyTemp.isEmpty()) {
            dailyTemp.push(temp);
            // return 0 since there is nothing to compare
            return 0;
        }
        // keeps going while the stack has an element in it
        while (!dailyTemp.isEmpty()) {
            // if temp is greater than the most recent temp
            if (temp > dailyTemp.peek()) {
                // increase nConsec and remove from stack to
                // compare other values, adds to tempStack for
                // easy access to replace afterwards
                this.nConsec++;
                tempStack.push(dailyTemp.pop());
            } else {
                // stop if the recent temp is not less than the temp
                break;
            }
        }
        // puts the temperatures back into the stack
        while(!tempStack.isEmpty()){
            dailyTemp.push(tempStack.pop());
        }
        dailyTemp.push(temp);
        return this.nConsec;
    }
}
