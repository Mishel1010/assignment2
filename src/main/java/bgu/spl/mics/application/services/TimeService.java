package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * TimeService acts as the global timer for the system, broadcasting TickBroadcast messages
 * at regular intervals and controlling the simulation's duration.
 */
public class TimeService extends MicroService {
    public int tickTime;
    public int duration;
    private int currentTick;

    /**
     * Constructor for TimeService.
     *
     * @param TickTime  The duration of each tick in milliseconds.
     * @param Duration  The total number of ticks before the service terminates.
     */
    public TimeService(int TickTime, int Duration) {
        super("TimeService");
        this.tickTime = TickTime;
        this.duration = Duration;
        this.currentTick = 0;
    }

    /**
     * Initializes the TimeService.
     * Starts broadcasting TickBroadcast messages and terminates after the specified duration.
     */
    @Override
    protected void initialize() {
        Thread timerThread = new Thread(() -> {
            while (currentTick < duration) 
            {
                try
                {
                    Thread.sleep(tickTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                currentTick++;
                sendBroadcast(new TickBroadcast(currentTick));
            }
            terminate();
        });
        timerThread.start();
    }
}
