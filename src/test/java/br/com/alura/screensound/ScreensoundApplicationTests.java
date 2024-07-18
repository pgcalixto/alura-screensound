package br.com.alura.screensound;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.alura.screensound.service.ScreensoundRunner;

@SpringBootTest
class ScreensoundApplicationTests {

	@MockBean
	private ScreensoundRunner screensoundRunner;

	@Test
	void contextLoads() {
	}

}
