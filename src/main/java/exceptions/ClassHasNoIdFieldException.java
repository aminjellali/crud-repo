package exceptions;

public class ClassHasNoIdFieldException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClassHasNoIdFieldException(Class _class) {
		super("FATAL: failed to extract id. \ncause: "+ _class.getName() + " has no field with annotation @IsIdentifier");
	}
}
