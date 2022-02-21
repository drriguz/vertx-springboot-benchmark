package com.riguz.frameworks.vertx;

import com.riguz.frameworks.model.AppInfo;
import com.riguz.frameworks.model.AppNotFoundException;
import com.riguz.frameworks.model.InvalidRequestException;
import com.riguz.frameworks.model.Service;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {
    private Service service = new Service();

    private Handler<RoutingContext> errorHandler = ctx -> {
        Throwable exception = ctx.failure();
        if (exception instanceof AppNotFoundException) {
            ctx.response().setStatusCode(404).end(exception.getMessage());
        } else if (exception instanceof InvalidRequestException) {
            ctx.response().setStatusCode(400).end(exception.getMessage());
        } else {
            ctx.fail(500, exception);
        }
    };

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.get("/api/v1/:app")
                .handler(context -> {
                    String app = context.pathParam("app");

                    AppInfo info = service.getAppInfo(app);
                    context.json(info);
                }).failureHandler(errorHandler);

        router.get("/api/v2/:app")
                .handler(context -> {
                    String app = context.pathParam("app");

                    AppInfo info = service.getAppInfo(app);
                    context.response().putHeader("Signature", service.getSign(info));
                    context.json(info);
                }).failureHandler(errorHandler);

        router.post("/api/v1/:app")
                .handler(BodyHandler.create())
                .handler(context -> {
                    String app = context.pathParam("app");
                    JsonObject body = context.getBodyAsJson();
                    AppInfo info = service.getAppInfo(app);
                    context.response().putHeader("nonce", body.getString("nonce"));
                    context.json(info);
                }).failureHandler(errorHandler);
        router.post("/api/v2/:app")
                .handler(BodyHandler.create())
                .handler(context -> {
                    String app = context.pathParam("app");
                    JsonObject body = context.getBodyAsJson();
                    AppInfo info = service.getAppInfo(app);
                    context.response().putHeader("Signature", service.getSign(info, body.getString("nonce")));
                    context.json(info);
                }).failureHandler(errorHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080)
                .onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }
}
