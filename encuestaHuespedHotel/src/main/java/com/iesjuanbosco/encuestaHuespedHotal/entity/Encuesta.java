package com.iesjuanbosco.encuestaHuespedHotal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidades")
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre no debe estar vacío")
    @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres")
    private String nombre;
    @NotBlank(message = "El nombre no debe estar vacío")
    @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres")
    private String apellidos;
    @Email(message = "El correo electrónico no es válido")
    private String email;
    @Min(value = 18, message = "Debe ser mayor de edad")
    private int edad;
    @NotBlank(message = "El campo no puede estar vacio" )
    private String telefono;
    @PastOrPresent
    @NotNull(message = "Debe marcar una fecha")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;
    @NotBlank(message = "Debes seleccionar una opción válida")
    private String motivo;
    private List<String> servicios;
    //Servicios
    private Boolean restauranteS;
    private Boolean gimnasioS;
    private Boolean spaS;
    private Boolean piscinaS;
    private Boolean roomServiceS;
    @NotNull(message = "Debes seleccionar una opción válida")
    private String nivelSatisfaccion;
    private String otrosComentarios;

    public Encuesta() {
    }

    public Encuesta(Long id, String nombre, String apellidos, String email, int edad, String telefono, LocalDate fechaInicio, String motivo, List<String> servicios, Boolean restauranteS, Boolean gimnasioS, Boolean spaS, Boolean piscinaS, Boolean roomService, String nivelSatisfaccion, String otrosComentarios) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.edad = edad;
        this.telefono = telefono;
        this.fechaInicio = fechaInicio;
        this.motivo = motivo;
        this.servicios = servicios != null ? servicios : new ArrayList<>();;
        this.restauranteS = restauranteS;
        this.gimnasioS = gimnasioS;
        this.spaS = spaS;
        this.piscinaS = piscinaS;
        this.roomServiceS = roomService;
        this.nivelSatisfaccion = nivelSatisfaccion;
        this.otrosComentarios = otrosComentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre no debe estar vacío") @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre no debe estar vacío") @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El nombre no debe estar vacío") @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres") String getApellidos() {
        return apellidos;
    }

    public void setApellidos(@NotBlank(message = "El nombre no debe estar vacío") @Pattern(regexp = "\\S{2,}", message = "El nombre no debe contener espacios y debe componerse de al menos dos caracteres") String apellidos) {
        this.apellidos = apellidos;
    }

    public @Email(message = "El correo electrónico no es válido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "El correo electrónico no es válido") String email) {
        this.email = email;
    }

    @Min(value = 18, message = "Debe ser mayor de edad")
    public int getEdad() {
        return edad;
    }

    public void setEdad(@Min(value = 18, message = "Debe ser mayor de edad") int edad) {
        this.edad = edad;
    }

    public @NotBlank String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank String telefono) {
        this.telefono = telefono;
    }

    public @PastOrPresent @NotNull LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(@PastOrPresent @NotNull LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public @NotNull(message = "Debes seleccionar una opción válida") String getMotivo() {
        return motivo;
    }

    public void setMotivo(@NotNull(message = "Debes seleccionar una opción válida") String motivo) {
        this.motivo = motivo;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public @NotNull(message = "Debes seleccionar una opción válida") String getNivelSatisfaccion() {
        return nivelSatisfaccion;
    }

    public void setNivelSatisfaccion(@NotNull(message = "Debes seleccionar una opción válida") String nivelSatisfaccion) {
        this.nivelSatisfaccion = nivelSatisfaccion;
    }

    public String getOtrosComentarios() {
        return otrosComentarios;
    }

    public void setOtrosComentarios(String otrosComentarios) {
        this.otrosComentarios = otrosComentarios;
    }

    public Boolean getRestauranteS() {
        return restauranteS;
    }

    public void setRestauranteS(Boolean restauranteS) {
        this.restauranteS = restauranteS;
    }

    public Boolean getGimnasioS() {
        return gimnasioS;
    }

    public void setGimnasioS(Boolean gimnasioS) {
        this.gimnasioS = gimnasioS;
    }

    public Boolean getSpaS() {
        return spaS;
    }

    public void setSpaS(Boolean spaS) {
        this.spaS = spaS;
    }

    public Boolean getPiscinaS() {
        return piscinaS;
    }

    public void setPiscinaS(Boolean piscinaS) {
        this.piscinaS = piscinaS;
    }

    public Boolean getRoomService() {
        return roomServiceS;
    }

    public void setRoomService(Boolean roomService) {
        this.roomServiceS = roomService;
    }

    @Override

    public String toString() {
        return "Encuesta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", motivo='" + motivo + '\'' +
                ", servicios=" + servicios +
                ", nivelSatisfaccion='" + nivelSatisfaccion + '\'' +
                ", otrosComentarios='" + otrosComentarios + '\'' +
                '}';
    }
}
