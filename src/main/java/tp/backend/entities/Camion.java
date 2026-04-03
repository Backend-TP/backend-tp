package tp.backend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa un Camión en el sistema.
 * Cada camión tiene una capacidad máxima de volumen y puede estar asociado a múltiples reservas.
 */
@Entity
@Table(name = "camiones")
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patente", nullable = false, unique = true, length = 10)
    private String patente;

    @Column(name = "marca", length = 100)
    private String marca;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "capacidad_volumen", nullable = false)
    private Double capacidadVolumen; // en metros cúbicos (m³)

    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // ACTIVO, INACTIVO

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @OneToMany(mappedBy = "camion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    // ==================== CONSTRUCTORES ====================

    public Camion() {
    }

    public Camion(String patente, String marca, String modelo, Integer anio, Double capacidadVolumen) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.capacidadVolumen = capacidadVolumen;
        this.estado = "ACTIVO";
        this.fechaAlta = LocalDateTime.now();
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede ser nula o vacía");
        }
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getCapacidadVolumen() {
        return capacidadVolumen;
    }

    public void setCapacidadVolumen(Double capacidadVolumen) {
        if (capacidadVolumen == null || capacidadVolumen <= 0) {
            throw new IllegalArgumentException("La capacidad de volumen debe ser mayor a 0");
        }
        this.capacidadVolumen = capacidadVolumen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    // ==================== MÉTODOS DE UTILIDAD ====================

    /**
     * Verifica si el camión está disponible para una fecha específica con un volumen requerido.
     *
     * @param fechaInicio Fecha de inicio de la reserva
     * @param fechaFin Fecha de fin de la reserva
     * @param volumenRequerido Volumen requerido en m³
     * @return true si el camión está disponible, false en caso contrario
     */
    public boolean estaDisponible(LocalDateTime fechaInicio, LocalDateTime fechaFin, Double volumenRequerido) {
        // Verificar si el volumen requerido no excede la capacidad
        if (volumenRequerido > this.capacidadVolumen) {
            return false;
        }

        // Verificar si el estado es ACTIVO
        if (!"ACTIVO".equals(this.estado)) {
            return false;
        }

        // Verificar si hay solapamiento de fechas con reservas confirmadas
        for (Reserva reserva : this.reservas) {
            if ("CONFIRMADA".equals(reserva.getEstado())) {
                // Verificar si hay solapamiento
                if (!(fechaFin.isBefore(reserva.getFechaInicio()) || fechaInicio.isAfter(reserva.getFechaFin()))) {
                    return false; // Hay solapamiento
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "id=" + id +
                ", patente='" + patente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anio=" + anio +
                ", capacidadVolumen=" + capacidadVolumen +
                ", estado='" + estado + '\'' +
                ", fechaAlta=" + fechaAlta +
                '}';
    }
}
