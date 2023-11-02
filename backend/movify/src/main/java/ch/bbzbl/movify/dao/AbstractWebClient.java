package ch.bbzbl.movify.dao;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

@Component
public abstract class AbstractWebClient {

	@Autowired
	private HttpClient nettyHttpClient;

	private WebClient webClient = null;

	@PostConstruct
	public void initWebClient() {
		this.webClient = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(nettyHttpClient))
				.codecs(configurer -> configurer
						.defaultCodecs()
						.maxInMemorySize(16 * 1024 * 1024))
				.baseUrl(baseUrl())
				.defaultHeaders(header -> header.setContentType(MediaType.APPLICATION_JSON))
				.defaultHeaders(header -> header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8)))
				.build();
	}

	public Optional<String> doGet(String path, MultiValueMap<String, String> params) {
		return callWebClient(getWebClient().get(), path, params);
	}

	public Optional<String> callWebClient(WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec, String path, MultiValueMap<String, String> params) {
		String responseAsString = requestHeadersUriSpec
				.uri(uriBuilder -> uriBuilder.path(path)
						.queryParams(params)
						.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();

		if (StringUtils.isNotBlank(responseAsString)) {
			return Optional.of(responseAsString);
		}
		return Optional.empty();
	}

	protected abstract String baseUrl();

	protected WebClient getWebClient() {
		return webClient;
	}
}
