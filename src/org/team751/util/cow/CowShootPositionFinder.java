package org.team751.util.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow2.Position;

/**
 * Finds the best position to move the cow to to shoot a disk.
 * @author Sam Crow
 */
public class CowShootPositionFinder implements CowPositionFinder {

	public Position getClosestPosition(final CowStomachStatus status,
									   final Position currentPosition) throws NoCowPositionException {

		//If the current position is suitable, return it
		if (isCurrentPositionGood(status, currentPosition)) {
			return currentPosition;
		}

		//Throw an exception if no slot has a disk in it
		if (!status.isAnyFull()) {
			throw new NoCowPositionException(
					NoCowPositionException.Cause.kStomachsEmpty);
		}

		//Find the best position to move to

		//If currently in a loading position, the nearest position is the shooting
		//position that is rotated the least far forward.
		//kShoot3 is the best in this situation, followed by the lower numbers.
		if (currentPosition == Position.kLoad0 || currentPosition == Position.kLoad1
				|| currentPosition == Position.kLoad2 || currentPosition == Position.kLoad3) {
			//Best case: kShoot3
			if (status.stomach3) {

				if (CommandBase.cow.protection.canMoveTo(Position.kShoot3)) {
					return Position.kShoot3;
				}
			}
			if (status.stomach2) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot2)) {
					return Position.kShoot2;
				}
			}
			if (status.stomach1) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot1)) {
					return Position.kShoot1;
				}
			}
			if (status.stomach0) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot0)) {
					return Position.kShoot0;
				}
			}
		}

		//If currently in shoot3 position, closest shoot positions are 2, then 1, then 0
		if (currentPosition == Position.kShoot3) {
			if (status.stomach2) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot2)) {
					return Position.kShoot2;
				}
			}
			if (status.stomach1) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot1)) {
					return Position.kShoot1;
				}
			}
			if (status.stomach0) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot0)) {
					return Position.kShoot0;
				}
			}
		}

		//If currently in shoot2 position, closest shoot positions are {1 and 3}, then 0
		if (currentPosition == Position.kShoot2) {
			//Arbitrarily prioritize slot 1 over 3, because it is more central
			if (status.stomach1) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot1)) {
					return Position.kShoot1;
				}
			}
			if (status.stomach3) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot3)) {
					return Position.kShoot3;
				}
			}
			if (status.stomach0) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot0)) {
					return Position.kShoot0;
				}
			}
		}

		//If currently in shoot1 position, closest shoot positions are {0 and 2}, then 3
		if (currentPosition == Position.kShoot1) {
			//Prefer position 2, because it is more central
			if (status.stomach2) {
				if (CommandBase.cow.protection.canMoveTo(Position.kShoot2)) {
					return Position.kShoot2;
				}
			}
			if (status.stomach0) {
				if (CommandBase.cow.protection.canMoveTo(
						Position.kShoot0)) {
					return Position.kShoot0;
				}
			}
			if (status.stomach3) {
				if (CommandBase.cow.protection.canMoveTo(
						Position.kShoot3)) {
					return Position.kShoot3;
				}
			}

			//If currently in shoot0 position, preference is 1, then 2, and so on
			if (currentPosition == Position.kShoot0) {
				if (status.stomach1) {
					if (CommandBase.cow.protection.canMoveTo(
							Position.kShoot1)) {
						return Position.kShoot1;
					}
				}
			}
			if (status.stomach2) {
				if (CommandBase.cow.protection.canMoveTo(
						Position.kShoot2)) {
					return Position.kShoot2;
				}
			}
			if (status.stomach3) {
				if (CommandBase.cow.protection.canMoveTo(
						Position.kShoot3)) {
					return Position.kShoot3;
				}
			}
		}

		//Throw a new exception indicating an algorithm error
		throw new NoCowPositionException(
				NoCowPositionException.Cause.kAlgorithmError);
	}

	/**
	 * Determine if the current cow target position fulfills the
	 * requirements
	 * of this position finder (if it is full)
	 * @param status          The status
	 * @param currentPosition The current position to check
	 * @return true if the position is suitable, otherwise false
	 */
	private boolean isCurrentPositionGood(final CowStomachStatus status,
										  final Position currentPosition) {
		
		if (currentPosition == Position.kLoad0 && status.stomach0) {
			return true;
		}
		if (currentPosition == Position.kLoad1 && status.stomach1) {
			return true;
		}
		if (currentPosition == Position.kLoad2 && status.stomach2) {
			return true;
		}
		if (currentPosition == Position.kLoad3 && status.stomach3) {
			return true;
		}

		return false;
	}
}
