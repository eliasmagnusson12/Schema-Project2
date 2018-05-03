package sample;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Week {

    private Calendar calendar = new GregorianCalendar();


    public Week() {

    }

    public int getWeek() {
        Date date = new Date();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        return week;
    }

    public int getNextWeek(int weekChosen) {
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, weekChosen);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public int getYear() {
        Date date = new Date();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }
}
