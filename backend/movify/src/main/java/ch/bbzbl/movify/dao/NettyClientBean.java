package ch.bbzbl.movify.dao;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class NettyClientBean {

	@Bean
	public HttpClient nettyHttpClient() {
		return HttpClient.create()
				.responseTimeout(Duration.ofMillis(800000))
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 800000);
	}
}
