package br.com.alura.screensound.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.screensound.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

  Optional<Artista> findByNomeContainingIgnoreCase(final String nomeArtista);

}
