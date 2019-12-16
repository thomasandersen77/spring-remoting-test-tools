package no.spk.felles.remoting;

public interface RemoteClient<T> {
    T getEntity() throws IllegalArgumentException;
}
