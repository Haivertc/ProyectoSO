package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ResultsExporter {

    public static void export(String filename, Map<String, Object> data) {
        try (FileWriter writer = new FileWriter(filename)) {

            writer.write("=== SIMULATION RESULTS ===\n\n");

            for (var entry : data.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            System.out.println("Results exported to " + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
