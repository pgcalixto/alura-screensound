package br.com.alura.screensound.service;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.model.TipoArtista;
import br.com.alura.screensound.repository.ArtistaRepository;
import br.com.alura.screensound.repository.MusicaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ScreensoundService {

  @Autowired
  private ArtistaRepository artistaRepository;

  @Autowired
  private MusicaRepository musicaRepository;

  @Autowired
  private OpenAiService openAiService;

  public void cadastrarArtista(final Scanner scanner) {
    System.out.println("Digite o nome do artista que se deseja cadastrar:");

    final String nomeArtista = scanner.nextLine();

    final String nomeArtistaCapitalizado = StringUtils.capitalize(nomeArtista);

    System.out.printf(
        "Tipo do artista (%s):\n",
        Arrays.stream(TipoArtista.values())
              .map(TipoArtista::getTipo)
              .collect(Collectors.toList())
              .toString()
    );

    final String tipoArtistaTexto = scanner.nextLine();

    final TipoArtista tipoArtista = TipoArtista.fromTipo(tipoArtistaTexto);

    final Artista artista = Artista
        .builder()
        .nome(nomeArtistaCapitalizado)
        .tipoArtista(tipoArtista)
        .build();

    artistaRepository.save(artista);
  }

  @Transactional
  public void listarArtistas() {

    final List<Artista> artistas = artistaRepository.findAll();

    artistas.forEach(System.out::println);
  }

  @Transactional
  public Optional<Artista> buscarArtista(final Scanner scanner) {

    System.out.println("Digite o trecho do nome artista que se deseja buscar:");

    final String trechoNomeArtista = scanner.nextLine();

    final Optional<Artista> optionalArtista = artistaRepository.findByNomeContainingIgnoreCase(trechoNomeArtista);

    if (optionalArtista.isEmpty()) {
      System.out.println("Artista não encontrado!");
      return optionalArtista;
    }

    final Artista artista = optionalArtista.get();

    System.out.println(artista);

    return optionalArtista;
  }

  @Transactional
  public void saberMaisSobreArtista(Scanner scanner) {

    final Optional<Artista> optionalArtista = buscarArtista(scanner);

    if (optionalArtista.isEmpty()) {
      return;
    }

    final Artista artista = optionalArtista.get();

    final String resumoArtista = openAiService.getResumoArtista(artista.getNome());

    if (resumoArtista == null) {
      System.out.println("Não foi possível recuperar o resumo do artista.");
    }

    System.out.println("Resumo do artista: " + resumoArtista);
  }

  @Transactional
  public void cadastrarMusica(final Scanner scanner) {

    final Optional<Artista> optionalArtista = buscarArtista(scanner);

    if (optionalArtista.isEmpty()) {
      return;
    }

    final Artista artista = optionalArtista.get();

    System.out.println("Digite o título da música:");

    final String tituloMusica = scanner.nextLine();

    final Musica musica = Musica
        .builder()
        .titulo(tituloMusica)
        .build();

    artista.adicionaMusica(musica);

    artistaRepository.save(artista);
  }

  public void listarMusicas() {

    final List<Musica> musicas = musicaRepository.findAll();

    final Map<Artista, List<Musica>> musicasPorArtista = musicas
        .stream()
        .collect(Collectors.groupingBy(Musica::getArtista));

    musicasPorArtista.forEach((chaveArtista, valorMusicas) -> {
      System.out.printf("Artista (%d): %s\n", chaveArtista.getId(), chaveArtista.getNome());

      valorMusicas.forEach(musica -> System.out.println("\t" + musica.toString()));
    });
  }

}
