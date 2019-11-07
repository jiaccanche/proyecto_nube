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

@Entity
@Table(name = "chat")
public class Chat{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer idChat;

    @Column(name = "initDate")
    private LocalDateTime initDate;

    @ManyToOne
    @JoinColumn(name = "usuario1")
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn(name = "usuario2")
    private Usuario usuario2;

    public Chat(){}


    public Integer getIdChat() {
        return this.idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public LocalDateTime getInitDate() {
        return this.initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public Usuario getUsuario1() {
        return this.usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return this.usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

}