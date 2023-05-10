import ast.parsers.ObjParser;
import ast.terms.Obj;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static ast.interp.util.Collections.list;

public class ObjParserTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = ObjParserTest.class.getResource("/Room.java");
        ObjParser objParser = new ObjParser(Paths.get(resource.toURI()).toFile().getAbsolutePath(),true);
        Obj obj = objParser.parse("Room");

        System.out.println(new Gson().toJson(obj));
    }
}
