package memory;

import java.util.List;

import process.PCB;

public class MemoryManager {

    private Frame[] frames;
    private ReplacementAlgorithm algorithm;

    private int pageFaults = 0;

    public MemoryManager(int frameCount, ReplacementAlgorithm algorithm) {
        this.frames = new Frame[frameCount];
        for (int i = 0; i < frameCount; i++)
            frames[i] = new Frame();
        this.algorithm = algorithm;
    }

    public boolean accessPage(PCB process, int pageNumber) {
        Page page = new Page(pageNumber);

        PageTable table = process.getPageTable();

        // Si la página YA está en memoria
        if (table.isLoaded(pageNumber)) {
            int frameIndex = table.getFrameIndex(pageNumber);
            frames[frameIndex].updateLastUsed();
            return true; // acceso sin fallos
        }

        // Page fault
        pageFaults++;

        // Buscar un frame libre
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isFree()) {
                frames[i].loadPage(page, process);
                table.mapPage(pageNumber, i);
                return false; // hubo fallo, pero sin reemplazo
            }
        }

        // No hay libres → aplicar reemplazo
        int victim = algorithm.chooseFrame(frames);

        // liberar la página anterior
        Page oldPage = frames[victim].getPage();
        PCB oldOwner = frames[victim].getOwner();

        oldOwner.getPageTable().unmapPage(oldPage.getPageNumber());

        // cargar la nueva
        frames[victim].loadPage(page, process);
        process.getPageTable().mapPage(pageNumber, victim);

        return false;
    }

    public int getPageFaults() { return pageFaults; }

    public void printMemory() {
        System.out.println("\n===== Memory State =====");
        for (int i = 0; i < frames.length; i++) {
            System.out.println("Frame " + i + ": " + frames[i]);
        }
        System.out.println("========================\n");
    }

    public void printAllPageTables(List<PCB> processes) {
        System.out.println("=== TABLAS DE PÁGINAS ===");
        for (PCB p : processes) {
            p.printPageTable();
        }
        System.out.println("=========================\n");
    }

}
