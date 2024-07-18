package br.com.alura.screensound.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompletionDto(@JsonAlias("choices") List<ChoiceDto> escolhas) {

}
