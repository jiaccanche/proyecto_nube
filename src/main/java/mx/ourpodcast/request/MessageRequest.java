package mx.ourpodcast.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MessageRequest {


    private Integer idMessage;

    @NotNull(message = "El contenido es nulo")
    @Size(min = 1, max = 500, message 
      = "El contenido debe tener entre 1 y 500 caracteres")
    @NotEmpty(message = "El contenido es vacío")
    private String content;

    @NotNull(message = "La fecha de envío es nula")
    private String sendDate;

    @NotNull(message = "El id del chat es nulo")
    private Integer idChat;

    @NotNull(message = "El usuario es nulo")
    private Integer idUsuario;

    public MessageRequest(){}


    public Integer getIdMessage() {
        return this.idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    /**
     * @return the sendDate
     */
    public String getSendDate() {
        return sendDate;
    }

    /**
     * @return the idChat
     */
    public Integer getIdChat() {
        return idChat;
    }
    /**
     * @param idChat the idChat to set
     */
    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
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
    
}