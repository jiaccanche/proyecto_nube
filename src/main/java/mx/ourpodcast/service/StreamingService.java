package mx.ourpodcast.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.StreamingAlreadyExistsException;
import mx.ourpodcast.exceptions.StreamingNotFoundException;
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
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PodcastRepository podcastRepository;

  public List<Streaming> getAllStreamings(String token) {
    Usuario userEncontrado = usuarioRepository.findByToken(token);
    List<Streaming> streamings = streamingRepository.findByUsuario(userEncontrado);
    return streamings;
  }

  public Streaming getStreamingById(Integer idStreaming) {
    try {
      Optional<Streaming> optional = streamingRepository.findById(idStreaming);

      if (optional.isPresent()) {
        return optional.get();
      } else {
        throw new StreamingNotFoundException("No existe un streaming con el id " + idStreaming);
      }
    } catch (NullPointerException | NoSuchElementException e) {
      throw new StreamingNotFoundException("No existe el streaming");
    }

  }

  public Streaming getStreamingByCode(String code) {

    Streaming streamingEncontrado;
    try {
      streamingEncontrado = streamingRepository.findByCode(code);
    } catch (NoSuchElementException e) {
      throw new StreamingNotFoundException("Petición denegada");

    }

    return streamingEncontrado;

  }

  public Streaming createStreaming(String token, @Valid StreamingRequest request) {

    Usuario userEncontrado;
    try {
      userEncontrado = usuarioRepository.findByToken(token);
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");

    }

    Optional<Podcast> podcastEncontrado;
    try {
      podcastEncontrado = podcastRepository.findByCode(request.getPodcastCode());
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");

    }
    String code = generateCode();
    Streaming streaming = new Streaming();
    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime begin = LocalDateTime.parse(request.getBeginDateTime(), DATEFORMATTER);
    LocalDateTime finish = LocalDateTime.parse(request.getFinishDateTime(), DATEFORMATTER);
    streaming.setBeginDateTime(begin);
    streaming.setFinishDateTime(finish);
    streaming.setViewCount(0);
    streaming.setCode(code);
    streaming.setUsuario(userEncontrado);
    streaming.setPodcast(podcastEncontrado.get());

    streamingRepository.save(streaming);
    return streaming;

  }

  public Streaming updateStreaming(String code, String token, @Valid StreamingRequest request) {
    Usuario userEncontrado;
    try {
      userEncontrado = usuarioRepository.findByToken(token);
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");

    }

    Streaming streamingEncontrado;
    try {
      streamingEncontrado = streamingRepository.findByCode(code);
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");
    }

    if (userEncontrado.getIdUsuario() != streamingEncontrado.getUsuario().getIdUsuario()) {
      throw new StreamingNotFoundException("Petición denegada");
    }
    Streaming streaming = streamingEncontrado;
    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime begin = LocalDateTime.parse(request.getBeginDateTime(), DATEFORMATTER);
    LocalDateTime finish = LocalDateTime.parse(request.getFinishDateTime(), DATEFORMATTER);
    streaming.setBeginDateTime(begin);
    streaming.setFinishDateTime(finish);

    streamingRepository.save(streaming);

    return streaming;
  }

  public void deleteStreaming(String code, String token) {
    Usuario userEncontrado;
    try {
      userEncontrado = usuarioRepository.findByToken(token);
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");

    }

    Streaming streamingEncontrado;
    try {
      streamingEncontrado = streamingRepository.findByCode(code);
    } catch (NoSuchElementException e) {
      throw new UsuarioNotFoundException("No tienes acceso");
    }

    if (userEncontrado.getIdUsuario() != streamingEncontrado.getUsuario().getIdUsuario()) {
      throw new StreamingNotFoundException("Petición denegada");
    }

    streamingRepository.delete(streamingEncontrado);

  }

  public List<Streaming> getStreamingsByUsuario(Integer idUsuario) {
    UsuarioService usuarioService = new UsuarioService();
    Usuario usuario = usuarioService.getUsuarioById(idUsuario);
    List<Streaming> streamings = streamingRepository.findByUsuario(usuario);
    return streamings;
  }

  public List<Streaming> getStreamingsByDate(LocalDateTime dateTime) {
    List<Streaming> streamings = streamingRepository.findAllBybeginDateTime(dateTime);
    streamings.addAll(streamingRepository.findAllByfinishDateTime(dateTime));
    return streamings;
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

}