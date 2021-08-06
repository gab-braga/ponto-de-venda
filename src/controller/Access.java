package controller;

import model.Usuario;

public abstract class Access {

    protected final static String accessAdmin = "Administrador";
    protected final static String accessUser = "Usuário";

    private static boolean fullAccess = false;

    private static Usuario user;

    public static void checkFullAccess(String accessLevel) {
        if (accessLevel.equals(accessAdmin)) {
            fullAccess = true;
        } else if (accessLevel.equals(accessUser)) {
            fullAccess = false;
        }
    }

    public static boolean isFullAccess() {
        return fullAccess;
    }

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        Access.user = user;
    }
}
