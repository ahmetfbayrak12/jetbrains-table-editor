package main.constant;

public final class ErrorMessageConstants {
    public static final String INVALID_INPUT_ERROR_MESSAGE = "Please type only numbers not characters";
    public static final String FORMULA_ERROR_MESSAGE = "Formula should start with '=' character\nExample: =pow(1,2)";
    public static final String DIVISION_BY_ZERO_ERROR_MESSAGE = "Division by zero is not allowed.";
    public static final String INVALID_REFERENCE_ERROR_MESSAGE = "Reference invalid";
    public static final String LOOP_REFERENCE_ERROR_MESSAGE = "Mutual reference is not allowed\nExample: If A1 has a reference to C1 then C1 can not have a reference to A1";
    public static final String SELF_REFERENCE_ERROR_MESSAGE = "Self reference is not allowed.";
    public static final String INVALID_OPERATOR_ERROR_MESSAGE = "Operator is invalid.\nAccepted operators: \n- pow\n- avg\n- sqrt\nAnd basic operations: + - / * ^";
}
