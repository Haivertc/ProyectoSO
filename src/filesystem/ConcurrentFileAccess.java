package filesystem;

import process.PCB;

public class ConcurrentFileAccess {

    private VirtualFile file;
    private FileLock lock;

    public ConcurrentFileAccess(VirtualFile file) {
        this.file = file;
        this.lock = new FileLock();
    }

    public boolean requestAccess(PCB process) {
        return lock.tryLock(process);
    }

    public void releaseAccess(PCB process) {
        lock.unlock(process);
    }

    public void write(PCB process, String text) {
        file.write("P" + process.getPid() + ": " + text + "\n");
    }

    public String read() {
        return file.read();
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    public PCB getOwner() {
        return lock.getOwner();
    }

    public String getFileName() {
        return file.getName();
    }
}
