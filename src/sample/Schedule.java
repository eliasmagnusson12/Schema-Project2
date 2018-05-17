package sample;

public class Schedule {

    private String socialSecurityNumber;
    private String date;
    private String time;
    private int color;

    public Schedule(String socialSecurityNumber, String date, String time, int color) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getColor() {
        return color;
    }
}
