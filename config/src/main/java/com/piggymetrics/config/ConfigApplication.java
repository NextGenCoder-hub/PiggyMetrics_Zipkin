package com.piggymetrics.config;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.okhttp3.OkHttpSender;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}


	@Bean
	public io.opentracing.Tracer zipkinTracer() {

		OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
		AsyncReporter<Span> reporter = AsyncReporter.builder(sender).build();
		Tracing tracer = Tracing.newBuilder().localServiceName("zipkin-client-MySpringBoot123").reporter(reporter).build();
		return BraveTracer.create(tracer);
	}
}
