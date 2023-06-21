package ast.interp;

import ast.parsers.ObjParser;
import ast.terms.Obj;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class ObjParserTest {
    @Test public void testComplexSyntax() throws URISyntaxException, IOException {
        URL resource = ObjParserTest.class.getResource("/ComplexSyntax2.java");
        ObjParser objParser = new ObjParser(Paths.get(resource.toURI()).toFile().getAbsolutePath(), true);
        Optional<Obj> optional = objParser.parse("ComplexSyntax2");
        optional.ifPresent(obj->System.out.println(new Gson().toJson(obj)));
    }
}
