package com.cognitionbox.petra.dronesystem;

import java.util.concurrent.atomic.AtomicBoolean;

public final class Bool extends AtomicBoolean {
    public Bool(boolean initial){
        this.set(initial);
    }
    public boolean isTrue(){return this.get();}
    public boolean isFalse(){return !this.get();}
    public void setTrue(){this.set(true);}
    public void setFalse(){this.set(false);}
}
