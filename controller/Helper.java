package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Helper {

    protected static final int salesOperation = 1;
    protected static final int outputOperation = 2;

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

    protected static String getStringValueDouble(double value) {
        return Double.toString(value).replace(".", ",");
    }

    protected static String getDateTimeStringFormatted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    protected static Date getDateFormatted(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(dateString);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static String getDateFormattedString(Date date) {
        String dateString = "-";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateString = format.format(date);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    protected static String getDateStringByDayMonthYear(String day, String month, String year) {
        return String.format("%s/%s/%s", day, getMonthNumber(month), year);
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
        if(monthString.equals("Janeiro")) {
            month = "1";
        }
        else if(monthString.equals("Fevereiro")) {
            month = "2";
        }
        else if(monthString.equals("Março")) {
            month = "3";
        }
        else if(monthString.equals("Abril")) {
            month = "4";
        }
        else if(monthString.equals("Maio")) {
            month = "5";
        }
        else if(monthString.equals("Junho")) {
            month = "6";
        }
        else if(monthString.equals("Julho")) {
            month = "7";
        }
        else if(monthString.equals("Agosto")) {
            month = "8";
        }
        else if(monthString.equals("Setembro")) {
            month = "9";
        }
        else if(monthString.equals("Outubro")) {
            month = "10";
        }
        else if(monthString.equals("Novembro")) {
            month = "11";
        }
        else if(monthString.equals("Dezembro")) {
            month = "12";
        }
        return month;
    }
}
