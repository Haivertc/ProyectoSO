package process;

import java.util.List;

import memory.PageTable;

public class PCB {

    private int pid;
    private int priority;
    private int burstTime;   // Duración total (CPU)
    private int remainingTime;
    private int requiredPages;
    private List<String> filesToAccess;
    private PageTable pageTable = new PageTable();


    private ProcessState state;

    // Métricas
    private int arrivalTime;
    private int waitingTime;
    private int finishTime;

    public PCB(int pid, int priority, int burstTime, int requiredPages, List<String> files) {
        this.pid = pid;
        this.priority = priority;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.requiredPages = requiredPages;
        this.filesToAccess = files;
        this.state = ProcessState.NEW;
    }

    public PageTable getPageTable() {
        return pageTable;
    }
    // Getters y setters
    public int getPid() { return pid; }
    public int getPriority() { return priority; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public void reduceRemainingTime(int quantum) {
        remainingTime = Math.max(0, remainingTime - quantum);
    }

    public int getRequiredPages() { return requiredPages; }
    public List<String> getFilesToAccess() { return filesToAccess; }

    public ProcessState getState() { return state; }
    public void setState(ProcessState state) { this.state = state; }

    public void printPageTable() {
        this.pageTable.printTable(this.pid);    
    }

    // Métricas
    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int t) { this.arrivalTime = t; }

    public int getWaitingTime() { return waitingTime; }
    public void addWaitingTime(int t) { this.waitingTime += t; }

    public int getFinishTime() { return finishTime; }
    public void setFinishTime(int t) { this.finishTime = t; }

    @Override
    public String toString() {
        return "PCB(pid=" + pid +
               ", priority=" + priority +
               ", burst=" + burstTime +
               ", remaining=" + remainingTime +
               ", pages=" + requiredPages +
               ", state=" + state + ")";
    }
}
