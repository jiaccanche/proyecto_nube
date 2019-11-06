package mx.ourpodcast.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PodcastRequest{

    @NotNull(message = "El título es nulo")
    @Size(min = 5, max = 50, message 
      = "El título debe tener entre 5 y 50 caracteres")
    @NotEmpty(message = "El título es vacío")
    private String title;

    @NotNull(message = "El código es nulo")
    @Size(min = 5, max = 5, message 
      = "El codigo debe tener 5 caracteres de longitud")
    @NotEmpty(message = "El código es vacío")
    private String code;

    @NotNull(message = "El contenido es nulo")
    private String contentUrl;

    @NotNull(message = "El usuario es nulo")
    private Integer idUsuario;

    private Integer idPodcast;

    public PodcastRequest(){}
    

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
     * @return the contentUrl
     */
    public String getContentUrl() {
        return contentUrl;
    }

    /**
     * @param contentUrl the contentUrl to set
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    /**
     * @return the idUsuario
     */
    public Integer getidUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setidUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    /**
     * @return the idPodcast
     */
    public Integer getidPodcast() {
        return idPodcast;
    }
    
    /**
     * @param idPodcast the idPodcast to set
     */
    public void setidPodcast(Integer idPodcast) {
        this.idPodcast = idPodcast;
    }
    

}