package mx.ourpodcast.request;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;

@Entity
@Table(name = "comments")
public class CommentRequest{

   
    private int idComment;

    @NotNull(message = "El usuario es nulo")
    private Usuario usuario;

    @NotNull(message = "El streaming es nulo")
    private Streaming streaming;

    @NotNull(message = "La fecha-hora es nula")
    private LocalDateTime dateTime;

    @NotNull(message = "El contenido es nulo")
    @Size(min = 5, max = 500, message 
      = "El contenido debe tener entre 1 y 500 caracteres")
    @NotEmpty(message = "El contenido es vacío")
    private String content;


    /**
     * @return the idComment
     */
    public int getIdComment() {
        return idComment;
    }

    /**
     * @param idComment the idComment to set
     */
    public void setIdComment(int idComment) {
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
     * @return the dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}