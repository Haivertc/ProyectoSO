package scheduling;

import process.PCB;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class SJFScheduler implements Scheduler {

    private PriorityQueue<PCB> queue;

    public SJFScheduler() {
        queue = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.getRemainingTime(), b.getRemainingTime()));
    }

    @Override
    public void addProcess(PCB p) {
        queue.add(p);
    }

    @Override
    public PCB getNextProcess() {
        return queue.poll();
    }

    @Override
    public boolean hasProcesses() {
        return !queue.isEmpty();
    }

    @Override
    public List<PCB> getPendingProcesses() {
        return new ArrayList<>(queue);
    }
}
