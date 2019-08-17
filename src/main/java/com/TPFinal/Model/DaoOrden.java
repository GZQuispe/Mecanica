package com.TPFinal.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoOrden extends CrudRepository<OrdenDeTrabajo,Long>{

}
