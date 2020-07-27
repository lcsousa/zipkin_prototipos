package br.com.tracing.appender;

import br.com.tracing.util.OpenTracingZipkinUtil;
import brave.Span;
import brave.Tracing;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public final class OpenTracingZipkinAppender extends AppenderBase<ILoggingEvent> {

	@Override
	protected void append(ILoggingEvent eventObject) {
		if (Level.ERROR.equals(eventObject.getLevel())) {
			Span span = Tracing.currentTracer().currentSpan();
			span.tag(eventObject.getLevel().levelStr + " - " + OpenTracingZipkinUtil.getDateTimeFormatter(),
					eventObject.getFormattedMessage());
			span.finish();
		}
	}

}
