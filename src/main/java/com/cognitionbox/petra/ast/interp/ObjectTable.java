package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.terms.Obj;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectTable {
    private Map<String, Obj> table = new LinkedHashMap<>();

    public void put(String objectId, Obj obj){
        table.put(objectId,obj);
    }

    public Obj lookup(String objectId){
        return table.get(objectId);
    }

    public Collection<Obj> values() {
        return table.values();
    }
}
