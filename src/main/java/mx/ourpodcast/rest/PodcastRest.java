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

    @GetMapping("/podcast/usuario/{idUsuario}")
    public ResponseEntity<List<Podcast>> getAllPodcastByUsuario(@PathVariable Integer idUsuario){
        List<Podcast> podcasts = podcastService.getAllPodcastByUsuario(idUsuario);
        return ResponseEntity.status(HttpStatus.FOUND).body(podcasts);
    }

    @GetMapping("/podcast/{code}")
    public ResponseEntity<Podcast> getPodcastById(@PathVariable String code){
        Podcast podcast = podcastService.getPodcastByCode(code);
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

    @DeleteMapping("/podcast/{code}")
    public ResponseEntity<Void> deletePodcast(@PathVariable String code){
        podcastService.deletePodcast(code);
        return ResponseEntity.ok().build();
    }
}