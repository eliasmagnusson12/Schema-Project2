package sample;

public class Singleton {
    private static Singleton ourInstance = new Singleton();

    private User user;
    boolean fullScreenSetting;



    public boolean isFullScreenSetting() {
        return fullScreenSetting;
    }

    public void setFullScreenSetting(boolean fullScreenSetting) {
        this.fullScreenSetting = fullScreenSetting;
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
