package flatlightingsystem;

import java.util.concurrent.atomic.AtomicBoolean;

public final class Bool extends AtomicBoolean {
    public boolean isTrue(){return this.get();}
    public boolean isFalse(){return !this.get();}
    public void setTrue(){this.set(true);}
    public void setFalse(){this.set(false);}
}
