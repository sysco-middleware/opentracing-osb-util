package no.sysco.middleware.opentracing.osb;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import java.util.Map;

public class Tracing {

  public static void start(String serviceName, String operationName, Map<String, String> headers) {
    tracer(serviceName).buildSpan(operationName).startActive();
    for(Map.Entry<String, String> entry : headers.entrySet()){
      System.out.print(entry.getKey() + ": " + entry.getValue());
    }
  }

  public static void close(String serviceName) {
    tracer(serviceName).activeSpan().close();
  }

  public static Tracer tracer(String componentName) {
    Tracer tracer = GlobalTracer.get();
    if (tracer == null) {
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
      return GlobalTracer.get();
    }
    return tracer;
  }
}
