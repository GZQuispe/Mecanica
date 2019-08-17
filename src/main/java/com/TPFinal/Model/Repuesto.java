package com.TPFinal.Model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Repuesto {
	
	@Id
	@GeneratedValue
	private long idRepuesto;
	
	private float costo;
	private String descripcion;
	
	@OneToMany(mappedBy = "repuesto")
	private List <RepeOrden> listaRepeOrdenR;

	public long getIdRepuesto() {
		return idRepuesto;
	}

	public void setIdRepuesto(long idRepuesto) {
		this.idRepuesto = idRepuesto;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<RepeOrden> getListaRepeOrden() {
		return listaRepeOrdenR;
	}

	public void setListaRepeOrden(List<RepeOrden> listaRepeOrden) {
		this.listaRepeOrdenR = listaRepeOrden;
	}
	
}
