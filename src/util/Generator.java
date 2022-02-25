package util;

import java.util.List;
import java.util.ArrayList;

public class Generator {

    public List<int[]> generateTestCases(int min, int max, int count, int density) {
        int densePointCount = (int) (count * 0.1 * density);
        count = count - densePointCount;
        List<int[]> pointsList = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            int[] point = new int[2];
            // generates x values
            point[0] = (int)(min + Math.random() * (max - min));
            // generates y values
            point[1] = (int)(min + Math.random() * (max - min));

            pointsList.add(point);
        }

        //Generate dense area
        double denseArea = 0.1;
        double randomPaddingX = Math.random() * (max - min)/2;
        double randomPaddingY = Math.random() * (max - min)/2;
        for(int j = 0; j < densePointCount; j++) {
            int[] point = new int[2];
            // generates x values
            point[0] = (int)(min + Math.random() * (max - min) * denseArea + randomPaddingX);
            // generates y values
            point[1] = (int)(min + Math.random() * (max - min) * denseArea + randomPaddingY);

            pointsList.add(point);
        }
        return pointsList;
    }

    public int[] generateRandomPoint(int min, int max) {
        int[] point = new int[2];
        // generates x values
        point[0] = (int)(min + Math.random() * (max - min));
        // generates y values
        point[1] = (int)(min + Math.random() * (max - min));
        
        return point;
    }
}