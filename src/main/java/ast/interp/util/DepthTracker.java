package ast.interp.util;

public final class DepthTracker {
    private int depth = 0;
    public void markEntry(){
        depth++;
    }
    public void markExit(){
        depth--;
    }

    public int depth(){
        return depth;
    }
}
