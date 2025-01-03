package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Camera;

/**
 * CameraService is responsible for processing data from the camera and
 * sending DetectObjectsEvents to LiDAR workers.
 * 
 * This service interacts with the Camera object to detect objects and updates
 * the system's StatisticalFolder upon sending its observations.
 */
public class CameraService extends MicroService {
    public Camera camera;

    /**
     * Constructor for CameraService.
     *
     * @param camera The Camera object that this service will use to detect objects.
     */
    public CameraService(Camera camera) { 
        super("Camera_" + camera.id);
        this.camera = camera;
        
    }

    /**
     * Initializes the CameraService.
     * Registers the service to handle TickBroadcasts and sets up callbacks for sending
     * DetectObjectsEvents.
     */
    @Override
    protected void initialize() {  //subscribes to all relevant messages
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminatedBroadcast.class, broadcast -> {
            //TODO: implement
        });
        subscribeBroadcast(TickBroadcast.class, tick -> {
            //TODO: implement
        });
        subscribeBroadcast(CrashedBroadcast.class, broadcast -> {
            //TODO: implement
        });  
    }
}
