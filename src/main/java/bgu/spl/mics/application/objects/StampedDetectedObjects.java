package bgu.spl.mics.application.objects;

import java.util.ArrayList;

import scala.collection.immutable.List;

/**
 * Represents objects detected by the camera at a specific timestamp.
 * Includes the time of detection and a list of detected objects.
 */
public class StampedDetectedObjects {
    private int time;
    private ArrayList<DetectedObject> DetectedObjects;


public StampedDetectedObjects(int time){
    this.time = time;
    this.DetectedObjects = new ArrayList<DetectedObject>();
}



















}