package ca.humbermail.n01300070.automahome.utils;

public class NullHandler {
	public static <E> E setDefaultIfNull(E value, E defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
}
