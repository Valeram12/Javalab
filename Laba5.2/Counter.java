
import java.util.Arrays;

public class Counter {
    private int threadCount = 0;
    private boolean stop = false;
    private int[] threadData = new int[4];
    public synchronized void setThreadInfo(int data) throws InterruptedException {
        threadData[threadCount] = data;
        threadCount++;
        if (threadCount == 4) {
            threadCount = 0;
            System.out.println("");
            checkEquality();
        }
    }
    private void checkEquality() {
        Arrays.sort(threadData);
        if ((threadData[0] == threadData[1] && threadData[1] == threadData[2]) ||
        (threadData[1] == threadData[2] && threadData[2] == threadData[3])) {
            stop = true;
        }
    }
    public boolean getStop() {
        return stop;
    }
}
