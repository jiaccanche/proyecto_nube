package mx.ourpodcast.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.request.StreamingRequest;
import mx.ourpodcast.service.PodcastService;
import mx.ourpodcast.service.StreamingService;

import org.springframework.http.MediaType;


@RestController
public class StreamingRest{

    @Autowired
    private StreamingService streamingService;

    @GetMapping("/streaming")
    public ResponseEntity<List<Streaming>> getAllStreamings(){
        List<Streaming> streamings = streamingService.getAllStreamings();
        return ResponseEntity.ok().body(streamings);
    }

    @GetMapping("/streaming/{code}")
    public ResponseEntity<Streaming> getStreamingById(@PathVariable String code){
        Streaming streaming = streamingService.getStreamingByCode(code);
        return ResponseEntity.ok().body(streaming);
    }

    @PostMapping("/streaming")
    public ResponseEntity<Streaming> createStreaming(@Valid @RequestBody StreamingRequest request){
        Streaming streaming = streamingService.createStreaming(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(streaming);
    }

    @PutMapping("/streaming")
    public ResponseEntity<Streaming> updateStreaming(@Valid @RequestBody StreamingRequest request){
        Streaming streaming = streamingService.updateStreaming(request);
        return ResponseEntity.ok().body(streaming);
    }

    @DeleteMapping("/streaming/delete/{code}")
    public ResponseEntity<Void> deleteStreaming(@PathVariable String code){
        streamingService.deleteStreaming(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/streaming/usuario/{idUsuario}")
    public ResponseEntity<List<Streaming>> getStreamingsByUsuario(@PathVariable Integer idUsuario){
        List<Streaming> streamings = streamingService.getStreamingsByUsuario(idUsuario);
        return ResponseEntity.ok().body(streamings);
    }

    @GetMapping("/streaming/date/{date}")
    public ResponseEntity<List<Streaming>> getStreamingsByDate(@PathVariable String date){
        
        List<Streaming> streamings = streamingService.getStreamingsByDate(date);
        return ResponseEntity.ok().body(streamings);
    }

    @GetMapping("/streaming/{code}/splitted")
    public ResponseEntity<ResourceRegion> getPodcastContentSplitted(@PathVariable String code, @RequestHeader HttpHeaders headers) throws IOException {
        ResourceRegion region = streamingService.getStreamingContentByCodeSplitted(code, headers);
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        .contentType(type)
        .body(region);
    }

    @GetMapping("/streaming/{code}/content")
    public ResponseEntity<UrlResource> getStreamingContent(@PathVariable String code) throws MalformedURLException {
        UrlResource resource = streamingService.getStreamingContentByCode(code);
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(type).body(resource);
    }
}