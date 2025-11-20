package core;

import process.PCB;
import process.ProcessGenerator;
import scheduling.*;
import memory.*;
import filesystem.*;
import metrics.*;

import java.util.List;
import java.util.Random;

public class OSSimulator {

    public static void main(String[] args) {

        // Configuración (puedes exponer por args)
        String schedulerType = "RR"; // "RR", "SJF", "PRIORITY"
        int quantum = 3;
        int frameCount = 4;
        int processCount = 6;
        String replacement = "FIFO"; // "FIFO" o "LRU"

        // --- Inicializar componentes ---
        Scheduler scheduler;
        switch (schedulerType.toUpperCase()) {
            case "SJF":
                scheduler = new SJFScheduler();
                break;
            case "PRIORITY":
                scheduler = new PriorityScheduler();
                break;
            case "RR":
            default:
                scheduler = new RoundRobinScheduler(quantum);
                break;
        }

        ReplacementAlgorithm algo = replacement.equalsIgnoreCase("FIFO")
                ? new FIFOReplacement()
                : new LRUReplacement();

        MemoryManager memManager = new MemoryManager(frameCount, algo);
        FileManager fileManager = new FileManager();
        SimulationMetrics metrics = new SimulationMetrics();

        // Crear archivos
        fileManager.createFile("data.txt");
        fileManager.createFile("log.txt");

        // Generar procesos
        List<PCB> processes = ProcessGenerator.generateProcessList(processCount);

        // Asignar arrival time usando clock más abajo (todos se insertan en t=0 aquí)
        int clock = 0;
        for (PCB p : processes) {
            p.setArrivalTime(clock); // todos llegan en tick 0
            p.setState(process.ProcessState.READY);
            scheduler.addProcess(p);
        }

        System.out.println("=== INICIANDO SIMULACIÓN ===");
        System.out.println("Scheduler: " + schedulerType + " | Frames: " + frameCount + " | Replacement: " + replacement);
        System.out.println("Procesos generados: " + processes.size() + "\n");

        metrics.startSimulation();

        Random rnd = new Random();

        int lastPid = -1; // para contar context switches al cambiar de proceso

        // Ciclo principal: seguimos hasta que no haya procesos pendientes
        while (scheduler.hasProcesses()) {
            PCB current = scheduler.getNextProcess();
            if (current == null) break;

            // CONTEXT SWITCH: contar si cambiamos de proceso respecto al anterior
            if (lastPid != -1 && current.getPid() != lastPid) {
                metrics.increaseContextSwitch();
            }
            lastPid = current.getPid();

            current.setState(process.ProcessState.RUNNING);
            System.out.println("Tick " + clock + " - Ejecutando proceso: " + current);

            // decidir cuántos ticks consecutivos puede ejecutar antes de re-evaluar
            int maxRun;
            if (scheduler instanceof RoundRobinScheduler) {
                maxRun = Math.min(((RoundRobinScheduler) scheduler).getQuantum(), current.getRemainingTime());
            } else {
                // no-preemptive SJF/Priority: ejecuta hasta completar
                maxRun = current.getRemainingTime();
            }

            boolean processFinished = false;

            // ejecutar tick a tick
            for (int r = 0; r < maxRun; r++) {
                // --- 1 tick ---
                clock++; // avanzamos el clock

                // Acceso a memoria (una página al azar del proceso)
                int pagesRequired = Math.max(1, current.getRequiredPages());
                int pageToAccess = rnd.nextInt(pagesRequired);
                boolean hit = memManager.accessPage(current, pageToAccess);
                metrics.registerMemoryAccess();
                if (!hit) {
                    metrics.registerPageFault();
                    System.out.println("Tick " + clock + " - Page fault: P" + current.getPid() + " page " + pageToAccess);
                }

                // Acceso a archivo (si corresponde)
                if (!current.getFilesToAccess().isEmpty()) {
                    String fileName = current.getFilesToAccess().get(0);
                    boolean granted = fileManager.requestFileAccess(current, fileName);
                    if (granted) {
                        fileManager.writeToFile(current, fileName, "Escritura desde P" + current.getPid() + " en tick " + clock);
                        metrics.registerFileWrite();
                        fileManager.releaseFile(current, fileName);
                    } else {
                        // si el archivo no existe, requestFileAccess imprimirá "File not found"
                        metrics.registerFileConflict();
                        System.out.println("Tick " + clock + " - Conflicto de archivo: P" + current.getPid() + " no obtuvo " + fileName);
                    }
                }

                // Reducir tiempo del proceso 1 unidad
                current.reduceRemainingTime(1);

                // Aumentar waitingTime en 1 para todos los procesos en READY
                List<PCB> pending = scheduler.getPendingProcesses();
                for (PCB p : pending) {
                    p.addWaitingTime(1);
                }

                // Si el proceso terminó durante este tick
                if (current.getRemainingTime() <= 0) {
                    current.setState(process.ProcessState.TERMINATED);
                    current.setFinishTime(clock); // finish = current simulation clock
                    metrics.registerProcessFinished(current);
                    System.out.println("Tick " + clock + " - Proceso terminado: P" + current.getPid());
                    processFinished = true;
                    break; // salir del for
                }
            } // end for ticks of this process

            // Si el proceso no terminó, reinsertarlo (aplica para RR)
            if (!processFinished) {
                current.setState(process.ProcessState.READY);
                scheduler.addProcess(current);
            }

            // imprimir estado de memoria y archivos (opcional)
            memManager.printMemory();
            memManager.printAllPageTables(processes);
            fileManager.printStatus();
        }

        // Fin de simulación
        metrics.endSimulation();

        System.out.println("\n=== SIMULACIÓN FINALIZADA ===\n");
        metrics.printReport();

        // Export results
        ResultsExporter.export("simulation_results.txt", metrics.exportToMap());
    }
}
