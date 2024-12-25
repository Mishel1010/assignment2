package bgu.spl.mics.application.messages;

import java.util.ArrayList;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.TrackedObject;

public class TrackedObjectsEvent implements Event {
    public Future future;
    public ArrayList<TrackedObject> objects;

    public TrackedObjectsEvent(ArrayList<TrackedObject> objects){
        this.future = new Future<>();
        this.objects = objects;
    }

    @Override
    public Future getFuture() {
     return this.future;
    }

    @Override
    public void resolveFuture(Object result) {
      this.future.resolve(result);
    }

}