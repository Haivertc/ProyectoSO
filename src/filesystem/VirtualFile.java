package filesystem;

public class VirtualFile {

    private String name;
    private StringBuilder content;

    public VirtualFile(String name) {
        this.name = name;
        this.content = new StringBuilder();
    }

    public String getName() {
        return name;
    }

    public synchronized void write(String text) {
        content.append(text);
    }

    public synchronized String read() {
        return content.toString();
    }

    @Override
    public String toString() {
        return "VirtualFile(" + name + ")";
    }
}
