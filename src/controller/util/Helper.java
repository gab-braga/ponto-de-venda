package controller.util;

import controller.Access;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Produto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Helper {
    
    public static final List<String> LIST_MONTHS = Arrays.asList("", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    public static final List<String> LIST_YEARS = Arrays.asList("", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017","2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050");
    public static final List<String> LIST_UNITS = Arrays.asList("UN (Unidade)", "CX (Caixa)", "FD (Fardo)", "PCT (Pacote)", "KG (Quilograma)");

    public static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textField.getText().length() > maxLength) {
                    String s = textField.getText().substring(0, maxLength);
                    textField.setText(s);
                }
            }
        });
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    public static Double roundNumberTwoDecimalPlaces(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String number = ignoreComma(decimalFormat.format(value));
        return Double.parseDouble(number);
    }

    public static String ignoreComma(String number) {
        return number.replace(",", ".");
    }

    public static String formatNumber(Double number) {
        number = roundNumberTwoDecimalPlaces(number);
        return number.toString().replace(".", ",");
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String formatDateAndTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String formatDateByDayMonthYear(String day, String month, String year) {
        return String.format("%s/%s/%s", day, getMonthNumber(month), year);
    }

    public static String formatDateStringByMonthYear(String month, String year) {
        return String.format("%s/%s", getMonthNumber(month), year);
    }

    public static String formatDateByYear(String year) {
        return String.format("%s", year);
    }

    public static String extractMonthFromDate(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("MM");
            return format.format(date);
    }

    public static String extractYearFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String extractMonthAndYearFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        return format.format(date);
    }

    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateMonthAndYear(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateYear(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMonthNumber(String month) {
        String number = month;
        if (month.equals("Janeiro")) {
            number = "1";
        } else if (month.equals("Fevereiro")) {
            number = "2";
        } else if (month.equals("Março")) {
            number = "3";
        } else if (month.equals("Abril")) {
            number = "4";
        } else if (month.equals("Maio")) {
            number = "5";
        } else if (month.equals("Junho")) {
            number = "6";
        } else if (month.equals("Julho")) {
            number = "7";
        } else if (month.equals("Agosto")) {
            number = "8";
        } else if (month.equals("Setembro")) {
            number = "9";
        } else if (month.equals("Outubro")) {
            number = "10";
        } else if (month.equals("Novembro")) {
            number = "11";
        } else if (month.equals("Dezembro")) {
            number = "12";
        }
        return number;
    }

    public static void fillFieldMonth(ComboBox<String> fieldMonth) {
        List<String> months = Helper.LIST_MONTHS;
        ObservableList<String> items = FXCollections.observableArrayList(months);
        fieldMonth.setItems(items);
        fieldMonth.setValue("");
    }

    public static void fillFieldYear(ComboBox<String> fieldYear) {
        List<String> years = Helper.LIST_YEARS;
        ObservableList<String> items = FXCollections.observableArrayList(years);
        fieldYear.setItems(items);
        fieldYear.setValue("");
    }

    public static void fillFieldUnity(ComboBox<String> fieldUnity) {
        ObservableList<String> items = FXCollections.observableArrayList(LIST_UNITS);
        fieldUnity.setItems(items);
        fieldUnity.setValue("");
    }

    public static void fillFieldPermission(ComboBox<String> fieldPermission) {
        List<String> permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(Access.BASIC_ACCESS);
        permissions.add(Access.ADMINISTRATIVE_ACCESS);
        ObservableList<String> items = FXCollections.observableArrayList(permissions);
        fieldPermission.setItems(items);
        fieldPermission.setValue("");
    }
}
