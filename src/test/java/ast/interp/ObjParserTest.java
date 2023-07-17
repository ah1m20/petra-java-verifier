package ast.interp;

import ast.parsers.ObjParser;
import ast.terms.Obj;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class ObjParserTest {
    @Test public void testComplexSyntax() throws URISyntaxException, IOException {
        URL resource = ObjParserTest.class.getResource("/ComplexSyntax.java");
        ObjParser objParser = new ObjParser(Paths.get(resource.toURI()).toFile().getAbsolutePath(), true);
        Obj obj = objParser.parse("ComplexSyntax");
        System.out.println(new Gson().toJson(obj));
        System.out.println(PetraControlledEnglish.translate(obj));
        assertTrue(obj.isValid());
    }
}
