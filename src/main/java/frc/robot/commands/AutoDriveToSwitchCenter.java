package frc.robot.commands;

import frc.robot.MatchData;
import frc.robot.Robot;
import frc.robot.MatchData.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoDriveToSwitchCenter extends CommandGroup {

    public AutoDriveToSwitchCenter() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	MatchData.Side ownedSwitchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		MatchData.Side startingSide = Robot.startingSide;
    	
		if (ownedSwitchSide != Side.UNKNOWN) {
			if (ownedSwitchSide == Side.LEFT){
				System.out.println("Switch From Center - Left Switch chosen");
				addSequential(new DriveDistance(12, 12));
		        addSequential(new TurnLeft45());
		        addParallel(new SetArmTargetToAbsolutePosition("Arm Pos 2 Value"));
		        addSequential(new DriveDistance(84,84));
		        addParallel(new SetWristTargetToAbsolutePosition(Robot.prefs2.getInt("Wrist Down Position")));
		        addSequential(new TurnRight45());
		        addSequential(new TimedDrive(.2, 3));
		        addSequential(new IntakeReleaseSlow());
		        addSequential(new WaitCommand(2));
		        addSequential(new IntakeStop());
			} else if (ownedSwitchSide == Side.RIGHT){
				System.out.println("Switch From Center - Right Switch Chosen");
				addSequential(new DriveDistance(12, 12));
		        addSequential(new TurnRight45());
		        addParallel(new SetArmTargetToAbsolutePosition("Arm Pos 2 Value"));
		        addSequential(new DriveDistance(76,76));
		        addParallel(new SetWristTargetToAbsolutePosition(Robot.prefs2.getInt("Wrist Down Position")));
		        addSequential(new TurnLeft45());
		        addSequential(new TimedDrive(.2, 3));
		        addSequential(new IntakeReleaseSlow());
		        addSequential(new WaitCommand(2));
		        addSequential(new IntakeStop());
			}else {
				addSequential(new AutoDriveToSwitch());
				System.out.println("We didn't set up in the center");
			}
		} else {
			System.out.println("Switch from Center - No Field Layout Data Recieved");
			addSequential(new WaitCommand(2));
			addSequential(new DriveDistance(12, 12));
	        addSequential(new TurnLeft45());
	        addSequential(new DriveDistance(84,84));
	        addSequential(new TurnRight45());
	        addSequential(new TimedDrive(.2,3));
		}
    }
}
