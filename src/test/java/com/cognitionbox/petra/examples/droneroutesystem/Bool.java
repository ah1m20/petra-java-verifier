package com.cognitionbox.petra.examples.droneroutesystem;

import com.cognitionbox.petra.ast.terms.External;

import java.util.concurrent.atomic.AtomicBoolean;

@External public final class Bool extends AtomicBoolean {
    public Bool(boolean initial){
        this.set(initial);
    }
    public boolean isTrue(){return this.get();}
    public boolean isFalse(){return !this.get();}
    public void setTrue(){this.set(true);}
    public void setFalse(){this.set(false);}
}
