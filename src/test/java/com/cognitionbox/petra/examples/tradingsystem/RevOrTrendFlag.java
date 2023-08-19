package com.cognitionbox.petra.examples.tradingsystem;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class RevOrTrendFlag {
	private final BoolFlag reversion = new BoolFlag(false);
	public boolean reversion() { return reversion.isTrue(); }
	public boolean trend() { return reversion.isFalse(); }

	public void setReversion() {
		if (reversion() ^ trend()){
			reversion.setTrue();
			assert(reversion());
		}
	}

	public void setTrend() {
		if (reversion() ^ trend()){
			reversion.setFalse();
			assert(trend());
		}
	}
}