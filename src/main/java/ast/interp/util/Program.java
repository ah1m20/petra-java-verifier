package ast.interp.util;

import ast.parsers.ObjParser;
import ast.terms.Obj;
import ast.terms.Prog;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Program {

    public static Prog buildProgram(String rootPath, String entryPointMethodName, String rootObjectName, String... javaSrcFileNames) throws URISyntaxException, IOException {
        List<Obj> objs = new ArrayList<>();
        for (String fileName : javaSrcFileNames){
            URL url = Program.class.getResource(rootPath+fileName+".java");
            ObjParser parser = new ObjParser(Paths.get(url.toURI()).toFile().getAbsolutePath(),true);
            Obj o = parser.parse(fileName);
            objs.add(o);
        }
        return new Prog(entryPointMethodName,rootObjectName,objs);
    }
}
