package bgu.spl.mics.application.objects;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

/**
 * LiDarDataBase is a singleton class responsible for managing LiDAR data.
 * It provides access to cloud point data and other relevant information for tracked objects.
 */
public class LiDarDataBase {

    private ArrayList<StampedCloudPoints> cloudPoints;
    private static volatile LiDarDataBase instance;

    // Private constructor to prevent instantiation
    private LiDarDataBase() {
        this.cloudPoints = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of LiDarDataBase. Loads the data from a file if not already loaded.
     *
     * @param filePath The path to the LiDAR data file.
     * @return The singleton instance of LiDarDataBase.
     */
    public static LiDarDataBase getInstance(String filePath) {
        if (instance == null) {
            synchronized (LiDarDataBase.class) {
                if (instance == null) {
                    instance = new LiDarDataBase();
                    instance.load(filePath); // Load data into the singleton instance
                }
            }
        }
        return instance;
    }

    /**
     * Loads the LiDAR data from the specified file.
     *
     * @param filePath The path to the LiDAR data file.
     */
    private void load(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<StampedCloudPoints> parsedCloudPoints = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                JsonObject row = element.getAsJsonObject();

                // Parse time and id
                int time = row.get("time").getAsInt();
                String id = row.get("id").getAsString();

                // Parse cloudPoints
                JsonArray cloudPointsArray = row.getAsJsonArray("cloudPoints");
                ArrayList<ArrayList<CloudPoint>> cloudPointsList = new ArrayList<>();

                for (JsonElement groupElement : cloudPointsArray) {
                    JsonArray pointGroup = groupElement.getAsJsonArray();
                    ArrayList<CloudPoint> pointList = new ArrayList<>();

                    for (JsonElement pointElement : pointGroup) {
                        JsonArray point = pointElement.getAsJsonArray();
                        int x = point.get(0).getAsInt();
                        int y = point.get(1).getAsInt();

                        // Add CloudPoint to the group
                        pointList.add(new CloudPoint(x, y));
                    }

                    // Add group to the cloud points list
                    cloudPointsList.add(pointList);
                }

                // Create StampedCloudPoints and add to the list
                StampedCloudPoints stampedCloudPoints = new StampedCloudPoints(id, time, cloudPointsList);
                parsedCloudPoints.add(stampedCloudPoints);
            }

            // Assign the parsed data to the class field
            this.cloudPoints = parsedCloudPoints;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter for cloudPoints
    public ArrayList<StampedCloudPoints> getCloudPoints() {
        return this.cloudPoints;
    }
}
