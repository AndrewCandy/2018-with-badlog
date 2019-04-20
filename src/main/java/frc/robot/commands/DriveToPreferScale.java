package frc.robot.commands;

import frc.robot.MatchData;
import frc.robot.Robot;
import frc.robot.MatchData.GameFeature;
import frc.robot.MatchData.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToPreferScale extends CommandGroup {

	public DriveToPreferScale() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		MatchData.Side ownedScaleSide = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		MatchData.Side startingSide = Robot.startingSide;
		
		if (ownedScaleSide != Side.UNKNOWN) {
			if (ownedScaleSide == Side.LEFT && startingSide == Side.LEFT) {
				addSequential(new AutoDriveToScale());
			} else if (ownedScaleSide == Side.RIGHT && startingSide == Side.RIGHT) {
				addSequential(new AutoDriveToScale());
			} else {
				addSequential(new AutoDriveToSwitch());
			}
		} else {
			System.out.println("No Field Layout Data Recieved");
		}
	}
}
