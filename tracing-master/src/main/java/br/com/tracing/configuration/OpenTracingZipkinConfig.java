package br.com.tracing.configuration;

import brave.Tracing;
import brave.baggage.BaggagePropagation;
import brave.opentracing.BraveTracer;
import brave.propagation.B3Propagation;
import brave.propagation.Propagation.Factory;
import io.opentracing.Tracer;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

public final class OpenTracingZipkinConfig {

	private final static String END_POINT = "http://127.0.0.1:9411/api/v2/spans";

	public Tracer build(String localServiceName) {
		OkHttpSender okHttpSender = OkHttpSender.create(END_POINT);
		AsyncZipkinSpanHandler zipkinSpanHandler = AsyncZipkinSpanHandler.create(okHttpSender);
		Factory factory = BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY).build();
		Tracing tracing = Tracing.newBuilder().localServiceName(localServiceName).addSpanHandler(zipkinSpanHandler)
				.propagationFactory(factory).build();
		return BraveTracer.create(tracing);
	}

}
