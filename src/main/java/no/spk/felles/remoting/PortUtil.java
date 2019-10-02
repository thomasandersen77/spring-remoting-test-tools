package no.spk.felles.remoting;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtil {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static Integer getPort() {
        return getPort(false);
    }

    public static Integer getPort(boolean reset) {
        try {
            if (threadLocal.get() == null || reset) {
                final ServerSocket serverSocket = new ServerSocket(0);
                threadLocal.set(serverSocket.getLocalPort());
                serverSocket.close();
                return threadLocal.get();
            }
            else
                return threadLocal.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
