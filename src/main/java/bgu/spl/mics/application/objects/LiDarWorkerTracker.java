package bgu.spl.mics.application.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * LiDarWorkerTracker is responsible for managing a LiDAR worker.
 * It processes DetectObjectsEvents and generates TrackedObjectsEvents by using data from the LiDarDataBase.
 * Each worker tracks objects and sends observations to the FusionSlam service.
 */
public class LiDarWorkerTracker {
   public int id;
   public int frequency;
   public STATUS status;
   public ArrayList<TrackedObject> LastTrackedObjects;



   public LiDarWorkerTracker(int id, int frequency){
    this.id = id;
    this.frequency = frequency;
    status = STATUS.ERROR;
    LastTrackedObjects = new ArrayList<>();
   }
}
