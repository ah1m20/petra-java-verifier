package meanreversion;

import ast.terms.Base;

@Base
public class RevOrTrend {
	private volatile boolean reversion = false;
	public boolean reversion() { return reversion; }
	public boolean trend() { return !reversion; }

	public void setReversion() {
		if (reversion() ^ trend()){
			reversion = true;
			assert(reversion());
		}
	}

	public void setTrend() {
		if (reversion() ^ trend()){
			reversion = false;
			assert(trend());
		}
	}
}