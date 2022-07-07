package ir.kazix.mymessenger.Classes;

import androidx.annotation.Nullable;

public class User {

    private String email;

    @Nullable
    private String lastName;

    @Nullable
    private String firstName;

    private String password;

//    public User(String email, @Nullable String lastName, @Nullable String firstName, String password) {
//        this.email = email;
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.password = password;
//    }
//
//    public User(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//
//    public User(String email) {
//        this.email = email;
//    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }
}
