package br.com.alura.screensound.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.alura.screensound.dto.CompletionDto;

@Service
public class OpenAiService {

    private final String ENDERECO_BASE = "https://api.openai.com/v1";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${open-ai.api-key}")
    private String openAiApiKey;

    public String getResumoArtista(final String nomeArtista) {

        final URI uri = UriComponentsBuilder
            .fromHttpUrl(ENDERECO_BASE + "/chat/completions")
            .build()
            .encode()
            .toUri();

        final String body = String.format(
            """
            {
              "model": "gpt-3.5-turbo-0125",
              "messages": [
                {
                  "role": "user",
                  "content": "Pode me passar um resumo de 1 frase sobre quem é o artista/banda %s?"
                }
              ]
            }
            """,
            nomeArtista);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        final HttpEntity<String> entity = new HttpEntity<>(body, headers);

        final CompletionDto completionDto = restTemplate.postForObject(uri, entity, CompletionDto.class);

        if (
            completionDto == null
            || completionDto.escolhas() == null
            || completionDto.escolhas().isEmpty()
            || completionDto.escolhas().get(0) == null
            || completionDto.escolhas().get(0).messageDto() == null
            || completionDto.escolhas().get(0).messageDto().conteudo() == null
            || completionDto.escolhas().get(0).messageDto().conteudo().isEmpty()
        ) {
          return null;
        }

        return completionDto.escolhas().get(0).messageDto().conteudo();
    }

}
