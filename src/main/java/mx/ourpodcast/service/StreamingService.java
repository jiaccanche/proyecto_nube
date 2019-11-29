package mx.ourpodcast.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.PodcastNotFoundException;
import mx.ourpodcast.exceptions.StreamingAlreadyExistsException;
import mx.ourpodcast.exceptions.StreamingNotAviableException;
import mx.ourpodcast.exceptions.StreamingNotFoundException;
import mx.ourpodcast.exceptions.StreamingUnauthorizedException;
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.PodcastRepository;
import mx.ourpodcast.repository.StreamingRepository;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.StreamingRequest;

@Service
public class StreamingService {

  @Autowired
  private StreamingRepository streamingRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private PodcastService podcastService;

  public List<Streaming> getAllStreamings() {
    List<Streaming> streamings = streamingRepository.findAll();
    return streamings;
  }

  public Streaming getStreamingById(Integer idStreaming) {
    try {
      Optional<Streaming> optional = streamingRepository.findById(idStreaming);
      return optional.get();
    } catch (NullPointerException | NoSuchElementException e) {
      throw new StreamingNotFoundException("No existe un streaming con el id " + idStreaming);
    }

  }

  public Streaming getStreamingByCode(String code) {

    Optional<Streaming> streamingEncontrado;
    try {
      streamingEncontrado = streamingRepository.findByCode(code);
      return streamingEncontrado.get();
    } catch (NoSuchElementException | NullPointerException e) {
      throw new StreamingNotFoundException("No existe un streaming con código " + code);
    }

    

  }

  public Streaming createStreaming(@Valid StreamingRequest request) {

    Podcast podcastEncontrado = podcastService.getPodcastById(request.getIdPodcast());
    Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    String code = generateCode();
    Streaming streaming = new Streaming();
    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime begin = LocalDateTime.parse(request.getBeginDateTime(), DATEFORMATTER);
    LocalDateTime finish = LocalDateTime.parse(request.getFinishDateTime(), DATEFORMATTER);
    streaming.setBeginDateTime(begin);
    streaming.setFinishDateTime(finish);
    streaming.setViewCount(0);
    streaming.setCode(code);
    streaming.setUsuario(user);
    streaming.setPodcast(podcastEncontrado);

    
    return streamingRepository.save(streaming);

  }

  public Streaming updateStreaming(@Valid StreamingRequest request) {
    

    Streaming streaming;
    try {
      Optional<Streaming> optional = streamingRepository.findByCode(request.getCode()); 
      streaming = optional.get();
    }catch (NoSuchElementException | NullPointerException e) {
      throw new StreamingNotFoundException("No existe un streaming con código " + request.getCode());
    }

    Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (user.getIdUsuario() != streaming.getUsuario().getIdUsuario()) {
      throw new StreamingUnauthorizedException("No tiene permisos para editar la info del streaming " +  request.getCode());
    }

    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime begin = LocalDateTime.parse(request.getBeginDateTime(), DATEFORMATTER);
    LocalDateTime finish = LocalDateTime.parse(request.getFinishDateTime(), DATEFORMATTER);
    streaming.setBeginDateTime(begin);
    streaming.setFinishDateTime(finish);

    
    return streamingRepository.save(streaming);

  }

  public void deleteStreaming(String code) {

    Streaming streaming;
    try {
      Optional<Streaming> optional = streamingRepository.findByCode(code); 
      streaming = optional.get();
    }catch (NoSuchElementException | NullPointerException e) {
      throw new StreamingNotFoundException("No existe un streaming con código " + code);
    }

    Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (user.getIdUsuario() != streaming.getUsuario().getIdUsuario()) {
      throw new StreamingUnauthorizedException("No tiene permisos para eliminar el streaming " +  code);
    }

    streamingRepository.delete(streaming);

  }

  public List<Streaming> getStreamingsByUsuario(Integer idUsuario) {
    
    Usuario usuario = usuarioService.getUsuarioById(idUsuario);
    List<Streaming> streamings = streamingRepository.findByUsuario(usuario);
    return streamings;
  }

  public List<Streaming> getStreamingsByDate(String date) {
    List<Streaming> streamings = streamingRepository.findAll();
    List<Streaming> dateStreamings = new ArrayList<>();
    LocalDate datee = LocalDate.now();
    System.out.println("actual:"+datee);

    for(int i = 0; i < streamings.size(); i++){
      Streaming streaming = streamings.get(i);
      LocalDate initStreaming = streaming.getBeginDateTime().toLocalDate();
      LocalDate finishStreaming = streaming.getFinishDateTime().toLocalDate();
      System.out.println("init: "+initStreaming);
      System.out.println("fin:"+finishStreaming);
      if(datee.compareTo(initStreaming) == 0 || datee.compareTo(finishStreaming) == 0){
        System.out.println("yess");
        dateStreamings.add(streaming);
      }
    }
    return dateStreamings;
  }

  private String generateCode() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 5) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    return salt.toString();
  }

  public ResourceRegion getStreamingContentByCodeSplitted(String code, HttpHeaders headers) throws IOException {
    Streaming streamingEncontrado;
    try {
      Optional<Streaming> optional = streamingRepository.findByCode(code);
      streamingEncontrado = optional.get();
    } catch (NoSuchElementException | NullPointerException e) {
      throw new StreamingNotFoundException("No existe un streaming con código " + code);
    }
    LocalDateTime today = LocalDateTime.now();
    LocalDateTime initStreaming = streamingEncontrado.getBeginDateTime();
    LocalDateTime finishStreaming = streamingEncontrado.getFinishDateTime();

    if (today.isAfter(initStreaming) && today.isBefore(finishStreaming)) {
      incrementViewCountOfStreaming(streamingEncontrado);
      String podcastCode = streamingEncontrado.getPodcast().getCode();
      return podcastService.getPodcastContentSplitted(podcastCode, headers);
    } else {
      throw new StreamingNotAviableException("el contenido de este streaming no está disponible");
    }
  }

  public UrlResource getStreamingContentByCode(String code) throws MalformedURLException {
	Streaming streamingEncontrado;
    try {
      Optional<Streaming> optional = streamingRepository.findByCode(code);
        streamingEncontrado = optional.get();
    } catch (NoSuchElementException | NullPointerException e) {
        throw new StreamingNotFoundException("No existe un streaming con código " + code);
    }
    LocalDateTime today = LocalDateTime.now();
    LocalDateTime initStreaming = streamingEncontrado.getBeginDateTime();
    LocalDateTime finishStreaming = streamingEncontrado.getFinishDateTime();

    if(today.isAfter(initStreaming) && today.isBefore(finishStreaming)){
      incrementViewCountOfStreaming(streamingEncontrado);
      String podcastCode = streamingEncontrado.getPodcast().getCode();
      return podcastService.getPodcastContent(podcastCode);
    }else{
      throw new StreamingNotAviableException("el contenido de este streaming no está disponible");
    }
  }

  private void incrementViewCountOfStreaming(Streaming streaming){
    streaming.setViewCount(streaming.getViewCount() + 1);
    streamingRepository.save(streaming);
  }

}