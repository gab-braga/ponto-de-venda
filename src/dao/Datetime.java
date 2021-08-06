package dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Datetime {

    protected static Date getTimestamp(java.sql.Date date, Time time) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");

        String datetimeString = String.format("%s %s", formatDate.format(date), formatHour.format(time));

        Date datetime = new java.util.Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            datetime = format.parse(datetimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datetime;
    }
}
