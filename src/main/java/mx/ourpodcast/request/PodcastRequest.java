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

    @NotNull(message = "El contenido es nulo")
    @NotEmpty(message = "El contenido es vacío")
    private String contentUrl;

    private String code;
    
    public PodcastRequest(){}
    

    /**
     * @return the code
     */


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

}