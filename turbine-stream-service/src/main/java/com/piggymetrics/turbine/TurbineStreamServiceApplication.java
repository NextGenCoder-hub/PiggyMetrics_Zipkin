package com.piggymetrics.turbine;


import brave.Tracing;
import brave.opentracing.BraveTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;
import org.springframework.context.annotation.Bean;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.okhttp3.OkHttpSender;


@SpringBootApplication
@EnableTurbineStream
@EnableDiscoveryClient
public class TurbineStreamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineStreamServiceApplication.class, args);
	}

	@Bean
	public io.opentracing.Tracer zipkinTracer() {

		OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
		AsyncReporter<Span> reporter = AsyncReporter.builder(sender).build();
		Tracing tracer = Tracing.newBuilder().localServiceName("zipkin-client-MySpringBoot123").reporter(reporter).build();
		return BraveTracer.create(tracer);
	}


}