package com.darkneees.soapfrontservice.entity;

public class Social {

    private Long id;
    private String nameSocial;
    private String identifierSocial;

    public Social() {
    }

    public Social(String nameSocial, String identifierSocial) {
        this.nameSocial = nameSocial;
        this.identifierSocial = identifierSocial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSocial() {
        return nameSocial;
    }

    public void setNameSocial(String nameSocial) {
        this.nameSocial = nameSocial;
    }

    public String getIdentifierSocial() {
        return identifierSocial;
    }

    public void setIdentifierSocial(String identifierSocial) {
        this.identifierSocial = identifierSocial;
    }

    @Override
    public String toString() {
        return "Social{" +
                "id=" + id +
                ", nameSocial='" + nameSocial + '\'' +
                ", identifierSocial='" + identifierSocial + '\'' +
                '}';
    }
}
