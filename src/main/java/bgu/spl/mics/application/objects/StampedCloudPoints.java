package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents a group of cloud points corresponding to a specific timestamp.
 * Used by the LiDAR system to store and process point cloud data for tracked objects.
 */
public class StampedCloudPoints {
    private String id;
    private int time;
    private ArrayList<ArrayList<CloudPoint>> cloudPoints;



    public StampedCloudPoints(String id, int time, ArrayList<ArrayList<CloudPoint>> cloudPoints){
        this.id = id;
        this.time = time;
        this.cloudPoints = cloudPoints;
    }


    public int getTime(){
        return this.time;
    }

    public ArrayList<ArrayList<CloudPoint>> getCloudPoints(){
        return this.cloudPoints;
    }
}
