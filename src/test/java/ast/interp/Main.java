package ast.interp;

import ast.terms.Prog;
import static ast.interp.util.Program.buildProgram;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Prog prog = buildProgram("/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        symbolic.printOutput();
    }
}
