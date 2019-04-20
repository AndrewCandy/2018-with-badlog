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
public class XBoxTriggerButton extends Button {
	private final GenericHID m_joystick;
	private final int m_axisNumber;
	private final double m_threshold;

	/**
	 * Create a joystick button for triggering commands.
	 *
	 * @param joystick
	 *            The GenericHID object that has the button (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param buttonNumber
	 *            The button number (see {@link GenericHID#getRawButton(int) }
	 */
	public XBoxTriggerButton(GenericHID joystick, int axisNumber, double threshold) {
		m_joystick = joystick;
		m_threshold = threshold;

		if (axisNumber >= 0 && axisNumber <= 5) {
			m_axisNumber = axisNumber;
		} else {
			System.out.println("error, Axis out of threshold");
			m_axisNumber = -1;
		}
	}

	/**
	 * Gets the value of the joystick button.
	 *
	 * @return The value of the joystick button
	 */
	public boolean get() {
		if (m_axisNumber >= 0) {
			if (m_joystick.getRawAxis(m_axisNumber) >= m_threshold) {
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
}