// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Wrist extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX wristMotor = RobotMap.wristWristMotor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    int wristTargetPos = Robot.prefs2.getInt("Wrist Up Position");
	double motorPeakCurrent = 0;
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
		// Put code here to be run every loop
		
    	SmartDashboard.putNumber("Wrist Position", wristMotor.getSelectedSensorPosition(0));
    	SmartDashboard.putString("Wrist Control Mode", wristMotor.getControlMode().toString());
		   
		// Output MotionMagic target if it's active
       	if(wristMotor.getControlMode() == ControlMode.MotionMagic)
    	{
    		SmartDashboard.putNumber("Wrist Target Position", wristMotor.getClosedLoopTarget(0));
    	}
 		SmartDashboard.putNumber("Wrist Voltage", wristMotor.getMotorOutputVoltage());
		SmartDashboard.putNumber("Wrist Velocity", wristMotor.getSelectedSensorVelocity(0));
		
		// Track current current and update peak
		double wristCurrent = wristMotor.getOutputCurrent();
		if (wristCurrent > motorPeakCurrent){motorPeakCurrent = wristCurrent;}
    }

    public boolean isWristSwitchPressed() {
		return wristMotor.getSensorCollection().isFwdLimitSwitchClosed();
	}
    public void setWristTargetPosition(int position) {
    	System.out.println("Setting Wrist Speed");
		//wristMotor.set(ControlMode.MotionMagic, position);
		wristTargetPos = position;	
	}
    public double getWristTargetPosition(){
    	return wristMotor.getClosedLoopTarget(0);
    }
    public int getCurrentRawWristPosition() {
		return wristMotor.getSelectedSensorPosition(0);
	}
	public boolean isAtTargets() {
		double Delta = Math.abs(Math.abs(getWristTargetPosition()) - Math.abs(getCurrentRawWristPosition()));
		int threshold = Robot.prefs2.getInt("Wrist MM Target Threshold");

		if (Delta <= threshold) {
			return true;
		} else {
			return false;
		}
	}
}
