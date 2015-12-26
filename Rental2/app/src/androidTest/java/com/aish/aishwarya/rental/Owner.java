package com.aish.aishwarya.rental;

/**
 * Created by Aishwarya on 24-Dec-15.
 */
public class Owner

{
    String name,surname,contact_no,email,city;



    public Owner( String name, String surname, String contact_no, String email,String city)
    {
        this.city = city;
        this.contact_no = contact_no;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
