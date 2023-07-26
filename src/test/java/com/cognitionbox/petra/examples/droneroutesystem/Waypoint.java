package com.cognitionbox.petra.examples.droneroutesystem;

import com.cognitionbox.petra.ast.terms.External;

@External public class Waypoint {
	private final int x;
	private final int y;
	private final int z;

	public Waypoint(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}