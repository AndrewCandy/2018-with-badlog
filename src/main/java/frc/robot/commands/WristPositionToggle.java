package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristPositionToggle extends Command {

    public WristPositionToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.wrist.getWristTargetPosition()==Robot.prefs2.getInt("Wrist Up Position")){
    		Robot.wrist.setWristTargetPosition(Robot.prefs2.getInt("Wrist Down Position"));
    		System.out.println("Wrist is currently up. Make it down");
    	}else{
    		Robot.wrist.setWristTargetPosition(Robot.prefs2.getInt("Wrist Up Position"));
    		System.out.println("Wrist is currently down. Make it Up");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
