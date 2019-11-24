package mx.ourpodcast.request;

import java.time.LocalDateTime;

public class StreamingRequest {

    private String beginDateTime;

    private String finishDateTime;

    private String podcastCode;
    
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
   

    public String getPodcastCode() {
        return this.podcastCode;
    }

    public void setPodcastCode(String podcastCode) {
        this.podcastCode = podcastCode;
    }
    

}