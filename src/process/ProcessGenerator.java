package process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator {

    private static int counter = 1;
    private static Random random = new Random();

    public static PCB createRandomProcess() {
        int pid = counter++;
        int priority = random.nextInt(5) + 1;
        int burstTime = random.nextInt(9) + 2; // entre 2 y 10
        int pages = random.nextInt(5) + 1;     // entre 1 y 5 páginas

    // Only generate existing files to avoid "File not found"
    List<String> files = new ArrayList<>();
    String[] existingFiles = {"data.txt", "log.txt"};
    if (random.nextBoolean()) {
        files.add(existingFiles[random.nextInt(existingFiles.length)]);
    }

        PCB pcb = new PCB(pid, priority, burstTime, pages, files);
        return pcb;
    }

    public static List<PCB> generateProcessList(int count) {
        List<PCB> processes = new ArrayList<>();
        for (int i = 0; i < count; i++)
            processes.add(createRandomProcess());
        return processes;
    }
}
