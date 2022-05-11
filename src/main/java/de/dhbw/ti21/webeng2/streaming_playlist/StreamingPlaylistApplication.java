package de.dhbw.ti21.webeng2.streaming_playlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class StreamingPlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingPlaylistApplication.class, args);
	}

}
