package p1;

/**
 * Created by Aishwarya on 24-Dec-15.
 */
public class Owner

{
    String name,surname,contact_no,email,password;
    //int id;

    
    public Owner()
    {}

    public Owner(String name, String surname, String contact_no, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.contact_no = contact_no;
        this.email = email;
        this.password = password;
    }

/*   public Owner( int id,String name, String surname, String contact_no, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.contact_no = contact_no;
        this.email = email;
        this.password = password;
        this.id = id;
    }

  */  @Override
    public String toString() {
        return "Owner{" + "name=" + name + ", surname=" + surname + ", contact_no=" + contact_no + ", email=" + email + ", password=" + password + '}';
    }

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
*/


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
