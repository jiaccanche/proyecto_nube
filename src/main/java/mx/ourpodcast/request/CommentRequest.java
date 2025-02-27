package mx.ourpodcast.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentRequest{

   
    private Integer idComment;

    @NotNull(message = "El idUsuario es nulo")
    private Integer idUsuario;

    @NotNull(message = "El streaming es nulo")
    private String codeStreaming;

    @NotNull(message = "La fecha-hora es nula")
    String creationDate;

    @NotNull(message = "El contenido es nulo")
    @Size(min = 5, max = 500, message 
      = "El contenido debe tener entre 1 y 500 caracteres")
    @NotEmpty(message = "El contenido es vacío")
    private String content;


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
     * @return the streaming
     */
    public String getCodeStreaming() {
        return codeStreaming;
    }

    /**
     * @param streaming the streaming to set
     */
    public void setCodeStreaming(String codeStreaming) {
        this.codeStreaming = codeStreaming;
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
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}