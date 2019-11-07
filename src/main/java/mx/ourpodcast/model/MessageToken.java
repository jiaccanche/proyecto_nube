package mx.ourpodcast.model;

import java.util.Date;

public class MessageToken {
    private String token;
    private String time;


    public MessageToken() {
    }
    

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }


}