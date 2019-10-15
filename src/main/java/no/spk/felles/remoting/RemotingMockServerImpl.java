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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteInvocationResult;


public class RemotingMockServerImpl implements RemotingMockServer, InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(RemotingMockServerImpl.class);
    private HttpServer server;
    private int port;

    public RemotingMockServerImpl() {
        this.createServerInstance();
    }

    public RemotingMockServerImpl(List<RemoteContext> contexts) {


        this.createServerInstance();
        this.createServerContexts(contexts);
    }

    private void createServerContexts(List<RemoteContext> contexts) {
        server.createContext("/ping", exchange -> {
            String response = "pong";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseHeaders().add("Context-Type", "text/plain");
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        for (RemoteContext c : contexts) {
            HttpContext context = server.createContext(c.getPath(), exchange -> {
                if(! (c.getResponse() instanceof Serializable))
                    throw new RuntimeException("Respons object must implement java.io.Serializable: " + c.getClass());

                if(c.getResponse() instanceof Throwable)
                    exchange.sendResponseHeaders(500, 0);
                else
                    exchange.sendResponseHeaders(200, 0);

                RemoteInvocationResult result = new RemoteInvocationResult(c.getResponse());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(exchange.getResponseBody());
                objectOutputStream.writeObject(result);
                objectOutputStream.close();
            });
            log.info("Registered ResponseContext: {}, HttpContext: {}", c.toString(), context.getPath());
        }
    }

    void createServerInstance() {
        try {
            this.port = PortUtil.getPort(true);
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
    }

    @Override
    public void start() {
        log.info("Start server on port=["+getPort()+"]...");
        server.start();
    }

    @Override
    public void stop() {
        log.info("Stopping server...");
        server.stop(0);
    }

    public void setContexts(List<RemoteContext> contexts) {
        this.createServerContexts(contexts);
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
