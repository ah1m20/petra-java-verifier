package com.cognitionbox.petra.meanreversion;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class RevOrTrend {
	private final Bool reversion = new Bool(true);
	public boolean reversion() { return reversion.isTrue(); }
	public boolean trend() { return reversion.isFalse(); }

	public void setReversion() {
		if (reversion() ^ trend()){
			reversion.isTrue();
			assert(reversion());
		}
	}

	public void setTrend() {
		if (reversion() ^ trend()){
			reversion.isFalse();
			assert(trend());
		}
	}
}