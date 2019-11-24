package mx.ourpodcast.model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "state")
    private boolean state;

    @Column(name = "token")
    private String token;

    @Column(name = "intengoslogin", columnDefinition = "int default 0")
    private Integer intentoLogin = 0;
    public boolean isState() {
        return this.state;
    }
        
    @Column(name = "tiempoIniToken")
    private LocalDateTime tiempoIniToken;



    public Usuario(){}

    /**
     * @return the intentoLogin
     */
    public Integer getIntentoLogin() {
        return intentoLogin;
    }
    /**
     * @return the tiempoIniToken
     */
    public LocalDateTime getTiempoIniToken() {
        return tiempoIniToken;
    }

    /**
     * @param intentoLogin the intentoLogin to set
     */
    public void setIntentoLogin(Integer intentoLogin) {
        this.intentoLogin = intentoLogin;
    }
    /**
     * @param tiempoIniToken the tiempoIniToken to set
     */
    public void setTiempoIniToken(LocalDateTime tiempoIniToken) {
        this.tiempoIniToken = tiempoIniToken;
    }



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
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }



}