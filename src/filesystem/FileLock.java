package filesystem;

import process.PCB;

public class FileLock {

    private boolean locked = false;
    private PCB owner = null;

    public synchronized boolean tryLock(PCB process) {
        if (!locked) {
            locked = true;
            owner = process;
            return true;
        }
        return false;
    }

    public synchronized void unlock(PCB process) {
        if (locked && owner == process) {
            locked = false;
            owner = null;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public PCB getOwner() {
        return owner;
    }
}
