package infcon.armeria;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpResponse;

class BackendTest {

    @Test
    void backend() throws InterruptedException {
        final Backend foo = Backend.of("foo", 9000);
        foo.start();

        final WebClient webClient = WebClient.of("http://127.0.0.1:9000");

        //3초 뒤에 리턴해도 비동기서버이기에 응답을 갖고있지 않다.
        final HttpResponse httpResponse = webClient.get("/foo");
        final CompletableFuture<AggregatedHttpResponse> future = httpResponse.aggregate();
        //call back
        future.thenAccept(aggregatedHttpResponse -> {
            //event loop 가 받음.
            System.err.println("In callback. Thread Name: "+ Thread.currentThread().getName());
            sendBackToTheOriginalClient(aggregatedHttpResponse);
        });

        Thread.sleep(Long.MAX_VALUE);



    }

    private void sendBackToTheOriginalClient(AggregatedHttpResponse aggregatedHttpResponse) {
    }
}