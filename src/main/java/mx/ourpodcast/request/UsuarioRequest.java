package mx.ourpodcast.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequest {
        
    private Integer idUsuario;

    @NotNull(message = "El nombre es nulo")
    @Size(min = 5, max = 50, message 
      = "El nombre debe tener entre 5 y 50 caracteres")
    @NotEmpty(message = "El nombre es vacío")
    private String name;

    @NotNull(message = "El email es nulo")
    @Size(min = 10, max = 30, message 
      = "El email debe tener entre 10 y 30 caracteres")
    @NotEmpty(message = "El email es vacío")
    private String email;

    @NotNull(message = "El password es nulo")
    @Size(min = 8, max = 12, message 
      = "El password debe tener entre12 y 50 caracteres")
    @NotEmpty(message = "El título es vacío")
    private String password;

    @NotNull(message = "El username es nulo")
    @Size(min = 5, max = 10, message 
      = "El username debe tener entre 5 y 10 caracteres")
    @NotEmpty(message = "El username es vacío")
    private String username;

    private String birthday;

    private boolean state;

    private String token;

    public UsuarioRequest(){}

    /**
     * @return the idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the state
     */
    public boolean getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }



}