package controller;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    protected static boolean validateInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static boolean validateDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static boolean validateQuantity(int value) {
        return (value >= 0);
    }

    protected static List<String> getMonths() {
        List<String> months = new ArrayList<String>();
        months.add("Janeiro");
        months.add("Fevereiro");
        months.add("Março");
        months.add("Abril");
        months.add("Maio");
        months.add("Junho");
        months.add("Julho");
        months.add("Agosto");
        months.add("Setembro");
        months.add("Outubro");
        months.add("Novembro");
        months.add("Dezembro");
        return months;
    }
}
