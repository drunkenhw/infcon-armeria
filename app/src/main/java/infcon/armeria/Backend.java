package infcon.armeria;

import java.time.Duration;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

public final class Backend {

    public static Backend of(String name, int port){
        return new Backend(name, port);
    }

    private final Server server;

    private Backend(String name, int port) {
        server = Server.builder()
              .http(port)
              .service('/'+ name,(ctx, req) -> {
                  final HttpResponse res = HttpResponse.of("response from : " + name);
                  return HttpResponse.delayed(res, Duration.ofSeconds(3));
               })
                .build();
    }
    public void start(){
        server.start().join();
    }
}
