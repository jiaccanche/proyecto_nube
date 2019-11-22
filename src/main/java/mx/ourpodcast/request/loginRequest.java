package mx.ourpodcast.request;

import javax.validation.constraints.NotNull;

public class loginRequest{
    @NotNull(message = "No puede ser nulo")
    private String username;
    @NotNull(message = "No puede ser nulo")
    private String password;

    public loginRequest(){}

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}