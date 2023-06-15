package ast.terms.statements.s;

import java.util.ArrayList;
import java.util.List;

public final class Par implements S, QR{
    private final List<Am> r = new ArrayList<>();

    public void add(Am am) {
        r.add(am);
    }

    public List<Am> getR() {
        return r;
    }

    @Override
    public List<Am> getCmds() {
        return r;
    }
}
