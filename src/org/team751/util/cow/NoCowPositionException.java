package org.team751.util.cow;

/**
 * An exception that is thrown when there is no valid position to move the
 * cow to
 * @author Sam Crow
 */
public class NoCowPositionException extends Exception {
	
	/**
	 * Enumerates conditions that can cause this exception
	 */
	public static class Cause {
		
		/**
		 * All the stomachs are full, and this is a problem
		 */
		public static final Cause kStomachsFull = new Cause();
		/**
		 * All the stomachs are empty, and this is a problem
		 */
		public static final Cause kStomachsEmpty = new Cause();
		/**
		 * The cow position finder algorithm encountered an internal problem
		 */
		public static final Cause kAlgorithmError = new Cause();
		
		private Cause() {}
	}
	
	/**
	 * The cause for this exception
	 */
	private Cause cause;

	/**
	 * Constructor
	 * @param cause The cause for this exception
	 */
	public NoCowPositionException(Cause cause) {
		this.cause = cause;
	}

	/**
	 * Get the cause for this exception
	 * @return the cause
	 */
	public Cause getPositionCause() {
		return cause;
	}
	
	
	
}
