package scheduling;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import process.PCB;

public class RoundRobinScheduler implements Scheduler {

    private Queue<PCB> readyQueue;
    private int quantum;

    public RoundRobinScheduler(int quantum) {
        this.quantum = quantum;
        this.readyQueue = new LinkedList<>();
    }

    @Override
    public void addProcess(PCB p) {
        readyQueue.add(p);
    }

    @Override
    public PCB getNextProcess() {
        return readyQueue.poll();
    }

    @Override
    public boolean hasProcesses() {
        return !readyQueue.isEmpty();
    }

    @Override
    public List<PCB> getPendingProcesses() {
        return new ArrayList<>(readyQueue);
    }

    public int getQuantum() {
        return quantum;
    }
}
