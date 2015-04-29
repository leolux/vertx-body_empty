package com.reproducer;

import io.vertx.core.Vertx;

public class Main {


  public static void main(String[] args) {
    // Normal mode
     Vertx vertx = Vertx.vertx();
     deployVerticles(vertx);

    // Cluster mode
//    VertxOptions options = new VertxOptions();
//    options.setClustered(true);
//    options.setClusterManager(new HazelcastClusterManager());
//    Vertx.clusteredVertx(options, vertxHandler -> {
//      if (vertxHandler.succeeded()) {
//        Vertx vertx = vertxHandler.result();
//        deployVerticles(vertx);
//      }
//    });
  }

  private static void deployVerticles(Vertx vertx) {
    vertx.deployVerticle(new Server());
    vertx.deployVerticle(new Client());
  }
}
