package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance extends Command {
	private double m_leftDistance;
	private double m_rightDistance;

	public DriveDistance(double leftDistance, double rightDistance) {

		m_leftDistance = leftDistance;
		m_rightDistance = rightDistance;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.driveDistance(m_leftDistance, m_rightDistance);
		System.out.println("Starting Drive Distance - left: " + m_leftDistance + " Right: " + m_rightDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.driveTrain.isAtTargets();
		//return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.drive(0, 0);
		System.out.println("Ending Drive Distance - left: " + m_leftDistance + " Right: " + m_rightDistance);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.driveTrain.drive(0, 0);
	}
}
