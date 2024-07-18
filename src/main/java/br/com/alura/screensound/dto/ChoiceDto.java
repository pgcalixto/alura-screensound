package br.com.alura.screensound.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChoiceDto(@JsonAlias("message") MessageDto messageDto) {

}
