package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents the robot's GPS and IMU system.
 * Provides information about the robot's position and movement.
 */
public class GPSIMU {
    private int currentTick;
    private STATUS status;
    private ArrayList<Pose> PoseList;



public GPSIMU (){
    this.currentTick = 0;
    this.status = STATUS.ERROR;
    this.PoseList = new ArrayList<>();
}

public Pose getPose(){
    return PoseList.get(currentTick);
}
}