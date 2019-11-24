package mx.ourpodcast.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import mx.ourpodcast.service.StreamingService;

@RestController
public class StreamingRest{

    @Autowired
    private StreamingService streamingService;

    @GetMapping("/streaming")
    public ResponseEntity<List<Streaming>> getAllStreamings(@RequestHeader(value="Authorization") String token){
        List<Streaming> streamings = streamingService.getAllStreamings(token);
        return ResponseEntity.ok().body(streamings);
    }

    @GetMapping("/streaming/{code}")
    public ResponseEntity<Streaming> getStreamingById(@PathVariable String code){
        Streaming streaming = streamingService.getStreamingByCode(code);
        return ResponseEntity.ok().body(streaming);
    }

    @PostMapping("/streaming")
    public ResponseEntity<Streaming> createStreaming(@RequestHeader(value="Authorization") String token, @Valid @RequestBody StreamingRequest request){
        Streaming streaming = streamingService.createStreaming(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(streaming);
    }

    @PutMapping("/streaming/{code}")
    public ResponseEntity<Streaming> updateStreaming(@PathVariable String code, @RequestHeader(value="Authorization") String token, @Valid @RequestBody StreamingRequest request){
        Streaming streaming = streamingService.updateStreaming(code, token, request);
        return ResponseEntity.ok().body(streaming);
    }

    @DeleteMapping("/streaming/delete/{code}")
    public ResponseEntity<Void> deleteStreaming(@PathVariable String code, @RequestHeader(value="Authorization") String token){
        streamingService.deleteStreaming(code, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/streaming/usuario/{idUsuario}")
    public ResponseEntity<List<Streaming>> getStreamingsByUsuario(@PathVariable Integer idUsuario){
        List<Streaming> streamings = streamingService.getStreamingsByUsuario(idUsuario);
        return ResponseEntity.ok().body(streamings);
    }

    @GetMapping("/streaming/date/{date}")
    public ResponseEntity<List<Streaming>> getStreamingsByDate(@PathVariable LocalDateTime date){
        List<Streaming> streamings = streamingService.getStreamingsByDate(date);
        return ResponseEntity.ok().body(streamings);
    }
}