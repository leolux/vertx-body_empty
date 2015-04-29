package com.reproducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Client extends AbstractVerticle {

  public void start() {
    HttpClientOptions options = new HttpClientOptions();
    HttpClient client = vertx.createHttpClient(options);
    try {
      HttpClientRequest request =
          client.request(HttpMethod.POST, 8899, "localhost", "/some/resource", resp -> {
            System.out.println("Client send completed");
          });

      String data = "{\"data\":[\"whatever\"]}";
      // request.putHeader("Content-Type", "application/json");
      String contentLength = null;
      try {
        contentLength = String.valueOf(data.getBytes("UTF-8").length);
      } catch (Exception e) {
        // suck
      }
      request.putHeader("Content-Length", contentLength);
      request.setTimeout(TimeUnit.SECONDS.toMillis(5));
      request.write(data, "UTF-8");
      request.end();
    } finally {
      vertx.setTimer(2500, l -> {
        client.close();
      });
    }

  }
}
