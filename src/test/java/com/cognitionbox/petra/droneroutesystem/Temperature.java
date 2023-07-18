package com.cognitionbox.petra.droneroutesystem;
import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base public class Temperature {
	private final DroneConnection connection = DroneConnection.getDroneConnection();

	/*
	 * Only the predicates in an external objects mentioned in the expressions are assumed to be disjoint, thus providing implicit views.
	 *
	 * This information is used for the predicates disjointedness and covering checks in the base objects.
	 * Methods in base objects are computed from the pre/post-conditions and are not checked using compositions.
	 */
	@Initial
	public boolean low() { return connection.low(); }
	public boolean normal() { return connection.normal(); }
	public boolean high() { return connection.high(); }

}