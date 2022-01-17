package controller;

import model.User;

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

    private static User operator = null;

    public static User getOperator() {
        return operator;
    }

    public static void setOperator(User operator) {
        Access.operator = operator;
    }
}
