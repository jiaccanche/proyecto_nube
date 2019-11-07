package mx.ourpodcast.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.StreamingAlreadyExistsException;
import mx.ourpodcast.exceptions.StreamingNotFoundException;
import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.StreamingRepository;
import mx.ourpodcast.request.StreamingRequest;

@Service
public class StreamingService{

    @Autowired
    private StreamingRepository streamingRepository;

	public List<Streaming> getAllStreamings() {
		return streamingRepository.findAll();
	}

	public Streaming getStreamingById(Integer idStreaming) {
        Optional<Streaming> optional = streamingRepository.findById(idStreaming);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new StreamingNotFoundException("No existe un streaming con el id " + idStreaming);
        }
	}

	public Streaming createStreaming(@Valid StreamingRequest request) {
		Optional<Streaming> optional = streamingRepository.findById(request.getIdStreaming());
        if(optional.isPresent()){
           throw new StreamingAlreadyExistsException("Ya existe un streaming con el id " + request.getIdStreaming());
        }else{
            Streaming streaming = new Streaming();
            streaming.setBeginDateTime(request.getBeginDateTime());
            streaming.setFinishDateTime(request.getFinishDateTime());
            streaming.setViewCount(0);
            
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());

            streaming.setUsuario(usuario);

            PodcastService podcastService = new PodcastService();
            Podcast podcast = podcastService.getPodcastById(request.getIdPodcast());

            streaming.setPodcast(podcast);
            
            streamingRepository.save(streaming);
            return streaming;
        }
	}

	public Streaming updateStreaming(@Valid StreamingRequest request) {
		Optional<Streaming> optional = streamingRepository.findById(request.getIdStreaming());
        if(optional.isPresent()){
            Streaming streaming = optional.get();
            streaming.setBeginDateTime(request.getBeginDateTime());
            streaming.setFinishDateTime(request.getFinishDateTime());
            streaming.setViewCount(request.getViewCount());
            
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());

            streaming.setUsuario(usuario);

            PodcastService podcastService = new PodcastService();
            Podcast podcast = podcastService.getPodcastById(request.getIdPodcast());

            streaming.setPodcast(podcast);
            
            streamingRepository.save(streaming);
            return streaming;

        }else{
            throw new StreamingNotFoundException("No existe un streaming con el id " + request.getIdStreaming());
        }
	}

	public void deleteStreaming(Integer idStreaming) {
        Optional<Streaming> optional = streamingRepository.findById(idStreaming);
        if(optional.isPresent()){
            streamingRepository.delete(optional.get());
        }else{
            throw new StreamingNotFoundException("No existe un streaming con el id " + idStreaming);
        }
	}

	public List<Streaming> getStreamingsByUsuario(Integer idUsuario) {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        List<Streaming> streamings = streamingRepository.findAllByUsuario(usuario);
		return streamings;
	}

	public List<Streaming> getStreamingsByDate(LocalDateTime dateTime) {
        List<Streaming> streamings = streamingRepository.findAllBybeginDateTime(dateTime);
        streamings.addAll(streamingRepository.findAllByfinishDateTime(dateTime));
		return streamings;
	}

}