package mx.ourpodcast.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.PodcastNotFoundException;
import mx.ourpodcast.exceptions.PodcastUnauthorizedException;
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.PodcastRepository;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.PodcastRequest;

@Service
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    public List<Podcast> getAllPodcastByUsuario(Integer idUsuario){
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getIdUsuario() != idUsuario){
            throw new PodcastUnauthorizedException("No tiene permisos para ver los podcast del usuario " + idUsuario);
        }
        List<Podcast> podcasts = podcastRepository.findByUsuario(user);
        return podcasts;
    }

    public Podcast createPodcast(PodcastRequest request) { 
        //el podcast se crea con el usuarioen sesión
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String code = generateCode();
        
        Podcast podcast = new Podcast();
        podcast.setCode(code);
        podcast.setContentUrl(request.getContentUrl());
        podcast.setTitle(request.getTitle());
        podcast.setUsuario(user);

        return podcastRepository.save(podcast);
    }

    public Podcast updatePodcast(PodcastRequest request) {

        Podcast podcast;
        try{
            podcast = podcastRepository.findByCode(request.getCode()).get();
            
        }
        catch(NoSuchElementException | NullPointerException e){
            throw new PodcastNotFoundException("No existe un podcast con código " + request.getCode());  
        }
       
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getIdUsuario() != podcast.getUsuario().getIdUsuario()){
            throw new PodcastUnauthorizedException("No tiene permiso para editar el podcast");
        }

        podcast.setContentUrl(request.getContentUrl());
        podcast.setTitle(request.getTitle());
        
        return podcastRepository.save(podcast);  
        
    }

    public void deletePodcast(String code) {
        
        Optional<Podcast> podcastEncontrado;
        try{
          podcastEncontrado = podcastRepository.findByCode(code);
          podcastRepository.delete(podcastEncontrado.get());
        }
        catch(NoSuchElementException e){
            throw new PodcastNotFoundException("No existe un podcast con código " + code);  
        }

        
    }

    public Podcast getPodcastById(int idPodcast) {
        try{
            Optional<Podcast> optional = podcastRepository.findById(idPodcast);
            return optional.get();
        }catch(NoSuchElementException | NullPointerException e) {
            throw new PodcastNotFoundException("No existe un podcast con el id " + idPodcast);
        }

    }

    public Podcast getPodcastByCode(String code) {
    
        Optional<Podcast> podcastEncontrado;
        try{
            podcastEncontrado = podcastRepository.findByCode(code);
            return podcastEncontrado.get();
        }
        catch(NoSuchElementException | NullPointerException e){
            throw new PodcastNotFoundException("No existe un podcast con código " + code);
        
        }
        
    }

    public UrlResource getPodcastContent(String code) throws MalformedURLException {
        Podcast podcast = getPodcastByCode(code);
        String ubicacion = podcast.getContentURL();
        UrlResource video = new UrlResource("file:" + ubicacion);
        return video;
    }

    public ResourceRegion getPodcastContentSplitted(String code, HttpHeaders headers) throws IOException {
        
        Podcast podcast =  getPodcastByCode(code);
        String ubicacion = podcast.getContentURL();
        UrlResource media = new UrlResource("file:" + ubicacion);
        //UrlResource media = new UrlResource(ubicacion);
        ResourceRegion region = partirMedia(media, headers);
        return region;
        
    }

    private ResourceRegion partirMedia(UrlResource media, HttpHeaders headers) throws IOException {
        long longitudMedia = media.contentLength();
        HttpRange rango = headers.getRange().size() != 0 ? headers.getRange().get(0) : null;

        if(rango == null)  {
            long paso = Math.min(1 * 1024, longitudMedia);
            return new ResourceRegion(media, 0, paso);
        }

        long inicio = rango.getRangeStart(longitudMedia);
        long fin = rango.getRangeEnd(longitudMedia);
        long paso = Math.min(1 * 1024 * 1024, fin - inicio + 1);
        return new ResourceRegion(media, inicio, paso);
        
    }

    private String generateCode(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
          StringBuilder salt = new StringBuilder();
          Random rnd = new Random();
          while (salt.length() < 5) { // length of the random string.
              int index = (int) (rnd.nextFloat() * SALTCHARS.length());
              salt.append(SALTCHARS.charAt(index));
          }
        return salt.toString();
    }
}