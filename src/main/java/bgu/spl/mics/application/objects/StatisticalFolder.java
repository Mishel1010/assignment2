package bgu.spl.mics.application.objects;

/**
 * Holds statistical information about the system's operation.
 * This class aggregates metrics such as the runtime of the system,
 * the number of objects detected and tracked, and the number of landmarks identified.
 */
public class StatisticalFolder {
    private int systemRuntime;
    private int numDetectedObjects;
    private int numTrackedObjects;
    private int numLandmarks;
    private static StatisticalFolder instance;
    
    
    private StatisticalFolder(int systemRuntime){
        this.systemRuntime = systemRuntime;
        this.numDetectedObjects = 0;
        this.numTrackedObjects = 0;
        this.numLandmarks = 0;
    }

    public StatisticalFolder getInstance(int systemRuntime){
      if (instance != null){
         return instance;
      }
      synchronized(Instance){

      }
   }
    
   
   public StatisticalFolder withIncrementedDetectedObjects() {
      return new StatisticalFolder(systemRuntime, numDetectedObjects + 1, numTrackedObjects, numLandmarks);
  }

  /**
   * Creates a new instance of StatisticalFolder with an incremented tracked object count.
   * @return A new StatisticalFolder instance with updated tracked object count.
   */
  public StatisticalFolder withIncrementedTrackedObjects() {
      return new StatisticalFolder(systemRuntime, numDetectedObjects, numTrackedObjects + 1, numLandmarks);
  }

  /**
   * Creates a new instance of StatisticalFolder with an incremented landmark count.
   * @return A new StatisticalFolder instance with updated landmark count.
   */
  public StatisticalFolder withIncrementedLandmarks() {
      return new StatisticalFolder(systemRuntime, numDetectedObjects, numTrackedObjects, numLandmarks + 1);
  }
}
     
   


}

