package br.com.deliverit.controller.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.deliverit.service.generic.BasicService;

/**
 * 
 * @author brunofo 
 * Bruno Franco Oliveira
 *
 */
public abstract class BasicController<S extends BasicService<T, ID>, T, ID> {

	protected S service;

    public BasicController(S service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<Page<T>> findAll(Pageable pageable) {
    	return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity< T > findById(@PathVariable(value = "id") ID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity< T > save(@RequestBody T entity) throws Exception {
    	return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity< Boolean > delete(@PathVariable(value = "id") ID id) throws Exception {
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping
    public ResponseEntity< T > update(@RequestBody T entity) throws Exception {
        return ResponseEntity.ok(service.update(entity));
    }

}
