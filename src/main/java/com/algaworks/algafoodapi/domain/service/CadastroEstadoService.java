package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidateEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidateNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excuir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidateNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com código %d", estadoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidateEmUsoException(
                    String.format("Estado de código %d não pode ser removida, pois está em uso", estadoId));
        }
    }
}