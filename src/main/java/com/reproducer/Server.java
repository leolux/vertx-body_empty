package com.reproducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.apex.Router;
import io.vertx.ext.apex.handler.BodyHandler;

/**
 *
 */
public class Server extends AbstractVerticle {

  public void start() {
    HttpServerOptions options = new HttpServerOptions();
    options.setPort(8899);
    HttpServer server = vertx.createHttpServer(options);

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create()).method(HttpMethod.POST).path("/some/resource")
        .handler(routingContext -> {
          try {

            JsonObject body = routingContext.getBodyAsJson();
            if (body == null || body.isEmpty()) {
              System.out.println("!!! Body is empty !!!");
            } else {
              System.out.println("BodyHandler works as expected");
            }
          } finally {
            routingContext.response().end();
          }
        });
    server.requestHandler(router::accept).listen();
  }
}
