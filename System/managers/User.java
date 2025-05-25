package System.managers;

public abstract class User {
    protected String username;
    protected String password;
    protected String role;

    public User() {

    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public String getRole() {
        return role;
    }

    public abstract void showMenu();  // Abstraction
}
