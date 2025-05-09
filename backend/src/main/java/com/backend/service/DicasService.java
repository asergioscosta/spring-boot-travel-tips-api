package com.backend.service;

import com.backend.exception.RegraNegocioException;
import com.backend.model.entity.Dicas;
import com.backend.model.repository.DicasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DicasService {

    private DicasRepository dicasRepository;

    public DicasService(DicasRepository dicasRepository) {
        this.dicasRepository = dicasRepository;
    }

    public List<Dicas> getDicas() {
        return dicasRepository.findAll();
    }

    public Optional<Dicas> getDicasById(Long id) {
        return dicasRepository.findById(id);
    }

    @Transactional
    public Dicas save(Dicas dicas) {
        validar(dicas);
        return dicasRepository.save(dicas);
    }

    @Transactional
    public void delete(Dicas dicas) {
        Objects.requireNonNull(dicas.getId());
        dicasRepository.delete(dicas);
    }

    public void validar(Dicas dicas) {

        if (dicas.getDestino() == null) {
            throw new RegraNegocioException("Destino inválido. Por favor, insira um destino válido.");
        }

        if (dicas.getCategorias() == null) {
            throw new RegraNegocioException("Categoria inválida. Por favor, insira uma categoria válida.");
        }

        if (dicas.getDescricao() == null || dicas.getDescricao().length() < 20) {
            throw new RegraNegocioException("Descrição inválida. A descrição deve ter pelo menos 20 caracteres.");
        }

        if (dicas.getAvaliacao() == null || dicas.getAvaliacao() < 1 || dicas.getAvaliacao() > 5) {
            throw new RegraNegocioException("Nível de avaliação inválido. Insira um valor de avaliação entre 1 e 5.");
        }

        if (dicas.getCustoMedioDia() == null) {
            throw new RegraNegocioException("Custo médio inválido. Insira um valor válido para o custo médio.");
        }
    }
}
