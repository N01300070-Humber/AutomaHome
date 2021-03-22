package ca.humbermail.n01300070.automahome.utils;

public class Convert {
	
	public static double celsiusToFahrenheit(double celsius) {
		return celsius * 9 / 5 + 32;
	}
	
	public static double fahrenheitToCelsius(double fahrenheit) {
		return (fahrenheit - 32) * 5 / 9;
	}
}
