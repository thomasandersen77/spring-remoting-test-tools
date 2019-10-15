package no.spk.felles.remoting;

public interface RemoteService<T> {
    T getEntity() throws IllegalArgumentException;
}
