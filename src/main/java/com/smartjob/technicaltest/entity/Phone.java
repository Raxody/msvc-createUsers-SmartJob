package com.smartjob.technicaltest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    private String uuid;
    private Long number;
    private Long cityCode;
    private Long countryCode;
    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private User user;

    public Phone() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Long countryCode) {
        this.countryCode = countryCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
