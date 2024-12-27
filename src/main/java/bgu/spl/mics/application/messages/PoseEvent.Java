public class PoseEvent implements Event { 

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
}