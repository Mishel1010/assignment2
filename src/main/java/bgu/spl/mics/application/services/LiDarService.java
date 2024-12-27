package bgu.spl.mics.application.services;
import bgu.spl.mics.application.messages.DetectEbjectsEvent;

import java.io.FileReader;
import java.util.ArrayList;

import javax.security.auth.login.Configuration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.LiDarDataBase;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.StampedCloudPoints;

/**
 * LiDarService is responsible for processing data from the LiDAR sensor and
 * sending TrackedObjectsEvents to the FusionSLAM service.
 * 
 * This service interacts with the LiDarTracker object to retrieve and process
 * cloud point data and updates the system's StatisticalFolder upon sending its
 * observations.
 */
public class LiDarService extends MicroService {
    public LiDarWorkerTracker lidarTracker;
    private int counter;
    private int time;
    private String dataBasePath;
    
    /**
     * Constructor for LiDarService.
     *
     * @param liDarTracker The LiDAR tracker object that this service will use to process data.
     */
    public LiDarService(LiDarTracker liDarTracker) {
        super("Lidar" + liDarTracker);
        this.lidarTracker = lidarTracker;
        this.counter = 0;
        FileReader reader = new FileReader("./config.json");
            JsonObject configJson = JsonParser.parseReader(reader).getAsJsonObject();

            // Extract the "Lidars" object
            JsonObject lidarsObject = configJson.getAsJsonObject("Lidars");

            // Extract the "lidars_data_path"
            String lidarsDataPath = lidarsObject.get("lidars_data_path").getAsString();
            this.dataBasePath = lidarsDataPath;
            this.time = 0;
    }

    /**
     * Initializes the LiDarService.
     * Registers the service to handle DetectObjectsEvents and TickBroadcasts,
     * and sets up the necessary callbacks for processing data.
     */
    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeEvent(DetectObjectsEvents.class, type ->{
          time++;
          ArrayList<StampedCloudPoints> clouds = LiDarDataBase.getInstance(dataBasePath).getCloudPoints();

          while (clouds[counter].getTime() < this.time){
            counter++;
          while(clouds[counter].getTime() == this.time){
            

                     
                    }
                }

                
            

        } 
    }
}
