package edu.eci.cvds.ECIBienestarGym.exceptions;

public class GYMException extends Exception {
    public static final String USER_NOT_FOUND = "Usuario No Encontrado";
    public static  final String ROUTINE_NOT_FOUND = "Rutina No Encontrada";
    public static final String RESERVE_NOT_FOUND = "Reserva No Encontrada";
    public static final String REPORT_NOT_FOUND = "Reporte No Encontrado";
    public static final String PHYSICAL_PROGRESS_NOT_FOUND = "Progreso Fisico No Encontrado";
    public static final String GYM_SESION_NOT_FOUND = "Sesion No Encontrada";
    public static final String NO_MAIL_CHANGED = "No se ha cambiado el correo";
    public static final String USER_NOT_NULL = "Usuario No Puede Ser Nulo";
    public static final String INVALID_PASSWORD = "Contraseña invalida";

    public GYMException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return getMessage();
    }
}
