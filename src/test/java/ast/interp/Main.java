package ast.interp;

import ast.parsers.ObjParser;
import ast.terms.Delta;
import ast.terms.Obj;
import ast.terms.Phi;
import ast.terms.Prog;
import ast.terms.statements.c.C;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static ast.interp.util.Program.buildProgram;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Prog prog = buildProgram("/flatlightingsystem/","toggle","Flat","Flat","Room","Light");
        Symbolic symbolic = new Symbolic(prog);
        for (Obj o : prog.getObjs()){
            if (o instanceof Obj){
                System.out.println(o.getA()+": "+symbolic.interpObj(o));
                System.out.println("\t\\Omega = "+symbolic.Omega(o));
                System.out.println("\t\\Theta = "+symbolic.Theta(o));
                for (Phi p : o.getOverlinePhi()){
                    if (!o.isPrimitive()){
                        System.out.println("\t"+p.getP()+" = "+symbolic.interpE(symbolic.lookupE(p.getP(),o),o));
                    } else {
                        System.out.println("\t"+p.getP());
                    }
                }
                for (Delta d : o.getOverlineDelta()){
                    System.out.println("\t"+d.getM()+":");
                    for (C c : d.getOverlineC()){
                        System.out.println("\t\tcase: "+symbolic.interpC(c,o));
                    }
                }
            }
        }
    }
}
