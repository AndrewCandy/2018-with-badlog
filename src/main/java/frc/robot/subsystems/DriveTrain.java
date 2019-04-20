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

import frc.robot.Prefs2;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities;
import frc.robot.commands.*;


import badlog.lib.BadLog;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	boolean isGearTwo = false;
	
	
	public enum Gears {
		high, low;
	}

	private boolean targetDisableTurn;
	private boolean turnDisabled;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX talonSRX_Right1 = RobotMap.driveTrainTalonSRX_Right1;
	private final WPI_TalonSRX talonSRX_Left1 = RobotMap.driveTrainTalonSRX_Left1;
	private final WPI_VictorSPX victor_Right2 = RobotMap.driveTrainVictor_Right2;
	private final WPI_VictorSPX victor_Left2 = RobotMap.driveTrainVictor_Left2;
	private final DoubleSolenoid gearShifter = RobotMap.driveTrainGearShifter;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DriveTrain() {
		// sets default state of gear to low
		gearShifter.set(DoubleSolenoid.Value.kForward);

		BadLog.createTopic("Drivetrain/Talon Right Current", "A", () -> talonSRX_Right1.getOutputCurrent(),"hide",
				"join:Drivetrain/Output Currents");
		BadLog.createTopic("Drivetrain/Talon Left Current", "A", () -> talonSRX_Left1.getOutputCurrent(), "hide",
		 		"join:Drivetrain/Output Currents");
		BadLog.createTopic("Drivetrain/Talon Right Speed", "Feet/Second", () -> Math.abs(talonSRX_Right1.getSelectedSensorVelocity()/200.0*10/12),"hide", "join:Drivetrain/Speed");
		BadLog.createTopic("Drivetrain/Talon Left Speed", "Feet/Second", () -> Math.abs(talonSRX_Left1.getSelectedSensorVelocity()/200.0*10/12), "hide", "join:Drivetrain/Speed");
		BadLog.createTopic("Drivetrain/Talon Right Position", "Inches", () -> talonSRX_Right1.getSelectedSensorPosition()/200.0,"hide", "join:Drivetrain/Position");
		BadLog.createTopic("Drivetrain/Talon Left Position", "Inches", () -> talonSRX_Left1.getSelectedSensorPosition()/200.0, "hide", "join:Drivetrain/Position");
	}

	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
		setDefaultCommand(new Drive());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void periodic() {
		SmartDashboard.putBoolean("High Gear", isGearTwo);
		SmartDashboard.putNumber("Drive Right Encoder Values", Robot.driveTrain.getCurrentPositionRight());
		SmartDashboard.putNumber("Drive Left Encoder Values", Robot.driveTrain.getCurrentPositionLeft());
	}

	public int getCurrentPositionLeft() {
		return talonSRX_Left1.getSelectedSensorPosition(0);
	}

	public int getCurrentPositionRight() {
		return talonSRX_Right1.getSelectedSensorPosition(0);
	}

	public double getTargetPositionLeft() {
		return talonSRX_Left1.getClosedLoopTarget(0);
	}

	public double getTargetPositionRight() {
		return talonSRX_Right1.getClosedLoopTarget(0);
	}

	// Receives controller input from command and sets motor speed
	public void drive(double L, double R) {
		talonSRX_Right1.set(ControlMode.PercentOutput, R);
		talonSRX_Left1.set(ControlMode.PercentOutput, L);
	}
	private int inchesToEncoderPulses(double inches){
		return (int) (inches * Robot.prefs2.getDouble("Drive Encoder To Wheel Ratio"));
	}
	private void _driveDistanceRight(int pulses) {
		talonSRX_Right1.set(ControlMode.MotionMagic, pulses);
	}

	private void _driveDistanceLeft(int pulses) {
		talonSRX_Left1.set(ControlMode.MotionMagic, pulses);
	}
	public void driveDistance(double distanceLeft, double distanceRight){
		_driveDistanceLeft((int)(getCurrentPositionLeft()+distanceLeft * Robot.prefs2.getDouble("Encoder Ticks Per Inch Driven")));
		_driveDistanceRight((int)(getCurrentPositionRight()+distanceRight * Robot.prefs2.getDouble("Encoder Ticks Per Inch Driven")));
	}

	// disable turning stops receiving input from "X" axis
	public void disableTurning(boolean disabled) {
		turnDisabled = disabled;
	}

	// zeros distance value when requested to do so
	public void zeroDistance() {
		talonSRX_Left1.setSelectedSensorPosition(0, 0, Robot.prefs2.getInt("CAN Sensor Timeout MS"));
		talonSRX_Right1.setSelectedSensorPosition(0, 0, Robot.prefs2.getInt("CAN Sensor Timeout MS"));
	}

	// returns if turning is allowed or not
	public boolean isTurnDisabled() {
		return turnDisabled;
	}

	// returns current current draw
	public double[] getCurrentCurrentDraw() {
		double[] currents = new double[4];
		currents[0] = talonSRX_Left1.getOutputCurrent();
		currents[1] = talonSRX_Right1.getOutputCurrent();
		// currents[2] = victor_Left2.getOutputCurrent();
		// currents[3] = victor_Right2.getOutputCurrent();
		return currents;
	}

	// returns current speed
	public double[] getCurrentSpeed() {
		double[] speeds = new double[2];
		speeds[0] = talonSRX_Left1.getSelectedSensorVelocity(0);
		speeds[1] = talonSRX_Right1.getSelectedSensorVelocity(0);
		return speeds;
	}

	// returns current distance since last zeroed
	public double[] getCurrentDistance() {
		double[] distances = new double[2];
		distances[0] = Utilities.encoderDistanceToFeet(talonSRX_Left1.getSelectedSensorPosition(0));
		distances[1] = Utilities.encoderDistanceToFeet(talonSRX_Right1.getSelectedSensorPosition(0));
		return distances;
	}

	public void shift(Gears gear) {
		if (gear == Gears.high) {
			gearShifter.set(DoubleSolenoid.Value.kReverse);
			isGearTwo = true;
		} else if (gear == Gears.low) {
			gearShifter.set(DoubleSolenoid.Value.kForward);
			isGearTwo = false;
		}
	}

	public boolean isHighGear() {
		return isGearTwo;
	}

	public boolean isAtTargets() {
		double leftDelta = Math.abs(Math.abs(getTargetPositionLeft()) - Math.abs(getCurrentPositionLeft()));
		double rightDelta = Math.abs(Math.abs(getTargetPositionRight()) - Math.abs(getCurrentPositionRight()));
		int threshold = Robot.prefs2.getInt("Drive MM Target Threshold");

		if (leftDelta <= threshold && rightDelta <= threshold) {
			return true;
		} else {
			return false;
		}
	}

}
