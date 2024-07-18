package br.com.alura.screensound.service;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Executor do menu de opções para o usuário.
 */
@Component
public class ScreensoundRunner implements CommandLineRunner {

  @Autowired
  private ScreensoundService screensoundService;

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public void run(String... args) throws Exception {

    int opcao = -1;

    final String menu = """
        Menu:
        1 - Cadastrar artista
        2 - Listar artistas
        3 - Buscar artista
        4 - Saber mais sobre artista
        5 - Cadastrar música
        6 - Listar músicas
        0 - Sair""";

    while (opcao != 0) {

      System.out.println(menu);

      System.out.print("Escolha uma opção: ");

      opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao) {
        case 0 -> System.out.println("Saindo...");

        case 1 -> screensoundService.cadastrarArtista(scanner);

        case 2 -> screensoundService.listarArtistas();

        case 3 -> screensoundService.buscarArtista(scanner);

        case 4 -> screensoundService.saberMaisSobreArtista(scanner);

        case 5 -> screensoundService.cadastrarMusica(scanner);

        case 6 -> screensoundService.listarMusicas();

        default -> System.out.println("Opção inválida!");
      }
    }

    scanner.close();
  }
}
