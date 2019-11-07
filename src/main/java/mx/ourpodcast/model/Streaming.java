package mx.ourpodcast.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "streaming")
public class Streaming {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer idStreaming;

    @Column(name = "viewCount")
    private Integer viewCount;

    @Column(name = "beginDateTime")
    private LocalDateTime beginDateTime;

    @Column(name = "finishDateTime")
    private LocalDateTime finishDateTime;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPodcast")
    private Podcast podcast;

    public Streaming(){}

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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the podcast
     */
    public Podcast getPodcast() {
        return podcast;
    }

    /**
     * @param podcast the podcast to set
     */
    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
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
