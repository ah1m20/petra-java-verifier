package com.cognitionbox.petra.examples.turingmachine;

import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;

public class Main {
    public static void main(String[] args) {
        startReactive(1000,new TuringMachine());
    }
}
