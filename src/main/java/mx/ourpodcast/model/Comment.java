package mx.ourpodcast.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private Integer idComment;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idStreaming", nullable = false)
    private Streaming streaming;

    @Column(name = "creationDate")
    private LocalDateTime  creationDate;

    @Column(name = "content")
    private String content;

    public Comment(){}

    /**
     * @return the idComment
     */
    public Integer getIdComment() {
        return idComment;
    }

    /**
     * @param idComment the idComment to set
     */
    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
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
     * @return the streaming
     */
    public Streaming getStreaming() {
        return streaming;
    }

    /**
     * @param streaming the streaming to set
     */
    public void setStreaming(Streaming streaming) {
        this.streaming = streaming;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the  creationDate
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}