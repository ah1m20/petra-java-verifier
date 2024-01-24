package com.cognitionbox.petra.examples.simplelightsystem;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base
public class Control {
        private boolean bool;
        public boolean on() { return bool==true; }
        @Initial public boolean off() { return bool==false; }

        public void turnOn() {
            if (on() ^ off()){
                bool=true;
                assert(on());
            }
        }

        public void turnOff() {
            if (on() ^ off()){
                bool=false;
                assert(off());
            }
        }
    }