package com.coop8.demojwt.Models;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DatosAuditoria {

    @Column(name = "usuariosys", nullable = false, length = 50)
    private String usuariosys;

    @Column(name = "fechasys", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechasys;

    // Constructor sin parámetros
    public DatosAuditoria() {
        this.fechasys = new Date();
    }

    // Constructor con parámetros
    public DatosAuditoria(String usuariosys) {
        this.usuariosys = usuariosys;
        this.fechasys = new Date();
    }

    @PrePersist
    protected void onCreate() {
        this.fechasys = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechasys = new Date();
    }
}
