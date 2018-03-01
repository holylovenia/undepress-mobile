package com.hulahoop.mentalhealth.undepress.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holy on 25/02/18.
 */

public class Patient extends User {
    private final String facebookKey = "facebook";
    private final String twitterKey = "twitter";
    private final String instagramKey = "instagram";
    private Map<String, String> socialMedia;

    public Patient(int id, String name, String email, String address, String phoneNumber) {
        super(id, name, email, address, phoneNumber);
        socialMedia = new HashMap<String, String>();
    }

    public Map<String, String> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(Map<String, String> socialMedia) {
        this.socialMedia = socialMedia;
    }
}
