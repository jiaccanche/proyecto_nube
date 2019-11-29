package mx.ourpodcast.request;

import java.time.LocalDateTime;

public class StreamingRequest {

    private String beginDateTime;

    private String finishDateTime;

    private Integer idPodcast;

    private String code;
    
    public StreamingRequest(){}



    public String getBeginDateTime() {
        return this.beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getFinishDateTime() {
        return this.finishDateTime;
    }

    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }
   

    /**
     * @return the idPodcast
     */
    public Integer getIdPodcast() {
        return idPodcast;
    }

    /**
     * @param idPodcast the idPodcast to set
     */
    public void setIdPodcast(Integer idPodcast) {
        this.idPodcast = idPodcast;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    

}