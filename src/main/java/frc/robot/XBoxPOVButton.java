/**
 * 
 */
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}.
 */
public class XBoxPOVButton extends Button {
	public enum PovDirection{
		North(0),Northeast(45), East(90), Southeast(135), South(180), Southwest(225), West(270), Northwest(315), Any(361), Center(-1);
		private int angle;
		PovDirection(int angle){
			this.angle = angle;
		}
		public int getAngle() {
			return angle;
		}
	}
	private final GenericHID m_joystick;
	private final PovDirection m_povDirection;

	/**
	 * Create a joystick button for triggering commands.
	 *
	 * @param joystick
	 *            The GenericHID object that has the button (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param buttonNumber
	 *            The button number (see {@link GenericHID#getRawButton(int) }
	 */
	public XBoxPOVButton(GenericHID joystick, PovDirection povDirection) {
		m_joystick = joystick;
		m_povDirection = povDirection;
	}

	/**
	 * Gets the value of the joystick button.
	 *
	 * @return The value of the joystick button
	 */
	public boolean get() {//if any POV button is pressed
		if(m_joystick.getPOV()>-1&&m_povDirection==PovDirection.Any){
			return true;
		}//makes it so whatever POV button is pressed, if that is equal to the target it sets it to true
		else if(m_joystick.getPOV()==m_povDirection.getAngle()){
			return true;
		}else{//if button pressed is not target returns false
			return false;
		}
	}
}