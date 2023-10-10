package com.example.techmasterpi.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;


public class Oauth2GoogleLogin {

    private final Map<String, Object> attributes;

    public Oauth2GoogleLogin() {
        this.attributes = Collections.emptyMap();
    }

    public Oauth2GoogleLogin(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return (String) attributes.get("sub");
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getNameAttributeKey() {
        return "name";
    }
}
