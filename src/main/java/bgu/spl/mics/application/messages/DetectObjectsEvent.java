package bgu.spl.mics.application.messages;
import java.util.ArrayList;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.DetectedObject;

public class DetectObjectsEvent  implements Event{

        public Future future;
        public int time;
        public ArrayList<DetectedObject> objects;
    
    
        public DetectObjectsEvent(int time, ArrayList<DetectedObject> objects){
            this.future = new Future();
            this.time = time;
            this.objects = objects;
        }
    
        public Future getFuture(){
            return this.future;
        }
        @Override
        public void resolveFuture(Object result){
            this.future.resolve(result);
        }

        public ArrayList<DetectedObject> getObjects(){
            return this.objects;
        }
    
    }


