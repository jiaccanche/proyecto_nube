package mx.ourpodcast.request;

import java.time.LocalDateTime;

public class StreamingRequest {
    private Integer idStreaming;

    private Integer viewCount;

    private LocalDateTime beginDateTime;

    private LocalDateTime finishDateTime;

    private Integer idUsuario;

    private Integer idPodcast;

    public StreamingRequest(){}

    /**
     * @return the idStreaming
     */
    public Integer getIdStreaming() {
        return idStreaming;
    }

    /**
     * @param idStreaming the idStreaming to set
     */
    public void setIdStreaming(Integer idStreaming) {
        this.idStreaming = idStreaming;
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
     * @return the beginDateTime
     */
    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    /**
     * @param beginDateTime the beginDateTime to set
     */
    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    /**
     * @return the finishDateTime
     */
    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    /**
     * @param finishDateTime the finishDateTime to set
     */
    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    /**
     * @return the viewCount
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount the viewCount to set
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}