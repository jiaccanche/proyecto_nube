package mx.ourpodcast.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.PodcastAlreadyExistException;
import mx.ourpodcast.exceptions.PodcastNotFoundException;
import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.repository.PodcastRepository;
import mx.ourpodcast.request.PodcastRequest;

@Service
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    public List<Podcast> getAllPodcast(){
        List<Podcast> podcasts = podcastRepository.findAll();
        return podcasts;
    }

    public Podcast createPodcast(PodcastRequest request) {
        
        Optional<Podcast> optional = podcastRepository.findById(request.getidPodcast());
        if(optional.isPresent()){
            throw new PodcastAlreadyExistException(request.getidPodcast().toString());
        } else{
            Podcast podcast = new Podcast();
            podcast.setCode(request.getCode());
            podcast.setContentUrl(request.getContentUrl());
            podcast.setTitle(request.getTitle());

            podcastRepository.save(podcast);
            return podcast;
        }
    }

    public Podcast updatePodcast(PodcastRequest request) {
        Optional<Podcast> optional = podcastRepository.findById(request.getidPodcast());
        if(optional.isPresent()){
            Podcast podcast = optional.get();
            podcast.setCode(request.getCode());
            podcast.setContentUrl(request.getContentUrl());
            podcast.setTitle(request.getTitle());
           
            podcastRepository.save(podcast);
            return podcast;
        } else{
            throw new PodcastNotFoundException("No existe el podcast con el id " + request.getidPodcast());
        }
    }

    public void deletePodcast(int idPodcast) {
        
        Optional<Podcast> optional = podcastRepository.findById(idPodcast);
        if(optional.isPresent()){
            Podcast podcast = optional.get();
            podcastRepository.delete(podcast);
        } else{
            throw new PodcastNotFoundException("No existe el podcast con el id " + idPodcast);
        }
    }

    public Podcast getPodcastById(int idPodcast) {
        Optional<Podcast> optional = podcastRepository.findById(idPodcast);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new PodcastNotFoundException("No existe un podcast con el id " + idPodcast);
        }

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
}