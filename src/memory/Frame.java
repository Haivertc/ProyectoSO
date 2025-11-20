package memory;

import process.PCB;

public class Frame {
    private Page page;
    private PCB owner;
    private long lastUsedTime;  // para LRU

    public boolean isFree() {
        return page == null;
    }

    public void loadPage(Page page, PCB owner) {
        this.page = page;
        this.owner = owner;
        updateLastUsed();
    }

    public void free() {
        this.page = null;
        this.owner = null;
    }

    public Page getPage() { return page; }
    public PCB getOwner() { return owner; }

    public long getLastUsedTime() { return lastUsedTime; }
    public void updateLastUsed() { 
        this.lastUsedTime = System.currentTimeMillis(); 
    }

    @Override
    public String toString() {
        if (page == null) return "[Empty]";
        return "[P" + owner.getPid() + " - Page " + page.getPageNumber() + "]";
    }
}
