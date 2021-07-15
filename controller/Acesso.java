package controller;

public class Acesso {

    protected final static String accessAdmin = "Administrador";
    protected final static String accessUser = "Usuário";

    private static boolean fullAccess = false;

    private static String user;

    public static void checkFullAccess(String accessLevel) {
        if(accessLevel.equals(accessAdmin)) {
            fullAccess = true;
        }
        else if(accessLevel.equals(accessUser)) {
            fullAccess = false;
        }
    }

    public static boolean isFullAccess() {
        return fullAccess;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Acesso.user = user;
    }
}
