package mx.ourpodcast.model;

import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Integer idChat;

    @Column(name = "init_date")
    private LocalDate initDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario1")
    private Usuario usuario1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario2")
    private Usuario usuario2;


    public Chat(){}


    public Integer getIdChat() {
        return this.idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public LocalDate getInitDate() {
        return this.initDate;
    }

    public void setInitDate(LocalDate initDate) {
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