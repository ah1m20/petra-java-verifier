package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.External;

import java.util.concurrent.atomic.AtomicBoolean;

@External
public final class BoolFlag extends AtomicBoolean {
    public BoolFlag(boolean initial){
        this.set(initial);
    }
    public boolean isTrue(){return this.get();}
    public boolean isFalse(){return !this.get();}
    public void setTrue(){this.set(true);}
    public void setFalse(){this.set(false);}
}
