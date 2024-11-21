package br.com.ecovolt.DAO;

import br.com.ecovolt.ConnectionFactory.CriaConexaoBD;
import br.com.ecovolt.Model.Cadastro;

import java.sql.*;

public class CadastroDAO {
    private Connection conn;

    public CadastroDAO() {
        this.conn = CriaConexaoBD.pegarConexao();
    }
    private final String SQL_DELETE_BY_ID = "UPDATE tbl_cadastros_ev SET situacao = 0 WHERE id_cadastro_ev = ?";
    private final String SQL_INSERT_CADASTRO = "INSERT INTO tbl_cadastros_ev (email, senha) VALUES (?, ?)";
    private final String SQL_EMAIL = "SELECT * FROM tbl_cadastros_ev WHERE email = ?";
    private final String SQL_ALTERAR_SENHA = "UPDATE tbl_cadastros_ev SET senha = ? WHERE email = ?";
    private final String SQL_EMAIL_EXISTENTE = "SELECT COUNT(*) FROM tbl_cadastros_ev WHERE email = ?";

    public void inserirUsuario(Cadastro cadastro) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_CADASTRO)) {
            ps.setString(1, cadastro.getEmail());
            ps.setString(2, cadastro.getSenha());
            ps.executeUpdate();
        }
    }

    public Cadastro buscarUsuarioPorEmail(String email) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_EMAIL)) {
            ps.setString(1, email);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                Cadastro c = new Cadastro();
                c.setIdCadastro(result.getInt("id_cadastro_ev"));
                c.setEmail(result.getString("email"));
                c.setSenha(result.getString("senha"));
                return c;
            }
        }
        return null;
    }

    public int alterarSenha(String email, String novaSenha) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_ALTERAR_SENHA)) {
            ps.setString(1, novaSenha);
            ps.setString(2, email);
            return ps.executeUpdate();
        }
    }

    public void desativarPorId(Integer idCadastro) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            ps.setInt(1, idCadastro);
            ps.executeUpdate();
        }
    }

    public boolean emailExiste(String email) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_EMAIL_EXISTENTE)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}