package br.com.alura.screensound.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Artista {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  @Enumerated(EnumType.STRING)
  private TipoArtista tipoArtista;

  @OneToMany(mappedBy="artista", cascade = CascadeType.ALL)
  private List<Musica> musicas;

  public void adicionaMusica(final Musica musica) {

    if (musicas == null) {
      musicas = List.of();
    }

    musicas.add(musica);

    musica.setArtista(this);
  }

}
