package br.com.dev.catalog.Controller;

import br.com.dev.catalog.dto.ProductDto;
import br.com.dev.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity <Page<ProductDto>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
        return ResponseEntity.ok(service.findAll(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto) {

        ProductDto savedDto = service.insert(dto);

        //Retorna no header o link do objeto criado.
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedDto);

        //return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {

        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();

    }

}
