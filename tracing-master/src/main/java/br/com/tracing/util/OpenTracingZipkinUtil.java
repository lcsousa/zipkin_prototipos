package br.com.tracing.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import brave.Tracing;

public final class OpenTracingZipkinUtil {

	public static void generateTracing(String key, String value) {
		
		//System.out.println("-------------------------------->" +key + " - " + getDateTimeFormatter() + "---->"+value);
		/*
		 * if(value.toUpperCase().contains("\"cpf\":\"00200200202\"")) {
		 * System.out.println("->>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); }
		 * if(key.toUpperCase().contains("REQUEST")) { System.out.println(key + " - " +
		 * getDateTimeFormatter() + "---->"+value); value+="ssss"; }
		 */
		Tracing.currentTracer().currentSpan().tag(key, value);
	//	Tracing.currentTracer().currentSpan().tag(key + " - " + getDateTimeFormatter(), value);
	}

	public static String getDateTimeFormatter() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
				.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

		return dateTimeFormatter.format(Instant.now());
	}

}
