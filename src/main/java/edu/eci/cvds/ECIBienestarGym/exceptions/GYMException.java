package edu.eci.cvds.ECIBienestarGym.exceptions;

public class GYMException extends RuntimeException {
    public static final String USERT_NOT_FOUND = "User Not Found";
    public static  final String ROUTINE_NOT_FOUND = "Routine Not Found";
    public static final String RESERVE_NOT_FOUND = "Reserve Not Found";
    public static final String REPORT_NOT_FOUND = "Report Not Found";
    public static final String PHYSICAL_PROGRESS_NOT_FOUND = "Physical progress Not Found";
    public static final String GYM_SESION_NOT_FOUND = "Gym Session Not Found";
    public static final String NO_GMAIL_CHANGED = "No GMail Changed";
    public static final String USERT_NOT_NULL = "User Not Null";
    public GYMException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return getMessage();
    }
}
