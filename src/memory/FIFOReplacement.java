package memory;

public class FIFOReplacement implements ReplacementAlgorithm {

    private int pointer = 0;

    @Override
    public int chooseFrame(Frame[] frames) {
        int frameIndex = pointer;
        pointer = (pointer + 1) % frames.length;
        return frameIndex;
    }
}
