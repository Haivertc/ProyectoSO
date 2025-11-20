package memory;

import java.util.HashMap;
import java.util.Map;

public class PageTable {

    // pageNumber → frameIndex
    private Map<Integer, Integer> table;

    public PageTable() {
        table = new HashMap<>();
    }

    public boolean isLoaded(int pageNumber) {
        return table.containsKey(pageNumber);
    }

    public void mapPage(int pageNumber, int frameIndex) {
        table.put(pageNumber, frameIndex);
    }

    public void unmapPage(int pageNumber) {
        table.remove(pageNumber);
    }

    public Integer getFrameIndex(int pageNumber) {
        return table.get(pageNumber);
    }

    public void printTable(int pid) {
    System.out.println("Tabla de páginas de P" + pid + ":");
    if (table.isEmpty()) {
        System.out.println("  (sin páginas cargadas)");
        return;
    }
    for (var entry : table.entrySet()) {
        System.out.println("  Página " + entry.getKey() + " → Frame " + entry.getValue());
    }
}

}
