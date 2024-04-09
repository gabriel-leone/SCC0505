import java.util.List;

public class Estado {
    public String estado;

    public List<Transicao> transicaoList;

    @Override
    public String toString() {
        return "Estado{" +
                "estado='" + estado + '\'' +
                '}';
    }
}
