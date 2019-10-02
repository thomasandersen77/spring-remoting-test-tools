package no.spk.felles.remoting;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteInvocationResult;


public class RemotingMockServerImpl implements RemotingMockServer, InitializingBean, DisposableBean {

    private HttpServer server;
    private List<RemoteContext> contexts;
    private int port;

    public RemotingMockServerImpl(List<RemoteContext> contexts) {
        port = PortUtil.getPort(true);

        try {
            long start = System.currentTimeMillis();
            while (server == null) {
                try {
                    server = HttpServer.create(new InetSocketAddress(port), 0);
                } catch (BindException e) {
                    long time = System.currentTimeMillis() - start;
                    if(time > 500){
                        port = PortUtil.getPort(true);
                        start = System.currentTimeMillis();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/index", exchange -> {
            String response = "<h3>Server start success if you see this message</h3>" + "<h5>Port: " + port + "</h5>";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseHeaders().add("Context-Type", "application/html");
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        this.contexts = contexts;
        for (RemoteContext c : this.contexts) {
            HttpContext context = server.createContext(c.getPath(), exchange -> {
                if(! (c.getResponse() instanceof Serializable))
                    throw new RuntimeException("Respons object must implement java.io.Serializable: " + c.getClass());

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
        System.out.println("Start server...");
        server.start();
    }

    @Override
    public void stop() {
        System.out.println("Stopping server...");
        server.stop(0);
    }

    public void setContexts(List<RemoteContext> contexts) {
        this.contexts = contexts;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }

    public int getPort() {
        return port;
    }
}
