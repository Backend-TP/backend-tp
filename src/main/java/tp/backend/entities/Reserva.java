package tp.backend.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad que representa una Reserva de camión.
 * Una reserva está asociada a un camión específico y contiene información de la operación de flete.
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "volumen_requerido", nullable = false)
    private Double volumenRequerido; // en metros cúbicos (m³)

    @Column(name = "origen", nullable = false, length = 255)
    private String origen;

    @Column(name = "destino", nullable = false, length = 255)
    private String destino;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // CONFIRMADA, CANCELADA

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;

    // ==================== CONSTRUCTORES ====================

    public Reserva() {
    }

    public Reserva(LocalDateTime fechaInicio, LocalDateTime fechaFin, Double volumenRequerido,
                   String origen, String destino, Camion camion) {
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setVolumenRequerido(volumenRequerido);
        this.origen = origen;
        this.destino = destino;
        this.camion = camion;
        this.estado = "CONFIRMADA";
        this.fechaCreacion = LocalDateTime.now();
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser nula");
        }
        if (this.fechaInicio != null && fechaFin.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        this.fechaFin = fechaFin;
    }

    public Double getVolumenRequerido() {
        return volumenRequerido;
    }

    public void setVolumenRequerido(Double volumenRequerido) {
        if (volumenRequerido == null || volumenRequerido <= 0) {
            throw new IllegalArgumentException("El volumen requerido debe ser mayor a 0");
        }
        this.volumenRequerido = volumenRequerido;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        if (origen == null || origen.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen no puede ser nulo o vacío");
        }
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede ser nulo o vacío");
        }
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", volumenRequerido=" + volumenRequerido +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", camionId=" + (camion != null ? camion.getId() : null) +
                '}';
    }
}
