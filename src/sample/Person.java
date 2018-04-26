package sample;

public class Person {

    private String firstName;
    private String lastName;
    private String initials;
    private String role;
    private String email;
    private String phoneNumber;
    private String department;
    private String socialSecurityNumber;

    public Person(String firstName, String lastName, String initials, String role, String email, String phoneNumber, String department, String socialSecurityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = initials;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInitials() {
        return initials;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
}


