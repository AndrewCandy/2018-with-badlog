package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoDriveForwardArmUpCommandGroup extends CommandGroup {

    public AutoDriveForwardArmUpCommandGroup() {
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
    	addParallel(new TimedDrive(.5, 5));
		addParallel(new SetArmTargetToAbsolutePosition("Arm Pos 2 Value"));
		addParallel(new SetWristTargetToAbsolutePosition(Robot.prefs2.getInt("Wrist Down Position")));
    }
}
