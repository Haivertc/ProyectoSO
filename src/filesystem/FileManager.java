package filesystem;

import java.util.HashMap;
import java.util.Map;

import process.PCB;

public class FileManager {

    private Map<String, ConcurrentFileAccess> files;
    private int conflictCount = 0;

    public FileManager() {
        files = new HashMap<>();
    }

    public void createFile(String name) {
        files.put(name, new ConcurrentFileAccess(new VirtualFile(name)));
    }

    private ConcurrentFileAccess getFile(String name) {
        return files.get(name);
    }

    public boolean requestFileAccess(PCB process, String fileName) {
        ConcurrentFileAccess file = getFile(fileName);

        if (file == null) {
            System.out.println("File not found: " + fileName);
            return false;
        }

        boolean granted = file.requestAccess(process);

        if (!granted) {
            conflictCount++;
        }

        return granted;
    }

    public void writeToFile(PCB process, String fileName, String text) {
        ConcurrentFileAccess file = getFile(fileName);
        if (file != null && file.getOwner() == process) {
            file.write(process, text);
        }
    }

    public void releaseFile(PCB process, String fileName) {
        ConcurrentFileAccess file = getFile(fileName);
        if (file != null) {
            file.releaseAccess(process);
        }
    }

    public String readFile(String fileName) {
        ConcurrentFileAccess file = getFile(fileName);
        if (file != null) return file.read();
        return null;
    }

    public int getConflictCount() {
        return conflictCount;
    }

    public void printStatus() {
        System.out.println("=== FILE SYSTEM STATUS ===");
        for (var entry : files.entrySet()) {
            ConcurrentFileAccess f = entry.getValue();
            String owner = (f.getOwner() != null ? "P" + f.getOwner().getPid() : "NONE");

            System.out.println(
                "- " + f.getFileName() + 
                " | Locked: " + f.isLocked() + 
                " | Owner: " + owner);
        }
        System.out.println("=========================\n");
    }
}
