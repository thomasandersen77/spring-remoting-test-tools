package no.spk.felles.remoting;

import java.io.IOException;
import java.net.ServerSocket;

public class RandomPortUtil {
    public static Integer getPort() {
        try {
            return new ServerSocket(0).getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
