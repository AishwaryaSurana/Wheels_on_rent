package com.example.dailyexpenseapp;


public class User
{
  int user_id,user_type;
  String name,email,mobile,password,manager_email;

    public User(int user_id, int user_type, String name, String email, String mobile, String password, String manager_email) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.manager_email = manager_email;
    }

    public User() 
    {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getManager_email() {
        return manager_email;
    }

    public void setManager_email(String manager_email) {
        this.manager_email = manager_email;
    }
  
  
  
}
