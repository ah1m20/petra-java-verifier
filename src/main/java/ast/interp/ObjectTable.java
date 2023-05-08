package ast.interp;

import ast.terms.Obj;

import java.util.HashMap;
import java.util.Map;

public class ObjectTable {
    private Map<String, Obj> table = new HashMap<>();

    public void put(String objectId, Obj obj){
        table.put(objectId,obj);
    }

    public Obj lookup(String objectId){
        return table.get(objectId);
    }
}
