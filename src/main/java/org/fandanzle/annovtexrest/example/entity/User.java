package org.fandanzle.annovtexrest.example.entity;

/**
 * Created by alexb on 01/12/2016.
 */
public class User {

    private String userName;

    private String email;

    private String age;

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAge() {
        return age;
    }

    public User setAge(String age) {
        this.age = age;
        return this;
    }
}
