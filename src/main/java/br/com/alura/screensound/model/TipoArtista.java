package br.com.alura.screensound.model;

import static java.util.function.Function.identity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Tipo do artista.
 */
public enum TipoArtista {

  SOLO("solo"),
  DUPLA("dupla"),
  BANDA("banda");

  @Getter
  private String tipo;

  TipoArtista(String tipo) {
    this.tipo = tipo;
  }

  private static final Map<String, TipoArtista> ENUM_MAP = Arrays.stream(TipoArtista.values())
      .collect(
          Collectors.toMap(tipoArtista -> tipoArtista.getTipo().toLowerCase(),
          identity()));

  public static TipoArtista fromTipo(final String tipo) {
    return ENUM_MAP.get(tipo.toLowerCase());
  }

}
