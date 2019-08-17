package com.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OrdenDeTrabajo {

	@Id
	@GeneratedValue
	private long idOrdenTbjo;
	
	private String patente,fechaIngreso,marca,detalleFalla;
	private boolean abierto;
	private float importeFinal;
	
	@ManyToOne
	@JoinColumn(name = "idPropietario")
	private Propietario propietario;

	@OneToMany(mappedBy = "orden")
	private List<RepeOrden> listaRepeOrden;

	public long getIdOrdenTbjo() {
		return idOrdenTbjo;
	}

	public void setIdOrdenTbjo(long idOrdenTbjo) {
		this.idOrdenTbjo = idOrdenTbjo;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDetalleFalla() {
		return detalleFalla;
	}

	public void setDetalleFalla(String detalleFalla) {
		this.detalleFalla = detalleFalla;
	}

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}

	public List<RepeOrden> getListaRepeOrden() {
		return listaRepeOrden;
	}

	public void setListaRepeOrden(List<RepeOrden> listaRepeOrden) {
		this.listaRepeOrden = listaRepeOrden;
	}

	public float getImporteFinal() {
		return importeFinal;
	}

	public void setImporteFinal(float importeFinal) {
		this.importeFinal = importeFinal;
	}

	
		
}
