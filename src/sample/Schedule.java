package sample;

public class Schedule {

    private String socialSecurityNumber;
    private String date;
    private String time;

    public Schedule(String socialSecurityNumber, String date, String time) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.date = date;
        this.time = time;
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
}
