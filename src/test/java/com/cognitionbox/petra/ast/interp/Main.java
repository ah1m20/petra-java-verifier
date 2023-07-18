package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.terms.Prog;
import static com.cognitionbox.petra.ast.interp.util.Program.buildProgram;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Prog prog = buildProgram("/com/cognitionbox/petra/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        //symbolic.printOutput();
    }
}
