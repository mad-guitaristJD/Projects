

public abstract class User{
    protected String Email;
    protected String password;

    public User(){

    }

    public User(String email, String pass){
        this.Email = email;
        this.password = pass;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public abstract void display();

}