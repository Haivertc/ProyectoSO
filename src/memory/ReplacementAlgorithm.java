package memory;

import process.PCB;

public interface ReplacementAlgorithm {
    int chooseFrame(Frame[] frames);
}
