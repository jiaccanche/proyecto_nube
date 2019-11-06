package mx.ourpodcast.rest;

import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.request.PodcastRequest;
import mx.ourpodcast.service.PodcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.Valid;

@RestController
public class PodcastRest{

    @Autowired
    private PodcastService podcastService;

    @GetMapping("/podcast")
    public ResponseEntity<List<Podcast>> getAllAlumnos(){
        List<Podcast> podcasts = podcastService.getAllPodcast();
        return ResponseEntity.ok().body(podcasts);
    }

    @GetMapping("/podcast/{idPodcast}")
    public ResponseEntity<Podcast> getPodcastById(@PathVariable int idPodcast){
        Podcast podcast = podcastService.getPodcastById(idPodcast);
        return ResponseEntity.ok().body(podcast);
    }

    @PostMapping("/podcast")
    public ResponseEntity<Podcast> createPodcast(@Valid @RequestBody PodcastRequest body){
        Podcast podcast = podcastService.createPodcast(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(podcast);
    }

    @PutMapping("/podcast")
    public ResponseEntity<Podcast> updatePodcast(@Valid @RequestBody PodcastRequest body){
        Podcast podcast = podcastService.updatePodcast(body);
        return ResponseEntity.ok().body(podcast);
    }

    @DeleteMapping("/podcast/{idPodcast}")
    public ResponseEntity<Void> deletePodcast(@PathVariable int idPodcast){
        podcastService.deletePodcast(idPodcast);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/podcast/user/{idUsuario}")
    public ResponseEntity<List<Podcast>> getPodcastByUser(@PathVariable int idUsuario){
        List<Podcast> podcasts = podcastService.getPodcastByUser(idUsuario);
        return ResponseEntity.ok().body(podcasts);
    }

    @GetMapping("/podcast/{idPodcast}/splitted")
    public ResponseEntity<ResourceRegion> getPodcastContentSplitted(@PathVariable int idPodcast, @RequestHeader HttpHeaders headers) throws IOException {
        ResourceRegion region = podcastService.getPodcastContentSplitted(idPodcast, headers);
        
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        .contentType(type)
        .body(region);
    }

    @GetMapping("/podcast/{idPodcast}/content")
    public ResponseEntity<UrlResource> getPodcastContent(@PathVariable int idPodcast) throws MalformedURLException {
        UrlResource resource = podcastService.getPodcastContent(idPodcast);
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(type).body(resource);
    }
}