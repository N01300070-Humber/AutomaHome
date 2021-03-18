package ca.humbermail.n01300070.automahome.data;

public class DeviceDataPaths {
	public static final String LIGHT_RED_INTENSITY = "red"; //Integer: range 0-255
	public static final String LIGHT_GREEN_INTENSITY = "green"; //Integer: range 0-255
	public static final String LIGHT_BLUE_INTENSITY = "blue"; //Integer: range 0-255
	
	public static final String MOVEMENT_ROOM2 = "room2"; //String
	public static final String MOVEMENT_LOG = "log"; //Map<String, Map<String, Object>>: Object could be unix timestamp or direction
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION = "direction"; //String: "left" or "right"
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION_TO_SIDE_A = "left";
	public static final String MOVEMENT_LOG_ENTRY_DIRECTION_TO_SIDE_B = "right";
	public static final String MOVEMENT_LOG_ENTRY_TIMESTAMP = "timestamp"; //Long: Unix timestamp (seconds since epoch)
	
	public static final String THERMOSTAT_TEMPERATURE = "temperature"; //Integer
	public static final String THERMOSTAT_HUMIDITY = "humidity"; //Integer
	public static final String THERMOSTAT_COMPRESSOR = "compressor"; //Boolean
	public static final String THERMOSTAT_REVERSE = "reverse"; //Boolean
	public static final String THERMOSTAT_FAN = "fan"; //Boolean
}
