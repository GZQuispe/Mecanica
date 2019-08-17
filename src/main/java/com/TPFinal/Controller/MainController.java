package com.TPFinal.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.TPFinal.Model.DaoOrden;
import com.TPFinal.Model.DaoPropietario;
import com.TPFinal.Model.DaoRepuesto;
import com.TPFinal.Model.OrdenDeTrabajo;
import com.TPFinal.Model.Propietario;
import com.TPFinal.Model.RepeOrden;
import com.TPFinal.Model.DaoRepeOrden;

@Controller
public class MainController {
	
	@Autowired
	private DaoOrden daoOrdenTrabajo;
	
	@Autowired 
	private DaoPropietario daoPropietario;
	
	@Autowired
	private DaoRepeOrden daoRepeOrden;
	
	@Autowired
	private DaoRepuesto daoRepuesto;
	
	//CREO UNA ORDEN DE TRABAJO Y LA PASO
	//PASO LA LISTA DE CLIENTES PARA USARLOS EN UN SELECT
	@RequestMapping(value="/formulario", method = RequestMethod.GET)
	public ModelAndView crearOrdenTrabajoGet(){
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("orden", new OrdenDeTrabajo());
		modelAndView.addObject("listaClientes", daoPropietario.findAll());
		modelAndView.setViewName("formOrdenTrabajo");
		
		return modelAndView;
	}

	//PONGO QUE LA ORDEN DE TRABAJO ESTE ABIERTA (TRUE) Y LA GUARDO
	//PASO UNA LISTA DE ORDENES ABIERTAS
	@RequestMapping(value="/formulario", method = RequestMethod.POST)
	public ModelAndView crearOrdenTrabajoPost(@ModelAttribute OrdenDeTrabajo ordenTrabajo){
		
		ordenTrabajo.setAbierto(true);
		daoOrdenTrabajo.save(ordenTrabajo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ordenes", getListaOrdenesAbiertas());
		modelAndView.setViewName("index");
		
		return modelAndView;
	} 
	
	//PASO EL FORMULARIO DE CLIENTE PARA CREAR UNO
	@RequestMapping(value="/crearCliente", method = RequestMethod.GET)
	public ModelAndView crearClienteGet(){
			
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("cliente", new Propietario());
		modelAndView.setViewName("formCliente");
		
		return modelAndView;
	}
	
	//GUARDO EL NUEVO PROPIETARIO EN LA BASE DE DATOS
	//VUELVO AL FORMULARIO DE ORDEN DE TRABAJO
	@RequestMapping(value="/crearCliente", method = RequestMethod.POST)
	public ModelAndView crearClientePost(@ModelAttribute Propietario propietario){
	
		daoPropietario.save(propietario);
		
		ModelAndView modelAndView = crearOrdenTrabajoGet();
		return modelAndView;
	}
	
	// OBTENGO EL ID DE LA ORDEN Y LO PONGO EN UNA NUEVO REPEORDEN
	// PASO EL NUEVO REPEORDEN CON LA LISTA DE REPUESTOS EN UN FORMULARIO
	@RequestMapping(value="/agRepuesto", method=RequestMethod.GET)
	public ModelAndView agregarRepuestoGet(@RequestParam("idOrden") long idOrden){
		
		RepeOrden ro = new RepeOrden();
		ro.setOrden(daoOrdenTrabajo.findOne(idOrden));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("repOrden", ro);
		modelAndView.addObject("listaRep", daoRepuesto.findAll());
		modelAndView.setViewName("formRepuesto");
		
		return modelAndView;
	}
	
	//GUARDO EL REPEORDEN Y PASO LAS ORDENES ABIERTAS
	@RequestMapping(value="/agRepuesto", method=RequestMethod.POST)
	public ModelAndView agregarRepuestoPost(@ModelAttribute RepeOrden repeOrden)
	{
		repeOrden.setOrden(repeOrden.getOrden());
		daoRepeOrden.save(repeOrden);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ordenes", getListaOrdenesAbiertas());
		modelAndView.setViewName("index");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/importe", method=RequestMethod.GET)
	public ModelAndView mostrarImporteGet(@RequestParam("idOrden") long idOrden){
			
		//RECIBO EL ID DEL ORDEN Y LO BUSCO EN LA BASE DE DATOS
		//CREO VARIABLES AUXILIARES PARA CALCULAR UN IMPORTE FINAL
		OrdenDeTrabajo ordenFinal = daoOrdenTrabajo.findOne(idOrden);
		ArrayList<RepeOrden> listaFinal = new ArrayList<>();
		float horasPor350,costoPorCantidad;
		float auxImporteFinal = 0;
		
		//ARMO UNA LISTA CON LAS ORDENES DE LA TABLA INTERMEDIA
		//QUE TENGAN EL MISMO IDORDEN QUE EL RECIBIDO
		for (RepeOrden ro: daoRepeOrden.findAll()){
			
			if(ro.getOrden().getIdOrdenTbjo()==idOrden){
				horasPor350 = ro.getHoras()*350;
				costoPorCantidad=ro.getRepuesto().getCosto()*ro.getCantidad();
				auxImporteFinal+=horasPor350+costoPorCantidad;
				listaFinal.add(ro);
			}
		}
		// CIERRO LA ORDEN EN CASO DE QUE ESTE ABIERTA
		// LE PONGO EL IMPORTE FINAL Y LO GUARDO
		if (ordenFinal.isAbierto()){
			ordenFinal.setAbierto(false);
			ordenFinal.setImporteFinal(auxImporteFinal);
			daoOrdenTrabajo.save(daoOrdenTrabajo.findOne(ordenFinal.getIdOrdenTbjo()));
		}
		//PASO LA ORDEN CON LA LISTA FINAL DE ORDENES/REPUESTO
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("orden", ordenFinal);
		modelAndView.addObject("listaRep", listaFinal);
		modelAndView.setViewName("cerrado");
		
		return modelAndView;
	}

	//MUESTRO LA LISTA DE ORDENES, PUEDE SER SOLO ABIERTA O COMPLETA
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public ModelAndView verListadoDisponiblesGet(@RequestParam("abierto") boolean abierto){
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(abierto){
		modelAndView.addObject("ordenes", getListaOrdenesAbiertas());
		}
		else{
		modelAndView.addObject("ordenes", daoOrdenTrabajo.findAll());
		}
		return modelAndView;
	}
	
	//FUNCION AUXILIAR PARA VER LA LISTA DE ORDENES ABIERTAS
	public List<OrdenDeTrabajo> getListaOrdenesAbiertas(){
		List<OrdenDeTrabajo> listadoOrdenesAbiertas = new ArrayList<>();
		for (OrdenDeTrabajo ot: daoOrdenTrabajo.findAll()){
			if(ot.isAbierto()){
				listadoOrdenesAbiertas.add(ot);
			}
		}
		return listadoOrdenesAbiertas;
	}
	
}
