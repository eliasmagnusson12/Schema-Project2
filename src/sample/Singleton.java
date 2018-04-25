package sample;

public class Singleton {
    private static Singleton ourInstance = new Singleton();

    User user;

    public static Singleton getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Singleton ourInstance) {
        Singleton.ourInstance = ourInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
