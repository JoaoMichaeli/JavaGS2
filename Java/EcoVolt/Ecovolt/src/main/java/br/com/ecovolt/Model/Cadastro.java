package br.com.ecovolt.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cadastro {
    private Integer idCadastro;
    private String email;
    private String senha;
}