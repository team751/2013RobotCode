package org.team751.util.cow;

/**
 * Enumerates different positions to which the cow can be set.
 *
 * There are 4 disk positions in the cow: 0, 1, 2, and 3.
 *
 * With the Cow in its most upright position, position 0 is furthest
 * backwards.
 * Positions 1-3 are forwards of it.
 *
 * When the cow is backward in feeding mode, position 0 is at the bottom.
 * When the cow is forwards in shooting mode, position 0 is at the top.
 * (this also represents its furthest forward limit)
 *
 */
public class CowPosition {
	/**
	 * The value, in encoder counts rearward from position kShoot0,
	 * that the cow should be set to
	 */
	private double encoderValue = 0;

	/**
	 * Position for loading into stomach 0. (stomach 0 is at the bottom).
	 */
	public static final CowPosition kLoad0 = new CowPosition(30);

	/**
	 * Position for loading into stomach 1.
	 */
	public static final CowPosition kLoad1 = new CowPosition(50);

	/**
	 * Position for loading into stomach 2.
	 */
	public static final CowPosition kLoad2 = new CowPosition(70);

	/**
	 * Position for loading into stomach 3. (stomach 3 is at the top)
	 */
	public static final CowPosition kLoad3 = new CowPosition(90);

	/**
	 * Position for shooting from stomach 0. (stomach 0 is at the top)
	 */
	public static final CowPosition kShoot0 = new CowPosition(-60);

	/**
	 * Position for shooting from stomach 1.
	 */
	public static final CowPosition kShoot1 = new CowPosition(-40);

	/**
	 * Position for shooting from stomach 2.
	 */
	public static final CowPosition kShoot2 = new CowPosition(-20);

	/**
	 * Position for shooting from stomach 3. (stomach 3 is at the bottom).
	 */
	//kShoot3 is at the zero point
	public static final CowPosition kShoot3 = new CowPosition(0);

	/**
	 * Constructor
	 * @param encoderValue The encoder value for this position
	 */
	private CowPosition(double encoderValue) {
		this.encoderValue = encoderValue;
	}

	/**
	 * Get the encoder value.
	 * @return The target encoder value for this position.
	 */
	public double getEncoderValue() {
		return encoderValue;
	}

	/**
	 * Get the position that is one position back from this one.
	 * @return The next back position. If no position is further back
	 * than this one, returns null.
	 */
	public CowPosition nextBack() {
		if (this == CowPosition.kShoot3) {
			return CowPosition.kLoad0;
		}
		if (this == CowPosition.kShoot2) {
			return CowPosition.kShoot3;
		}
		if (this == CowPosition.kShoot1) {
			return CowPosition.kShoot2;
		}
		if (this == CowPosition.kShoot0) {
			return CowPosition.kShoot1;
		}
		if (this == CowPosition.kLoad0) {
			return CowPosition.kLoad1;
		}
		if (this == CowPosition.kLoad1) {
			return CowPosition.kLoad2;
		}
		if (this == CowPosition.kLoad2) {
			return CowPosition.kLoad3;
		}
		if (this == CowPosition.kLoad3) {
			return null;
		}
		return null;
	}

	/**
	 * Get the position that is one forward of this one.
	 * @return The next forward position. If not position is further forward
	 * than this one, returns null.
	 */
	public CowPosition nextForward() {
		if (this == CowPosition.kLoad3) {
			return CowPosition.kLoad2;
		}
		if (this == CowPosition.kLoad2) {
			return CowPosition.kLoad1;
		}
		if (this == CowPosition.kLoad1) {
			return CowPosition.kLoad0;
		}
		if (this == CowPosition.kLoad0) {
			return CowPosition.kShoot3;
		}
		if (this == CowPosition.kShoot3) {
			return CowPosition.kShoot2;
		}
		if (this == CowPosition.kShoot2) {
			return CowPosition.kShoot1;
		}
		if (this == CowPosition.kShoot1) {
			return CowPosition.kShoot0;
		}
		if (this == CowPosition.kShoot0) {
			return null;
		}
		return null;
	}
	
}
