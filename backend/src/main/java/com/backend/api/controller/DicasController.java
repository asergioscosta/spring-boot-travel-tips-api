package com.backend.api.controller;

import com.backend.dto.DicasDTO;
import com.backend.exception.RegraNegocioException;
import com.backend.model.entity.Dicas;
import com.backend.service.DicasService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dicas")
@RequiredArgsConstructor
@CrossOrigin
public class DicasController {

    private final DicasService dicasService;

    @GetMapping()
    public ResponseEntity get() {
        List<Dicas> dicas = dicasService.getDicas();
        return ResponseEntity.ok(dicas.stream().map(DicasDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Dicas> dicas = dicasService.getDicasById(id);
        if (!dicas.isPresent()) {
            return new ResponseEntity("Dicas não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dicas.map(DicasDTO::create));
    }

    @PostMapping()
        public ResponseEntity post(@RequestBody DicasDTO dto) {
        try {
            Dicas dicas = converter(dto);
            dicas = dicasService.save(dicas);
            return new ResponseEntity(DicasDTO.create(dicas), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
        public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DicasDTO dto) {
        if (!dicasService.getDicasById(id).isPresent()) {
            return new ResponseEntity("Dicas não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Dicas dicas = converter(dto);
            dicas.setId(id);
            dicas = dicasService.save(dicas);
            return ResponseEntity.ok(DicasDTO.create(dicas));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Dicas> dicas = dicasService.getDicasById(id);
        if (!dicas.isPresent()) {
            return new ResponseEntity("Dicas não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            dicasService.delete(dicas.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Dicas converter(DicasDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Dicas dicas = modelMapper.map(dto, Dicas.class);
        return dicas;
    }
}