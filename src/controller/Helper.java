package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Produto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Helper {

    protected static boolean validateClient(Cliente cliente) {
        return !(cliente == null);
    }

    protected static boolean validateProduct(Produto produto) {
        return !(produto == null);
    }

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

    protected static boolean validateQuantity(double value) {
        return (value >= 0.0);
    }

    protected static boolean validateDate(String date) {
        boolean flag = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(date);
            flag = true;
        } catch (ParseException ex) {
            flag = false;
        }
        return flag;
    }

    protected static void addTextLimiter(final TextField textField, final int maxLength) {
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

    protected static String getStringValueDouble(double value) {
        return Double.toString(value).replace(".", ",");
    }

    protected static Double round(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String valueString = decimalFormat.format(value).replace(",", ".");
        return Double.parseDouble(valueString);
    }

    protected static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    protected static String getStringDateFormatted(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateString = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    protected static String getDateTimeStringFormatted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getDateStringFormatted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String getStringMonth(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM");
            dateString = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String getStringYear(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            dateString = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    protected static String getDateStringByDayMonthYear(String day, String month, String year) {
        return String.format("%s/%s/%s", day, getMonthNumber(month), year);
    }

    protected static String getDateStringByMonthYear(String month, String year) {
        return String.format("%s/%s", getMonthNumber(month), year);
    }

    protected static String getDateStringByMonthYear(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
            dateString = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    protected static String getDateStringByYear(String year) {
        return String.format("%s", year);
    }

    protected static String getDateStringByYear(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            dateString = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    protected static Date getDateFormattedDayMonthYear(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static Date getDateFormattedMonthYear(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static Date getDateFormattedYear(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static List<String> getListMonths() {
        List<String> months = new ArrayList<String>();
        months.add("");
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

    protected static String getMonthNumber(String monthString) {
        String month = monthString;
        if (monthString.equals("Janeiro")) {
            month = "1";
        } else if (monthString.equals("Fevereiro")) {
            month = "2";
        } else if (monthString.equals("Março")) {
            month = "3";
        } else if (monthString.equals("Abril")) {
            month = "4";
        } else if (monthString.equals("Maio")) {
            month = "5";
        } else if (monthString.equals("Junho")) {
            month = "6";
        } else if (monthString.equals("Julho")) {
            month = "7";
        } else if (monthString.equals("Agosto")) {
            month = "8";
        } else if (monthString.equals("Setembro")) {
            month = "9";
        } else if (monthString.equals("Outubro")) {
            month = "10";
        } else if (monthString.equals("Novembro")) {
            month = "11";
        } else if (monthString.equals("Dezembro")) {
            month = "12";
        }
        return month;
    }

    public static List<String> getListYears() {
        List<String> years = new ArrayList<String>();
        years.add("");
        years.add("1999");
        years.add("2000");
        years.add("2001");
        years.add("2002");
        years.add("2003");
        years.add("2004");
        years.add("2005");
        years.add("2006");
        years.add("2007");
        years.add("2008");
        years.add("2009");
        years.add("2010");
        years.add("2011");
        years.add("2012");
        years.add("2013");
        years.add("2014");
        years.add("2015");
        years.add("2016");
        years.add("2017");
        years.add("2018");
        years.add("2019");
        years.add("2020");
        years.add("2021");
        years.add("2022");
        years.add("2023");
        years.add("2024");
        years.add("2025");
        years.add("2026");
        years.add("2027");
        years.add("2028");
        years.add("2029");
        years.add("2030");
        years.add("2031");
        years.add("2032");
        years.add("2033");
        years.add("2034");
        years.add("2035");
        years.add("2036");
        years.add("2037");
        years.add("2038");
        years.add("2039");
        years.add("2040");
        years.add("2041");
        years.add("2042");
        years.add("2043");
        years.add("2044");
        years.add("2045");
        years.add("2046");
        years.add("2047");
        years.add("2048");
        years.add("2049");
        years.add("2050");
        return years;
    }
}
