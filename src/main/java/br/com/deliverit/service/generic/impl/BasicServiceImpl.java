package br.com.deliverit.service.generic.impl;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.deliverit.repository.generic.BasicRepository;
import br.com.deliverit.service.exception.ServiceException;
import br.com.deliverit.service.generic.BasicService;

public abstract class BasicServiceImpl <R extends BasicRepository<T, ID>, T, ID> implements BasicService<T, ID> {
	
	protected R repository;

    protected BasicServiceImpl(R repository) {
        this.repository = repository;
    }
    
    public Page<T> findAll(Pageable pageable) {
    	return repository.findAll(pageable);
    }
    
    @Override
	public T findById(ID id) {
    	
    	if(id == null) {
    		throw new ServiceException("Favor enviar um registro válido");
    	}
    	
    	Optional<T> entity = repository.findById(id);
    	
    	if(entity == null) {
    		throw new ServiceException("O registro não está cadastrado no sistema");
    	}
    	return entity.get();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public T save(T entity) throws ServiceException {
    	try {
    		
    		if(entity == null) {
    			throw new ServiceException("Favor informar um registro válido");
    		}
    		return repository.save(entity);
		} catch (Exception e) {			
			throw new ServiceException("Erro ao salvar o registro", e);
		}
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public T update(T entity) throws ServiceException {
    	try {
    		return save(entity);
		} catch (ServiceException e) {
			throw new ServiceException("Erro ao atualizar o registro", e);
		}
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(ID id) throws ServiceException {
    	
    	if(id == null) {
			throw new ServiceException("Favor informar um registro válido");
		}
    	
    	T found = findById(id);
    	
    	if(found == null) {
    		throw new ServiceException("Não existe o registro cadastrado para exclusão");
    	}
    	
		try {
			repository.delete(found);
			return true;
		} catch (ServiceException e) {
			throw new ServiceException("Erro ao excluir o registro", e);
		}
		
    }
	
}
