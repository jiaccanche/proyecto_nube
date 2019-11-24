package mx.ourpodcast.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ChatRequest {

    private Integer idChat;

    @NotNull(message = "La fecha de inicio es nula")
    private String initDate;

    @NotNull(message = "El usuario1 es nulo")
    private Integer idUsuario1;

    @NotNull(message = "El usuario2 es nulo")
    private Integer idUsuario2;

    public ChatRequest(){}


    public Integer getIdChat() {
        return this.idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public String getInitDate() {
        return this.initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public Integer getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(Integer idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public Integer getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(Integer idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

   
}