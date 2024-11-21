package br.com.ecovolt.Services;

import br.com.ecovolt.DAO.CadastroDAO;
import br.com.ecovolt.Model.Cadastro;

import java.sql.SQLException;

public class CadastroService {

    private final CadastroDAO cadastroDAO;

    public CadastroService() {
        this.cadastroDAO = new CadastroDAO();
    }

    public void cadastrarUsuario(Cadastro cadastro) throws SQLException {
        Cadastro usuarioExistente = cadastroDAO.buscarUsuarioPorEmail(cadastro.getEmail());
        if (usuarioExistente != null) {
            throw new RuntimeException("Já existe uma conta cadastrada com este e-mail!");
        }
        cadastroDAO.inserirUsuario(cadastro);
    }

    public Cadastro autorizaLogin(String email, String senha) throws SQLException {
        Cadastro usuario = cadastroDAO.buscarUsuarioPorEmail(email);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Login ou senha inválidos!");
        }
        return usuario;
    }

    public int alterarSenha(String email, String novaSenha) throws SQLException {
        Cadastro usuario = cadastroDAO.buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com o e-mail fornecido!");
        }
        return cadastroDAO.alterarSenha(email, novaSenha);
    }

    public void desativarConta(Integer idCadastro) throws SQLException {
        Cadastro usuario = cadastroDAO.buscarUsuarioPorEmail(idCadastro.toString());
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado para desativar!");
        }
        cadastroDAO.desativarPorId(idCadastro);
    }

    public boolean verificarEmailExistente(String email) {
        try {
            boolean existe = cadastroDAO.emailExiste(email);
            return existe;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar se o e-mail existe: " + e.getMessage());
            throw new RuntimeException("Erro ao verificar existência do e-mail. Tente novamente mais tarde.");
        }
    }
}