package com.cognitionbox.petra.examples.roomlightsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Power {
        private boolean bool;
        public boolean on() { return bool==true; }
        public boolean off() { return bool==false; }

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