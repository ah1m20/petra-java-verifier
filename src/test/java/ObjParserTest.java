import ast.interp.Symbolic;
import ast.parsers.ObjParser;
import ast.terms.Delta;
import ast.terms.Obj;
import ast.terms.Prog;
import ast.terms.statements.c.C;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjParserTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = ObjParserTest.class.getResource("/Room2.java");
        ObjParser objParser = new ObjParser(Paths.get(resource.toURI()).toFile().getAbsolutePath(),true);
        Obj obj = objParser.parse("Room2");
        System.out.println(new Gson().toJson(obj));

        List<Obj> objs = new ArrayList<>();
        for (String name : Arrays.asList("Flat","Room","Light")){
            URL url = ObjParserTest.class.getResource("/"+name+".java");
            ObjParser parser = new ObjParser(Paths.get(url.toURI()).toFile().getAbsolutePath(),true);
            Obj o = parser.parse(name);
            objs.add(o);
        }
        Prog prog = new Prog("toggle","Flat",objs);
        Symbolic symbolic = new Symbolic(prog);
        for (Obj o : objs){
            if (o instanceof Obj){
                System.out.println(o.getA()+": "+symbolic.interpObj(o));
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
