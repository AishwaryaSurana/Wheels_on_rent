package com.as.travela;

/**
 * Created by Aishwarya on 24-Dec-15.
 */
public class Owner

{
    String name,surname,contact_no,email,password;
    int owner_id;

    
    public Owner()
    {}

    public Owner(String name, String surname, String contact_no, String email, String password,int owner_id) {
        this.name = name;
        this.surname = surname;
        this.contact_no = contact_no;
        this.email = email;
        this.password = password;
        this.owner_id = owner_id;
    }

    public Owner(String name, String surname, String contact_no, String password, int owner_id) {
        this.name = name;
        this.surname = surname;
        this.contact_no = contact_no;
        this.password = password;
        this.owner_id = owner_id;
    }
    
    public int getId() {
        return owner_id;
    }

    @Override
	public String toString() {
		return "Owner [name=" + name + ", surname=" + surname + ", contact_no="
				+ contact_no + ", email=" + email + ", password=" + password
				 + ", owner_id=" + owner_id + "]";
	}

	public void setId(int owner_id) {
        this.owner_id = owner_id;
    }

        
    



    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
