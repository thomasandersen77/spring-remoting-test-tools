package no.spk.felles.remoting;

public interface RemotingMockServer {
    void start();
    void stop();
    int getPort();
}
