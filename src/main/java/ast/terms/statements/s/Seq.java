package ast.terms.statements.s;

import java.util.ArrayList;
import java.util.List;

public final class Seq implements S, QR{
    private final List<Am> q = new ArrayList<>();

    public void add(Am am) {
        q.add(am);
    }

    public List<Am> getQ() {
        return q;
    }

    @Override
    public List<Am> getCmds() {
        return q;
    }
}
