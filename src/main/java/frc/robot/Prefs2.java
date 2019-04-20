package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import java.util.Hashtable;
import java.util.Set;

public class Prefs2 {

	// Doubles Hastable
	private Hashtable<String, Double> doubles = new Hashtable<String, Double>(100);
	private Hashtable<String, Integer> ints = new Hashtable<String, Integer>(100);
	private static Prefs2 _prefs; // Instance of Self
	private Preferences prefs; // WPILIB Preferences Object

	private void initDefaults() {
		// Intake
		doubles.put("Intake Forward Speed", .5);
		doubles.put("Intake Reverse Speed Slow", -0.5);
		doubles.put("Intake Reverse Speed Fast", -0.7);

		// wrist
		doubles.put("Wrist Forward Speed", .25);
		doubles.put("Wrist Reverse Speed", .25);
		doubles.put("Wrist Scaling Value", 4.0);
		doubles.put("Wrist Gear Ratio", (14 / 64.0) * (30 / 60));
		doubles.put("Wrist Controller Deadband", .1);
		ints.put("Wrist Manual Increment", 500);
		ints.put("Wrist Soft Limit", -19500);
		ints.put("Wrist Max Acceleration", 500);
		ints.put("Wrist Max Velocity", 1640);
		ints.put("Wrist Up Position", -1000);
		ints.put("Wrist Down Position", -8000);
		ints.put("Wrist MM Target Threshold", 1000);

		// Winch
		doubles.put("Winch Speed", 1.0);

		// Arm
		doubles.put("Arm Forward Speed", 0.5);
		doubles.put("Arm Reverse Speed", -0.5);
		doubles.put("Arm Scaling Value", 2.0);
		doubles.put("Arm Controller Deadband", .1);
		doubles.put("Arm Encoder UPR", 4096.0);
		doubles.put("Arm Gear And Sprocket Ratio", (22 / 60.0) * (20 / 60) * (15 / 32));
		ints.put("Arm Pos 0 Value", 0);
		ints.put("Arm Pos 1 Value", 12200);
		ints.put("Arm Pos 2 Value", 14000);
		ints.put("Arm Pos 3 Value", 21000);
		ints.put("Arm Pos 4 Value", 25000);
		ints.put("Arm Pos 5 Value", 30000);
		ints.put("Arm Manual Increment", 1000);
		ints.put("Arm Top Limit", 31000);
		ints.put("Arm Bottom Limit", 0);
		ints.put("Arm Max Acceleration", 1000);
		ints.put("Arm Max Velocity", 2500);
		ints.put("Arm MM Target Threshold", 1000);

		// Talon
		ints.put("CAN Sensor Timeout MS", 10);

		// Drive
		doubles.put("Deadband Width", 0.05);
		doubles.put("Drive Slow Turn Speed", 0.6);
		doubles.put("Drive Slow Turn Threshold", 0.5);
		doubles.put("Drive Slow Speed Speed", 0.3);
		doubles.put("Drive Encoder To Wheel Ratio", 32.0*(50.0/34.0)*3*19.0);
		doubles.put("Encoder Ticks Per Inch Driven", 200.0);
		ints.put("Drive MM Target Threshold", 50);
		ints.put("Drive Max Acceleration", 2000);
		ints.put("Drive Max Velocity", 5000);
		
		//Autos
		doubles.put("Auto Distance To Switch", 149.0); //in inches
		doubles.put("Auto Distance To Scale", 26.0*12.0);//in inches
		doubles.put("Auto Distance To 90 Center Turn", 19.0); //in inches per side for a 90 degree turn
		doubles.put("Auto Distance To 90 Side Turn", 37.0); //in inches for one side for a 90 degree turn
		doubles.put("Auto Distance To 45 Center Turn", (19.0)/2); //in inches per side for a 90 degree turn
		doubles.put("Auto Distance To 45 Side Turn", (37.0)/2); //in inches for one side for a 90 degree turn
	}

	public static synchronized Prefs2 getInstance() {
		if (_prefs == null) {
			_prefs = new Prefs2();
		}
		return _prefs;
	}

	public Prefs2() {
		prefs = Preferences.getInstance();
		initDefaults();
	}

	public double getDouble(String preferenceKey) {
		if (!doubles.containsKey(preferenceKey) && !prefs.getInstance().containsKey(preferenceKey)) {
			// No Preference value set on SmartDashboard and no Default value
			// set in this class, return 0
			System.out.println("Prefs - Key '" + preferenceKey + "' does not exist, set in SmartDashboard or "
					+ getClass().getName());
			System.out.println("doubles:" + doubles.containsKey(preferenceKey) + " prefs:"
					+ prefs.getInstance().containsKey(preferenceKey));
			System.out.println("doubles keys:" + doubles);
			System.out.println("prefs keys:" + prefs.getKeys());

			return 0;
		}
		return prefs.getDouble(preferenceKey, doubles.get(preferenceKey));
	}

	public void printPrefs() {
		System.out.println("Preferences");
		// Loop thru each default double key and output value that will be
		// returned
		Set<String> doubleKeys = doubles.keySet();
		for (String key : doubleKeys) {
			if (!prefs.getInstance().containsKey(key)) {
				System.out.println(" - Double '" + key + "' = " + getDouble(key) + "(Pref Set: "
						+ prefs.getInstance().containsKey(key) + ")");
			}
		}

		// Loop thru each default int key and output value that will be returned
		Set<String> intKeys = ints.keySet();
		for (String key : intKeys) {
			if (!prefs.getInstance().containsKey(key)) {
				System.out.println(" - Int '" + key + "' = " + getInt(key) + "(Pref Set: "
						+ prefs.getInstance().containsKey(key) + ")");
			}
		}
		// Loop thru each default int key and output value that will be returned
		for (String key : intKeys) {
			if (prefs.getInstance().containsKey(key)) {
				System.out.println(" - Int '" + key + "' = " + getInt(key) + "(Pref Set: "
						+ prefs.getInstance().containsKey(key) + ")");
			}
		}

		// Loop thru each default double key and output value that will be
		// returned
		for (String key : doubleKeys) {
			if (prefs.getInstance().containsKey(key)) {
				System.out.println(" - Double '" + key + "' = " + getDouble(key) + "(Pref Set: "
						+ prefs.getInstance().containsKey(key) + ")");
			}
		}
	}

	public int getInt(String preferenceKey) {
		if (!ints.containsKey(preferenceKey) && !prefs.getInstance().containsKey(preferenceKey)) {
			// If preference value not set on SmartDashboard and default value
			// not set in this class, return 0
			System.out.println("Prefs - Key '" + preferenceKey + "' does not exist, set in SmartDashboard or "
					+ getClass().getName());
			return 0;
		}
		return prefs.getInt(preferenceKey, ints.get(preferenceKey));
	}
}