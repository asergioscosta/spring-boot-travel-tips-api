package com.backend.service;

import com.backend.exception.RegraNegocioException;
import com.backend.model.entity.Viajante;
import com.backend.model.repository.ViajanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ViajanteService {

    private ViajanteRepository viajanteRepository;

    public ViajanteService(ViajanteRepository viajanteRepository) {
        this.viajanteRepository = viajanteRepository;
    }

    public List<Viajante> getViajante() {
        return viajanteRepository.findAll();
    }

    public Optional<Viajante> getViajanteById(Long id) {
        return viajanteRepository.findById(id);
    }

    @Transactional
    public Viajante save(Viajante viajante) {
        validar(viajante);
        return viajanteRepository.save(viajante);
    }

    @Transactional
    public void delete(Viajante viajante) {
        Objects.requireNonNull(viajante.getId());
        viajanteRepository.delete(viajante);
    }

    public void validar(Viajante viajante) {
        if (viajante.getNome() == null || viajante.getNome().length() < 3 || viajante.getNome().isEmpty()) {
            throw new RegraNegocioException("Nome deve ter pelo menos 3 caracteres.");
        }

        if (viajante.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de Nascimento inválida. Digite uma data de nascimento válida.");
        }

        if (viajante.getNivelExperiencia() == null || viajante.getNivelExperiencia() < 1 || viajante.getNivelExperiencia() > 5) {
            throw new RegraNegocioException("Nível de Experiência inválido. O nível de experiência deve estar entre 1 e 5.");
        }

        if (viajante.getEmail() == null || viajante.getEmail().isEmpty()) {
            throw new RegraNegocioException("E-mail inválido. Digite um e-mail válido.");
        }

        Optional<Viajante> viajanteTemp = viajanteRepository.findByEmail(viajante.getEmail());
        if (viajanteTemp.isPresent()) {
            if (!Long.valueOf(viajante.getId()).equals(viajanteTemp.get().getId())) {
                throw new RegraNegocioException("O email fornecido já está em uso.");
            }
        }

        if (viajante.getTelefone() == null) {
            throw new RegraNegocioException("Telefone inválido. Digite um telefone válido.");
        }
    }
}
