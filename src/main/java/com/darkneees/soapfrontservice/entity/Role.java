package com.darkneees.soapfrontservice.entity;

public class Role {

    private Long id;
    private String nameRole;
    private String prettyNameRole;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public String getPrettyNameRole() {
        return prettyNameRole;
    }

    public void setPrettyNameRole(String prettyNameRole) {
        this.prettyNameRole = prettyNameRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nameRole='" + nameRole + '\'' +
                ", prettyNameRole='" + prettyNameRole + '\'' +
                '}';
    }
}