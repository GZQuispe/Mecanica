package com.TPFinal.Model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RepeOrden {
	
	@Id
	@GeneratedValue
	private long idRepeOrden;
	
	private float horas;
	private int cantidad;
	
	@ManyToOne
	@JoinColumn(name = "idOrdenTbjo")
	private OrdenDeTrabajo orden;
	
	@ManyToOne
	@JoinColumn(name = "idRepuesto")
	private Repuesto repuesto;

	public long getIdRepeOrden() {
		return idRepeOrden;
	}

	public void setIdRepeOrden(long idRepeOrden) {
		this.idRepeOrden = idRepeOrden;
	}

	public float getHoras() {
		return horas;
	}

	public void setHoras(float horas) {
		this.horas = horas;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public OrdenDeTrabajo getOrden() {
		return orden;
	}

	public void setOrden(OrdenDeTrabajo orden) {
		this.orden = orden;
	}

	public Repuesto getRepuesto() {
		return repuesto;
	}

	public void setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}
		

}
