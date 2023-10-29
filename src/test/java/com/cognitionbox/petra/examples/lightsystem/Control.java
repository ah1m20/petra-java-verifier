package com.cognitionbox.petra.examples.lightsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class Control {
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