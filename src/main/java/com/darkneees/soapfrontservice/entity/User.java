package com.darkneees.soapfrontservice.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private String username;
    private String firstName;
    private String secondName;
    private int age;
    private String password;
    private Set<Role> roleSet = new HashSet<>();
    private Set<Social> socialSet = new HashSet<>();

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<Social> getSocialSet() {
        return socialSet;
    }

    public void setSocialSet(Set<Social> socialSet) {
        this.socialSet = socialSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roleSet=" + roleSet +
                ", socialSet=" + socialSet +
                '}';
    }
}