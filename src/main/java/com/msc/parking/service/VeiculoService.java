// filepath: /home/kali/projects/parking/src/main/java/com/msc/parking/service/VeiculoService.java
package com.msc.parking.service;

import com.msc.parking.model.Veiculo;
import com.msc.parking.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo registrarEntrada(String placa, Double custoPorHora) {
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia.");
        }
        if (custoPorHora == null || custoPorHora < 5) {
            throw new IllegalArgumentException("O custo por hora deve ser um número válido e no mínimo 5.");
        }
        Optional<Veiculo> veiculoExistente = veiculoRepository.findByPlaca(placa);
        if (veiculoExistente.isPresent() && !veiculoExistente.get().isPago()) {
            throw new IllegalArgumentException("Placa já registrada e não paga.");
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setEntrada(LocalDateTime.now());
        veiculo.setCustoPorHora(custoPorHora);
        veiculo.setPago(false);
        return veiculoRepository.save(veiculo);
    }

    public Veiculo registrarSaida(Integer id) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isPresent()) {
            Veiculo veiculo = veiculoOptional.get();
            veiculo.setSaida(LocalDateTime.now());
            veiculo.setPago(true); // Mark as paid when registering exit
            return veiculoRepository.save(veiculo);
        }
        throw new IllegalArgumentException("Veículo não encontrado.");
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }
}