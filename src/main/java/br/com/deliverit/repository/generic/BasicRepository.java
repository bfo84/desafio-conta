package br.com.deliverit.repository.generic;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasicRepository<T, ID> extends JpaRepository<T, ID> {

	Page<T> findAll(Pageable pageable);

}