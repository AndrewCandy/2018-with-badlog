package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.SerialPort;
//import org.json.JSONObject;

public class Vision extends Subsystem {
    private static SerialPort cam = new SerialPort(115200, SerialPort.Port.kUSB1);
    // private static SerialPort cam2 = new SerialPort(115200,
    // SerialPort.Port.kUSB);
    private Double lastKnownAngle = -99.99; // -99.99 equals no target
    private Double lastKnownDistance = -99.99;
    private String receivedString;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        // table = NetworkTable.getTable("Team250");
    }

    public void periodic() {
        try {
            if (cam.getBytesReceived() > 0) {
                receivedString = cam.readString();
                // System.out.println("Billy The Pink Panda ->"+ billyThePinkPanda);
                String a = receivedString.substring(6, receivedString.indexOf(","));
                String d = receivedString.substring(receivedString.indexOf(",") + 10);
                System.out.println(a + " " + d); // prints "Alice 20"
                // billyThePinkPanda = cam.readString();
                lastKnownAngle = Double.valueOf(a);
                // System.out.print("Andrew is bad ->" + Double.parseDouble(billyThePinkPanda));

                // System.out.println("New Distance -> " + billyThePinkPanda);
                lastKnownDistance = Double.valueOf(d);
                // System.out.println("Last ->" + lastKnownAngle);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public double getTargetAngle() // Returns angle of error from target
    {
        // System.out.println("Last Known ->" + lastKnownAngle);
        return lastKnownAngle;
    }

    public double getDistance() {
        // return lastKnownDistance();
        return lastKnownDistance;
    }

    public boolean isTargetValid() {
        if (this.getTargetAngle() > -99 && this.getDistance() > -99) {
            return true;
        }
        return false;
    }
}