package no.spk.felles.remoting;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;


public class RemotingMockServerImpl implements RemotingMockServer, InitializingBean {

    private HttpServer server;
    private List<RemoteContext> contexts;

    public RemotingMockServerImpl(Integer port) {
        this(port, Collections.emptyList());
    }

    public RemotingMockServerImpl(Integer port, List<RemoteContext> contexts) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/index", exchange -> {
                String response = "<h3>Server start success if you see this message</h3>" + "<h5>Port: " + port + "</h5>";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseHeaders().add("Context-Type", "application/html");
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });
        } catch (IOException e) {
            throw new RuntimeException("Could not start server on port="+port, e);
        }

        this.contexts = contexts;
        for (RemoteContext c : this.contexts) {
            HttpContext context = server.createContext(c.getPath(), exchange -> {
                exchange.sendResponseHeaders(200, 0);
                RemoteInvocationResult result = new RemoteInvocationResult(c.getResponse());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(exchange.getResponseBody());
                objectOutputStream.writeObject(result);
                objectOutputStream.close();
            });
            System.out.println("Registered ResponseContext: " + c.toString() + ", HttpContext: " + context.getPath());
        }
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop(0);
    }

    public void setContexts(List<RemoteContext> contexts) {
        this.contexts = contexts;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        server.start();
    }
}
