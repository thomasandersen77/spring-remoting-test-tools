package no.spk.felles.remoting;

public class RemoteContextImpl implements RemoteContext {
    final private String path;
    final private Object response;

    public RemoteContextImpl(String path, Object response) {
        this.path = path;
        this.response = response;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "RemoteContextImpl{" +
                "path='" + path + '\'' +
                ", response=" + response +
                '}';
    }
}
