package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorUtil {
    private static List<String> generateRandomColors(int numColors) {
        List<String> colors = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numColors; i++) {
            // Generate random RGB values for each color
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            String color = "rgb(" + r + ", " + g + ", " + b + ")";
            colors.add(color);
        }
        return colors;
    }
}
