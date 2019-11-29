package mx.ourpodcast.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "streaming")
public class Streaming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idStreaming;

    @Column(name = "viewCount")
    private Integer viewCount;

    @Column(name = "beginDateTime")
    private LocalDateTime beginDateTime;

    @Column(name = "finishDateTime")
    private LocalDateTime finishDateTime;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_podcast", nullable = false)
    private Podcast podcast;
    
    @Column(name = "code")
    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Streaming(){}


    public Integer getIdStreaming() {
        return this.idStreaming;
    }

    public void setIdStreaming(Integer idStreaming) {
        this.idStreaming = idStreaming;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getBeginDateTime() {
        return this.beginDateTime;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return this.finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Podcast getPodcast() {
        return this.podcast;
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }
    
    

}
