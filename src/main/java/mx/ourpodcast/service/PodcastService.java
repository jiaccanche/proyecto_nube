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
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.PodcastAlreadyExistException;
import mx.ourpodcast.exceptions.PodcastNotFoundException;
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
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Podcast> getAllPodcast(String token){
        Usuario userEncontrado = usuarioRepository.findByToken(token);
        List<Podcast> podcasts = podcastRepository.findByUsuario(userEncontrado);
        return podcasts;
    }

    public Podcast createPodcast(PodcastRequest request, String token) { 
        Usuario userEncontrado;
        try{
            userEncontrado = usuarioRepository.findByToken(token);
          }
          catch(NoSuchElementException e){
              throw new UsuarioNotFoundException("No tienes acceso");
            
          }
            String code = generateCode();
          
            Podcast podcast = new Podcast();
            podcast.setCode(code);
            podcast.setContentUrl(request.getContentUrl());
            podcast.setTitle(request.getTitle());
            podcast.setUsuario(userEncontrado);

            podcastRepository.save(podcast);
            return podcast;
    }

    public Podcast updatePodcast(String code, PodcastRequest request, String token) {
        //Agregar validación
        Usuario userEncontrado;
        try{
            userEncontrado = usuarioRepository.findByToken(token);
          }
          catch(NoSuchElementException e){
              throw new UsuarioNotFoundException("No tienes acceso");
            
          }

          Optional<Podcast> podcastEncontrado;
          try{
            podcastEncontrado = podcastRepository.findByCode(code);
          }
          catch(NoSuchElementException e){
              throw new UsuarioNotFoundException("No tienes acceso");  
          }
       

            if(userEncontrado.getIdUsuario() != podcastEncontrado.get().getUsuario().getIdUsuario()){
              throw new PodcastNotFoundException("Petición denegada");
            }
               Podcast podcast = podcastEncontrado.get();
               podcast.setContentUrl(request.getContentUrl());
               podcast.setTitle(request.getTitle());
               podcastRepository.save(podcast);  
               return podcast;
        
    }

    public void deletePodcast(String code) {
        
        Optional<Podcast> podcastEncontrado;
        try{
          podcastEncontrado = podcastRepository.findByCode(code);
        }
        catch(NoSuchElementException e){
            throw new UsuarioNotFoundException("No tienes acceso");  
        }

        podcastRepository.delete(podcastEncontrado.get());
    }

    public Podcast getPodcastById(int idPodcast) {
        Optional<Podcast> optional = podcastRepository.findById(idPodcast);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new PodcastNotFoundException("No existe un podcast con el id " + idPodcast);
        }

    }

    public Podcast getPodcastByCode(String token, String code) {
        Usuario userEncontrado;
        try{
            userEncontrado = usuarioRepository.findByToken(token);
          }
          catch(NoSuchElementException e){
              throw new UsuarioNotFoundException("Petición denegada");
            
          }
        
        Optional<Podcast> podcastEncontrado;
          try{
           podcastEncontrado = podcastRepository.findByCode(code);
          }
          catch(NoSuchElementException e){
              throw new PodcastNotFoundException("Petición denegada");
            
          }

        if(userEncontrado.getIdUsuario()!=podcastEncontrado.get().getUsuario().getIdUsuario()){
            throw new PodcastNotFoundException("Petición denegada"); 
        }
        
        return podcastEncontrado.get();
        
       

    }

    public UrlResource getPodcastContent(int idPodcast) throws MalformedURLException {
        Optional<Podcast> optional = podcastRepository.findById(idPodcast);
        
        if (optional.isPresent()) {
            Podcast podcast = optional.get();
            String ubicacion = podcast.getContentURL();
            UrlResource video = new UrlResource("file:" + ubicacion);
            return video;
        }else{
            throw new PodcastNotFoundException("No existe un podcast con el id " + idPodcast);
        }
    }

    public ResourceRegion getPodcastContentSplitted(int idPodcast, HttpHeaders headers) throws IOException {
        
        /*podcastRepository.findById(idPodcast).ifPresent(p -> {
            Podcast podcast = optional.get();
            String ubicacion = podcast.getContentURL();
            UrlResource video = new UrlResource("file:" + ubicacion);
            ResourceRegion region = partirVideo(video, headers);

        });

        if (!optiona.isPresent()) {
            throw  new Exception();
    }*/
        Optional<Podcast> optional = podcastRepository.findById(idPodcast);
            
        if (optional.isPresent()) {
            Podcast podcast = optional.get();
            String ubicacion = podcast.getContentURL();
            UrlResource video = new UrlResource("file:" + ubicacion);
            ResourceRegion region = partirVideo(video, headers);

            return region;
        }else{
            throw new PodcastNotFoundException("No existe un podcast con el id " + idPodcast);
        }
    }

    private ResourceRegion partirVideo(UrlResource video, HttpHeaders headers) throws IOException {
        long longitudVideo = video.contentLength();
        HttpRange rango = headers.getRange().size() != 0 ? headers.getRange().get(0) : null;

        if(rango == null)  {
            long paso = Math.min(1 * 1024, longitudVideo);
            return new ResourceRegion(video, 0, paso);
        }

        long inicio = rango.getRangeStart(longitudVideo);
        long fin = rango.getRangeEnd(longitudVideo);
        long paso = Math.min(1 * 1024 * 1024, fin - inicio + 1);
        return new ResourceRegion(video, inicio, paso);
        
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