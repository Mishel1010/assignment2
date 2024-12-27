package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.Pose;
   
public class PoseEvent implements Event<Pose> { 

    //fields
    public Future future;
    public Pose pose;

    //constructor
    public PoseEvent(Pose pose) {
        this.future = new Future();
        this.pose = pose;
    }

    //methods
    public Pose getPose() {
        return pose;
    }   

    public Future getFuture() {
        return future;
    }

    public void resolveFuture(Pose result) {
        future.resolve(result);
    }    
}

