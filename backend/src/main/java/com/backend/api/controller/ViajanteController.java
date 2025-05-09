package com.backend.api.controller;

import com.backend.dto.ViajanteDTO;
import com.backend.exception.RegraNegocioException;
import com.backend.model.entity.Viajante;
import com.backend.service.ViajanteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/viajantes")
@RequiredArgsConstructor
@CrossOrigin
public class ViajanteController {

    private final ViajanteService viajanteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Viajante> viajantes = viajanteService.getViajante();
        return ResponseEntity.ok(viajantes.stream().map(ViajanteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Viajante> viajantes = viajanteService.getViajanteById(id);
        if (!viajantes.isPresent()) {
            return new ResponseEntity("Viajante não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(viajantes.map(ViajanteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ViajanteDTO dto) {
        try {
            Viajante viajante = converter(dto);
            viajante = viajanteService.save(viajante);
            return new ResponseEntity(ViajanteDTO.create(viajante), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ViajanteDTO dto) {
        if (!viajanteService.getViajanteById(id).isPresent()) {
            return new ResponseEntity("Viajante não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Viajante viajante = converter(dto);
            viajante.setId(id);
            viajante = viajanteService.save(viajante);
            return ResponseEntity.ok(ViajanteDTO.create(viajante));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Viajante> viajantes = viajanteService.getViajanteById(id);
        if (!viajantes.isPresent()) {
            return new ResponseEntity("Viajante não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            viajanteService.delete(viajantes.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Viajante converter(ViajanteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Viajante viajante = modelMapper.map(dto, Viajante.class);
        return viajante;
    }
}
