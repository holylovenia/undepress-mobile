package com.hulahoop.mentalhealth.undepress.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holy on 25/02/18.
 */

public class Patient extends User {
    private Map socialMedia;
    private final String facebookKey = "facebook";
    private final String twitterKey = "twitter";
    private final String instagramKey = "instagram";

    public Patient(String name, String email, String address, String phoneNumber, String facebook, String twitter, String instagram) {
        super(name, email, address, phoneNumber);
        socialMedia = new HashMap();
        socialMedia.put(facebookKey, facebook);
        socialMedia.put(twitterKey, twitter);
    }
}
