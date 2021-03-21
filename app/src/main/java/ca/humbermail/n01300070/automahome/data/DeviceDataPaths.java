package ca.humbermail.n01300070.automahome.data;

public class DeviceDataPaths {
	public static final String LIGHT_INTENSITY_RED = "red"; //Long: range 0-255
	public static final String LIGHT_INTENSITY_GREEN = "green"; //Long: range 0-255
	public static final String LIGHT_INTENSITY_BLUE = "blue"; //Long: range 0-255
	public static final String LIGHT_TIMESTAMP = "timestamp"; //Long: timestamp (milliseconds since UNIX epoch)
	
	public static final String MOVEMENT_SIDE_B = "room2"; //String
	public static final String MOVEMENT_LOG = "log"; //Map<String, Map<String, Object>>: Object could be unix timestamp or direction
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION = "direction"; //String: "left" or "right"
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION_TO_SIDE_A = "left";
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION_TO_SIDE_B = "right";
	public static final String MOVEMENT_LOG_ENTRY_TIMESTAMP = "timestamp"; //Long: timestamp (milliseconds since UNIX epoch)
	
	public static final String THERMOSTAT_TEMPERATURE = "temperature"; //Double: Temperature in celsius
	public static final String THERMOSTAT_HUMIDITY = "humidity"; //Double: Percentage
	public static final String THERMOSTAT_COMPRESSOR = "compressor"; //Boolean
	public static final String THERMOSTAT_REVERSE = "reverse"; //Boolean
	public static final String THERMOSTAT_FAN = "fan"; //Boolean
	public static final String THERMOSTAT_TIMESTAMP = "timestamp"; //Long: timestamp (milliseconds since UNIX epoch)
}
