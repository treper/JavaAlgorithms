package ataosky.example.schedule;

public class HttpProxy {
    private Integer id;
    private String host;
    private Integer port;

    public HttpProxy(String host) {
        this.host = host;
    }

    public HttpProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public HttpProxy(Integer id, String host, Integer port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public Integer getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "HttpProxy{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}