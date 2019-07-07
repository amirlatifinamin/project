package sample;

import java.util.Timer;
import java.util.TimerTask;

public class TimerClass {
    public Timer timer1;
    private int timeCounter;
    public boolean reset;
    public boolean pause;

    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
            while (getTimeCounter() > 0) {
                if (reset){
                    setTimeCounter(0);
                    reset = false;
                }
                if (!pause) {
                    setTimeCounter(getTimeCounter() - 1);
                    ControlPanel.updateTimer(getTimeCounter());
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
            ControlPanel.gameFinished();
            task1.cancel();
            timer1.cancel();
            timer1.purge();
        }
    };

    public TimerClass(){
        reset = false;
        pause = false;
        timeCounter = 0;
        timer1 = new Timer();
    }

    public void start(int initial){
        ControlPanel.finished = false;
        timeCounter = initial;
        timer1.schedule(task1, 1000);
    }

    public int getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(int timeCounter) {
        this.timeCounter = timeCounter;
    }
}
