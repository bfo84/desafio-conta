package br.com.deliverit.service.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.deliverit.service.exception.ServiceException;


/**
 * 
 * @author brunofo 
 * Bruno Franco Oliveira
 *
 */
public interface BasicService<T, ID> {
	
	T save(T entity) throws ServiceException;
	
	T update(T entity) throws ServiceException;
	
	Page< T > findAll(Pageable pageable);
	
	T findById(ID id);
	
	boolean delete(ID id) throws ServiceException;

}
