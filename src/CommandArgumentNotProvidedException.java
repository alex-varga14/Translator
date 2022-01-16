package src;

public class CommandArgumentNotProvidedException extends RuntimeException {
	public CommandArgumentNotProvidedException() {
		super("ERROR: ARGUMENT NOT PROVIDED.");
	}
}
