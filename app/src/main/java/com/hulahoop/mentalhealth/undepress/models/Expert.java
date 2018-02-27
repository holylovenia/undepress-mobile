package com.hulahoop.mentalhealth.undepress.models;

/**
 * Created by holy on 25/02/18.
 */

public class Expert extends User {
    private String experience;
    private String description;

    public Expert(String name, String description, String address, String phoneNumber, String email, String experience) {
        super(name, email, address, phoneNumber);
        this.description = description;
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
