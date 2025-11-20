package memory;

public class Page {
    private int pageNumber;

    public Page(int num) {
        this.pageNumber = num;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public String toString() {
        return "Page(" + pageNumber + ")";
    }
}
