// filepath: /home/kali/projects/parking/src/main/java/com/msc/parking/model/Veiculo.java
package com.msc.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa inválida. Deve seguir o formato ABC1D23 ou ABC1234.")
    private String placa;

    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double custoPorHora;
    private boolean pago;

    public double calcularCusto() {
        if (entrada != null && saida != null) {
            Duration duracao = Duration.between(entrada, saida);
            long minutos = duracao.toMinutes();
            double horas = minutos / 60.0;
            double custo = Math.ceil(horas) * custoPorHora;
            return Math.max(custo, custoPorHora);
        }
        return 0.0;
    }
}