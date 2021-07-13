package com.example.proyectopoo;

import java.io.Serializable;
import java.util.HashMap;

public class Client extends User implements Serializable {

    private String id,email,fullname,type;

    public Client(String email,String password,String fullname,String type){
        this.email = email;
        this.fullname = fullname;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
