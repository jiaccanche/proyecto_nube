package mx.ourpodcast.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "podcast")
public class Podcast{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int idPodcast;

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @Column(name = "content")
    private String contentUrl;

    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = false)
    private Usuario usuario;

    public Podcast(){}
    
    /**
     * @return the idPodcast
     */
    public int getIdPodcast() {
        return idPodcast;
    }

    /**
     * @param idPodcast the idPodcast to set
     */
    public void setIdPodcast(int idPodcast) {
        this.idPodcast = idPodcast;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the content
     */
    public String getContentURL() {
        return contentUrl;
    }

    /**
     * @param content the content to set
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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


}