package br.com.deliverit.repository.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.deliverit.repository.dao.BasicRepositoryDAO;


/**
 * 
 * @author brunofo 
 * Bruno Franco Oliveira
 *
 */
public abstract class BasicRepositoryImpl<T, ID> implements BasicRepositoryDAO<T, ID> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
}
