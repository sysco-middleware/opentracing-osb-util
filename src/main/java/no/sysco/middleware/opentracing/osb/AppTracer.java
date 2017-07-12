package no.sysco.middleware.opentracing.osb;

import io.opentracing.util.GlobalTracer;

/**
 *
 */
public class AppTracer {
  public static void configure(String componentName) {
    if (GlobalTracer.get() == null) {
      GlobalTracer.register(
          new com.uber.jaeger.Configuration(
              componentName,
              new com.uber.jaeger.Configuration.SamplerConfiguration("const", 1),
              new com.uber.jaeger.Configuration.ReporterConfiguration(
                  true,  // logSpans
                  "docker-vm",
                  5775,
                  1000,   // flush interval in milliseconds
                  10000)  /*max buffered Spans*/)
              .getTracer());
    }
  }
}
