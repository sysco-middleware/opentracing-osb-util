package no.sysco.middleware.opentracing.osb;

import io.opentracing.Span;
import io.opentracing.tag.Tags;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class SpanDecorator {

  /**
   * Called before record is sent by producer
   */
  public static <K, V> void onSend(String componentName, Span span) {
    Tags.COMPONENT.set(span, componentName);
    Tags.MESSAGE_BUS_DESTINATION.set(span, ""); //TODO
  }

  /**
   * Called when record is received in consumer
   */
  public static void onResponse(String componentName, Span span) {
    Tags.COMPONENT.set(span, componentName);
  }

  public static void onError(Exception exception, Span span) {
    Tags.ERROR.set(span, Boolean.TRUE);
    span.log(errorLogs(exception));
  }

  private static Map<String, Object> errorLogs(Throwable throwable) {
    Map<String, Object> errorLogs = new HashMap<>(4);
    errorLogs.put("event", Tags.ERROR.getKey());
    errorLogs.put("error.kind", throwable.getClass().getName());
    errorLogs.put("error.object", throwable);

    errorLogs.put("message", throwable.getMessage());

    StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw));
    errorLogs.put("stack", sw.toString());

    return errorLogs;
  }
}
