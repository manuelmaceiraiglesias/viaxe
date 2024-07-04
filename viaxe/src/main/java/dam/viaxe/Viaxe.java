package dam.viaxe;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="viajes")
public class Viaxe {
    @Id
    private LocalDate fecha;
    private double distancia;
    @Column(columnDefinition = "smallint()")
    private int desnivel;
    private String comentario;

    public Viaxe() {
    }

    public Viaxe(double distancia, int desnivel, String comentario) {
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.comentario = comentario;
    }

    public Viaxe(LocalDate fecha, double distancia, int desnivel, String comentario) {
        this.fecha = fecha;
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(int desnivel) {
        this.desnivel = desnivel;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viaxe viaxe = (Viaxe) o;
        return Objects.equals(fecha, viaxe.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha);
    }

    @Override
    public String toString() {
        return "Viaxe{" +
                "fecha=" + fecha +
                ", distancia=" + distancia +
                ", desnivel=" + desnivel +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
