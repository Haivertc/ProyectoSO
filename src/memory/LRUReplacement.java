package memory;

public class LRUReplacement implements ReplacementAlgorithm {

    @Override
    public int chooseFrame(Frame[] frames) {
        int chosen = 0;
        long oldest = frames[0].getLastUsedTime();

        for (int i = 1; i < frames.length; i++) {
            if (frames[i].getLastUsedTime() < oldest) {
                oldest = frames[i].getLastUsedTime();
                chosen = i;
            }
        }

        return chosen;
    }
}
