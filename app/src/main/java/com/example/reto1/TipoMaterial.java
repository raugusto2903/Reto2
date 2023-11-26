package com.example.reto1;

import java.util.ArrayList;
import java.util.List;
public class TipoMaterial {
    private String nombreTipo;
    public List<MesCantidadValor> listaMesesCantidad;
    private boolean activo;

    public TipoMaterial(String nombreTipo) {
        this.nombreTipo = nombreTipo;
        this.activo=false;
        listaMesesCantidad = new ArrayList<>();
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
