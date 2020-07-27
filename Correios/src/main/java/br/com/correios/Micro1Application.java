package br.com.correios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.com.tracing.configuration.OpenTracingZipkinConfig;
import br.com.tracing.filter.OpenTracingZipkinFilter;
import io.opentracing.Tracer;

@SpringBootApplication
public class Micro1Application {

	@Value("${spring.application.name}")
	private String localServiceName;

	public static void main(String[] args) {
		SpringApplication.run(Micro1Application.class, args);
	}

	@Bean
	public Tracer tracer() {
		return new OpenTracingZipkinConfig().build(localServiceName);
	}

	@Bean
	public FilterRegistrationBean<OpenTracingZipkinFilter> filterRegistrationBean() {
		FilterRegistrationBean<OpenTracingZipkinFilter> filterRegistrationBean = new FilterRegistrationBean<OpenTracingZipkinFilter>();
		filterRegistrationBean.setFilter(new OpenTracingZipkinFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

}
