package com.msc.parking.service;

import com.msc.parking.model.Veiculo;
import com.msc.parking.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarEntrada() {
        String placa = "ABC1234";
        double custoPorHora = 10.0;

        when(veiculoRepository.existsByPlaca(placa)).thenReturn(false);
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Veiculo veiculo = veiculoService.registrarEntrada(placa, custoPorHora);

        assertNotNull(veiculo);
        assertEquals(placa, veiculo.getPlaca());
        assertEquals(custoPorHora, veiculo.getCustoPorHora());
        assertFalse(veiculo.isPago());
        assertNotNull(veiculo.getEntrada());

        verify(veiculoRepository, times(1)).existsByPlaca(placa);
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void testRegistrarSaida() {
        Integer id = 1;
        Veiculo veiculo = new Veiculo();
        veiculo.setId(id);
        veiculo.setEntrada(LocalDateTime.now().minusHours(2));

        when(veiculoRepository.findById(id)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Veiculo updatedVeiculo = veiculoService.registrarSaida(id);

        assertNotNull(updatedVeiculo);
        assertEquals(id, updatedVeiculo.getId());
        assertTrue(updatedVeiculo.isPago());
        assertNotNull(updatedVeiculo.getSaida());

        verify(veiculoRepository, times(1)).findById(id);
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void testListarTodos() {
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setPlaca("ABC1234");
        Veiculo veiculo2 = new Veiculo();
        veiculo2.setPlaca("XYZ5678");

        when(veiculoRepository.findAll()).thenReturn(Arrays.asList(veiculo1, veiculo2));

        List<Veiculo> veiculos = veiculoService.listarTodos();

        assertNotNull(veiculos);
        assertEquals(2, veiculos.size());
        assertEquals("ABC1234", veiculos.get(0).getPlaca());
        assertEquals("XYZ5678", veiculos.get(1).getPlaca());

        verify(veiculoRepository, times(1)).findAll();
    }
}