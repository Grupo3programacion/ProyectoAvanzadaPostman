package co.edu.uniquindio.proyectoAvanzada.modelo.enums;

public enum TipoPqrs {

    QUEJA("QUEJA"),
    RECLAMO("RECLAMO"),
    SUGERENCIA("SUGERENCIA"),
    PETICION("PETICION");

    private String nombre;
    TipoPqrs(String nombre){
        this.nombre = nombre;

    }
}
