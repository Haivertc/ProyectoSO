package metrics;

import process.PCB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationMetrics {

    // === MÉTRICAS DE PROCESOS ===
    private List<PCB> finishedProcesses = new ArrayList<>();
    private int contextSwitches = 0;

    // === MÉTRICAS DE MEMORIA ===
    private int pageFaults = 0;
    private int memoryAccesses = 0;

    // === MÉTRICAS DE ARCHIVOS ===
    private int fileConflicts = 0;
    private int fileReads = 0;
    private int fileWrites = 0;

    // === MÉTRICAS DE TIEMPO ===
    private long simulationStartTime;
    private long simulationEndTime;

    public void startSimulation() {
        simulationStartTime = System.currentTimeMillis();
    }

    public void endSimulation() {
        simulationEndTime = System.currentTimeMillis();
    }

    public long getTotalSimulationTime() {
        return simulationEndTime - simulationStartTime;
    }

    // === REGISTRO DE PROCESOS ===
    public void registerProcessFinished(PCB p) {
        finishedProcesses.add(p);
    }

    public void increaseContextSwitch() {
        contextSwitches++;
    }

    // === REGISTRO DE MEMORIA ===
    public void registerPageFault() {
        pageFaults++;
    }

    public void registerMemoryAccess() {
        memoryAccesses++;
    }

    // === REGISTRO DE ARCHIVOS ===
    public void registerFileConflict() {
        fileConflicts++;
    }

    public void registerFileRead() {
        fileReads++;
    }

    public void registerFileWrite() {
        fileWrites++;
    }

    // === REPORTES ===
    public void printReport() {
        System.out.println("\n=========== SIMULATION REPORT ===========");

        System.out.println("\n--- TIME METRICS ---");
        System.out.println("Total Simulation Time: " + getTotalSimulationTime() + " ms");
        System.out.println("Context Switches: " + contextSwitches);

        System.out.println("\n--- MEMORY METRICS ---");
        System.out.println("Memory Accesses: " + memoryAccesses);
        System.out.println("Page Faults: " + pageFaults);

        System.out.println("\n--- FILE SYSTEM METRICS ---");
        System.out.println("File Reads: " + fileReads);
        System.out.println("File Writes: " + fileWrites);
        System.out.println("File Conflicts: " + fileConflicts);

        System.out.println("\n--- PER-PROCESS METRICS ---");
        int i = 1;
        for (PCB p : finishedProcesses) {
            System.out.println(
                "\nProcess " + (i++) +
                " | PID: " + p.getPid() +
                " | Burst: " + p.getBurstTime() +
                " | Waiting Time: " + p.getWaitingTime() +
                " | Turnaround Time: " + (p.getFinishTime() - p.getArrivalTime())
            );
        }

        System.out.println("\n==========================================\n");
    }

    public Map<String, Object> exportToMap() {
        Map<String, Object> data = new HashMap<>();

        data.put("total_time", getTotalSimulationTime());
        data.put("context_switches", contextSwitches);
        data.put("page_faults", pageFaults);
        data.put("memory_accesses", memoryAccesses);
        data.put("file_reads", fileReads);
        data.put("file_writes", fileWrites);
        data.put("file_conflicts", fileConflicts);
        data.put("finished_processes", finishedProcesses.size());

        return data;
    }
}
