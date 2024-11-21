package br.com.ecovolt.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PlacaSolar {
    private Integer idCadastro;
    private Integer idPlaca;
    private String modelo;
    private BigDecimal potenciaNominal;
    private String cidade;
    private Character situacao;
}