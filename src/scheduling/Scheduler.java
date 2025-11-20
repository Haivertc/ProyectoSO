package scheduling;

import process.PCB;
import java.util.List;

public interface Scheduler {
    void addProcess(PCB process);
    PCB getNextProcess();
    boolean hasProcesses();

    /**
     * Devuelve procesos que están en READY (pendientes), NO incluye el proceso en CPU.
     * Esto permite actualizar waiting times tick a tick.
     */
    List<PCB> getPendingProcesses();
}
