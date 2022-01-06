package controller;

import model.Usuario;

public abstract class Access {

    public final static String ADMINISTRATIVE_ACCESS = "Administrador";
    public final static String BASIC_ACCESS = "Usuário";

    private static boolean fullAccess = false;

    public static void checkFullAccess(String accessLevel) {
        if (accessLevel.equals(ADMINISTRATIVE_ACCESS)) {
            fullAccess = true;
        } else if (accessLevel.equals(BASIC_ACCESS)) {
            fullAccess = false;
        }
    }

    public static boolean isFullAccess() {
        return fullAccess;
    }

    private static Usuario operator = null;

    public static Usuario getOperator() {
        return operator;
    }

    public static void setOperator(Usuario operator) {
        Access.operator = operator;
    }
}
