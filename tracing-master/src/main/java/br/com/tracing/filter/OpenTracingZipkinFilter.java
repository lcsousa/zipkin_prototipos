package br.com.tracing.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import br.com.tracing.util.OpenTracingZipkinUtil;

public final class OpenTracingZipkinFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(OpenTracingZipkinFilter.class);

	private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
			MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.valueOf("application/*+json"), MediaType.valueOf("application/*+xml"),
			MediaType.MULTIPART_FORM_DATA);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isAsyncDispatch(request)) {
			filterChain.doFilter(request, response);
		} else {
			doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
		}
	}

	protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			beforeRequest(request, response);
			filterChain.doFilter(request, response);
		} finally {
			afterRequest(request, response);
			response.copyBodyToResponse();
		}
	}

	protected void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		if (LOG.isInfoEnabled()) {
			logRequestHeader(request);
		}
	}

	protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		if (LOG.isInfoEnabled()) {
			logRequestBody(request);
			logResponse(response);
		}
	}

	private static void logRequestHeader(ContentCachingRequestWrapper request) {
		/*String queryString = request.getQueryString();
			
		 * if (queryString == null) { // LOG.info("{} {} {}", prefix,
		 * request.getMethod(), request.getRequestURI());
		 * OpenTracingZipkinUtil.generateTracing("REQUEST", prefix + " " +
		 * request.getMethod() + " " + request.getRequestURI()); } else { //
		 * LOG.info("{} {} {}?{}", prefix, request.getMethod(), //
		 * request.getRequestURI(), queryString);
		 * OpenTracingZipkinUtil.generateTracing("REQUEST", prefix + " " +
		 * request.getMethod() + " " + request.getRequestURI() + " " + queryString); }
		 */
		StringBuilder sb = new StringBuilder("");
		
		
		Collections.list(request.getHeaderNames())
				.forEach(headerName -> Collections.list(request.getHeaders(headerName)).forEach(headerValue -> {
					// LOG.info("{} {}: {}", prefix, headerName, headerValue);]
					sb.append(headerName +" -> "+headerValue);
					
				}));
		OpenTracingZipkinUtil.generateTracing("HEADER",  sb.toString());
		// LOG.info("{}", prefix);
		//OpenTracingZipkinUtil.generateTracing("REQUEST", prefix);
	}

	private static void logRequestBody(ContentCachingRequestWrapper request) {
		byte[] content = request.getContentAsByteArray();
		if (content.length > 0) {
			logContentRequest(content, request.getContentType(), request.getCharacterEncoding());
		}
	}

	private static void logResponse(ContentCachingResponseWrapper response) {
		// int status = response.getStatus();
		// LOG.info("{} {} {}", prefix, status,
		// HttpStatus.valueOf(status).getReasonPhrase());
		// response.getHeaderNames().forEach(headerName ->
		// response.getHeaders(headerName).forEach(headerValue -> LOG.info("{} {}: {}",
		// prefix, headerName, headerValue)));
		// LOG.info("{}", prefix);
		byte[] content = response.getContentAsByteArray();
		if (content.length > 0) {
			logContent(content, response.getContentType(), response.getCharacterEncoding());
		}
	}

	private static void logContent(byte[] content, String contentType, String contentEncoding) {
		MediaType mediaType = MediaType.valueOf(contentType);
		boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
		if (visible) {
			try {
				String contentString = new String(content, contentEncoding);
				/*
				 * Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> {
				 * LOG.info("{} {}", prefix, line);
				 * OpenTracingZipkinUtil.generateResponseTracing(line); });
				 */
				Iterator<String> iterator = Stream.of(contentString.split("\r\n|\r|\n")).iterator();
				while (iterator.hasNext()) {
					String line = iterator.next();
					// LOG.info("{} {}", prefix, line);
					OpenTracingZipkinUtil.generateTracing("RESPONSE",line);
				}
			} catch (UnsupportedEncodingException e) {
				LOG.error(" [{} bytes content]", content.length);
			}
		} else {
			// LOG.info("{} [{} bytes content]", prefix, content.length);
			OpenTracingZipkinUtil.generateTracing("RESPONSE",
					 " " + content.length);
		}
	}
	
	private static void logContentRequest(byte[] content, String contentType, String contentEncoding) {
		MediaType mediaType = MediaType.valueOf(contentType);
		boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
		if (visible) {
			try {
				String contentString = new String(content, contentEncoding);
				/*
				 * Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> {
				 * LOG.info("{} {}", prefix, line);
				 * OpenTracingZipkinUtil.generateResponseTracing(line); });
				 */
				Iterator<String> iterator = Stream.of(contentString.split("\r\n|\r|\n")).iterator();
				StringBuilder sb = new StringBuilder("");
				while (iterator.hasNext()) {
					String line = iterator.next();
					// LOG.info("{} {}", prefix, line);
					sb.append(line);
					
				}
				OpenTracingZipkinUtil.generateTracing("REQUEST",sb.toString());
			} catch (UnsupportedEncodingException e) {
				LOG.error("{} [{} bytes content]", content.length);
			}
		} else {
			// LOG.info("{} [{} bytes content]", prefix, content.length);
			OpenTracingZipkinUtil.generateTracing("REQUEST",
					" "+ content.length);
		}
	}

	private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			return (ContentCachingRequestWrapper) request;
		} else {
			return new ContentCachingRequestWrapper(request);
		}
	}

	private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}

}
