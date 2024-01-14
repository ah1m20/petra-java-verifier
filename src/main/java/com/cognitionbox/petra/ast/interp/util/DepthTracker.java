package com.cognitionbox.petra.ast.interp.util;

public final class DepthTracker {
    private int lastDepth, depth = -1;

    boolean dc = false;
    public void markEntry(){
        lastDepth = depth;
        depth++;
        dc = true;
    }
    public void markExit(){
        lastDepth = depth;
        depth--;
        dc = true;
    }

    public int depth(){
        return depth;
    }

    public boolean depthChanged(){
//        boolean res = depth>lastDepth || depth<lastDepth;
//        lastDepth = depth;
//        return res;
        boolean res = dc;
        dc = false;
        return res;
    }
}
