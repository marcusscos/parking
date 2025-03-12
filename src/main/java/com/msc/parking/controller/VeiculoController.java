// filepath: /home/kali/projects/parking/src/main/java/com/msc/parking/controller/VeiculoController.java
package com.msc.parking.controller;

import com.msc.parking.model.Veiculo;
import com.msc.parking.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public String listarTodos(Model model) {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        model.addAttribute("veiculos", veiculos);
        return "veiculos";
    }

    @PostMapping("/entrada")
    public String registrarEntrada(@RequestParam String placa, @RequestParam(required = false) Double custoPorHora, Model model) {
        String placaPattern = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$";
        if (!Pattern.matches(placaPattern, placa)) {
            model.addAttribute("errorMessage", "Placa inválida. Deve seguir o formato ABC1D23 ou ABC1234.");
            return listarTodos(model);
        }
        if (custoPorHora == null || custoPorHora < 5) {
            model.addAttribute("errorMessage", "O custo por hora deve ser um número válido e no mínimo 5.");
            return listarTodos(model);
        }
        try {
            veiculoService.registrarEntrada(placa, custoPorHora);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return listarTodos(model);
        }
        return "redirect:/veiculos";
    }

    @PostMapping("/saida")
    public String registrarSaida(@RequestParam(required = false) Integer id, Model model) {
        if (id == null) {
            model.addAttribute("errorMessage", "ID não pode ser nulo ou vazio.");
            return listarTodos(model);
        }
        try {
            Veiculo veiculo = veiculoService.registrarSaida(id);
            if (veiculo != null) {
                double custoTotal = veiculo.calcularCusto();
                model.addAttribute("successMessage", "Saída registrada com sucesso. Preço total pago: " + custoTotal);
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return listarTodos(model);
        }
        return listarTodos(model);
    }
}