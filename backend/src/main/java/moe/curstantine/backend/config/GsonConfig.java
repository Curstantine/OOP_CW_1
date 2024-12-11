package moe.curstantine.backend.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class GsonConfig {
	@Bean
	public Gson gson() {
		return new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
				.create();
	}

	private static class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
		private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		@Override
		public void write(JsonWriter out, LocalDateTime value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(formatter.format(value));
			}
		}

		@Override
		public LocalDateTime read(JsonReader in) throws IOException {
			String dateTimeString = in.nextString();
			return LocalDateTime.parse(dateTimeString, formatter);
		}
	}
}
