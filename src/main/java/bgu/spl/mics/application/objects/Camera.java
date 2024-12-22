package bgu.spl.mics.application.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {
    private int id;
    private int frequency;
    private STATUS status;
    private ArrayList<StampedDetectedObjects> DetectObjectsList;




    public Camera(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        this.status = STATUS.ERROR;
        this.DetectObjectsList = new ArrayList<>();
    }
}






 