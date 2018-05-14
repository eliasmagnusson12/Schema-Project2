package sample;

import java.util.ArrayList;

public class Singleton {
    private static Singleton ourInstance = new Singleton();

    private User user;
    private boolean fullScreenSetting;
    private ArrayList listOfEmployees;
    private ArrayList listOfUnderDepartments;



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

    public void setListOfEmployees(ArrayList listOfEmployees){
        this.listOfEmployees = listOfEmployees;
    }

    public ArrayList getListOfEmployees(){
        return listOfEmployees;
    }

    public void setListOfUnderDepartments(ArrayList listOfUnderDepartments){
        this.listOfUnderDepartments = listOfUnderDepartments;
    }

    public ArrayList getListOfUnderDepartments(){
        return listOfUnderDepartments;
    }

    private Singleton() {
    }
}
