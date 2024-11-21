package br.com.ecovolt.DAO;

import br.com.ecovolt.ConnectionFactory.CriaConexaoBD;
import br.com.ecovolt.Model.PlacaSolar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlacaSolarDAO {
    private Connection conn;
    public PlacaSolarDAO() {
        this.conn = CriaConexaoBD.pegarConexao();
    }

    private static final String SQL_CADASTRAR_PLACA = "INSERT INTO tbl_placas_ev (id_cadastro_ev, modelo, potencia_nominal, cidade) VALUES (?, ?, ?, ?)";
    private static final String SQL_BUSCAR_PLACAS_POR_USUARIO = "SELECT * FROM tbl_placas_ev WHERE id_cadastro_ev = ? AND situacao = 1";
    private static final String SQL_BUSCAR_PLACA_POR_ID = "SELECT * FROM tbl_placas_ev WHERE id_placa = ?";
    private static final String SQL_EDITAR_PLACA = "UPDATE tbl_placas_ev SET modelo = ?, potencia_nominal = ?, cidade = ? WHERE id_placa = ?";
    private static final String SQL_DESATIVAR_PLACA = "UPDATE tbl_placas_ev SET situacao = 0 WHERE id_placa = ?";
    private static final String SQL_VERIFICAR_CADASTRO = "SELECT COUNT(*) FROM tbl_cadastros_ev WHERE id_cadastro_ev = ?";
    private static final String SQL_ATIVAR_PLACA = "UPDATE tbl_placas_ev SET situacao = 1 WHERE id_placa = ?";

    public void cadastrarPlaca(PlacaSolar placa) throws SQLException {
        if (placa.getIdCadastro() == null) {
            throw new SQLException("O idCadastro não pode ser nulo.");
        }
        try (PreparedStatement ps = conn.prepareStatement(SQL_CADASTRAR_PLACA)) {
            ps.setInt(1, placa.getIdCadastro());
            ps.setString(2, placa.getModelo());
            ps.setBigDecimal(3, placa.getPotenciaNominal());
            ps.setString(4, placa.getCidade());
            ps.executeUpdate();
        }
    }

    public boolean verificarIdCadastroExistente(Integer idCadastro) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_VERIFICAR_CADASTRO)) {
            ps.setInt(1, idCadastro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public List<PlacaSolar> buscarPlacasPorUsuario(int idCadastroEv) throws SQLException {
        List<PlacaSolar> placas = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_PLACAS_POR_USUARIO)) {
            ps.setInt(1, idCadastroEv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PlacaSolar placa = new PlacaSolar();
                placa.setIdPlaca(rs.getInt("id_placa"));
                placa.setIdCadastro(rs.getInt("id_cadastro_ev"));
                placa.setModelo(rs.getString("modelo"));
                placa.setPotenciaNominal(rs.getBigDecimal("potencia_nominal"));
                placa.setCidade(rs.getString("cidade"));
                placa.setSituacao((char) rs.getInt("situacao"));
                placas.add(placa);
            }
        }
        return placas;
    }

    public PlacaSolar buscarPlacaPorId(int idPlaca) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_PLACA_POR_ID)) {
            ps.setInt(1, idPlaca);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlacaSolar placa = new PlacaSolar();
                placa.setIdPlaca(rs.getInt("id_placa"));
                placa.setIdCadastro(rs.getInt("id_cadastro_ev"));
                placa.setModelo(rs.getString("modelo"));
                placa.setPotenciaNominal(rs.getBigDecimal("potencia_nominal"));
                placa.setCidade(rs.getString("cidade"));
                placa.setSituacao((char) rs.getInt("situacao"));
                return placa;
            }
        }
        return null;
    }

    public void editarPlaca(PlacaSolar placa) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_EDITAR_PLACA)) {
            ps.setString(1, placa.getModelo());
            ps.setBigDecimal(2, placa.getPotenciaNominal());
            ps.setString(3, placa.getCidade());
            ps.setInt(4, placa.getIdPlaca());
            ps.executeUpdate();
        }
    }

    public void desativarPorId(int idPlaca) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_DESATIVAR_PLACA)) {
            ps.setInt(1, idPlaca);
            ps.executeUpdate();
        }
    }

    public void ativarPorId(int idPlaca) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_ATIVAR_PLACA)) {
            ps.setInt(1, idPlaca);
            ps.executeUpdate();
        }
    }

    public List<PlacaSolar> listarTodasPlacas() throws SQLException {
        String sql = "SELECT * FROM tbl_placas_ev";
        List<PlacaSolar> placas = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PlacaSolar placa = new PlacaSolar();
                placa.setIdPlaca(rs.getInt("id_placa"));
                placa.setIdCadastro(rs.getInt("id_cadastro_ev"));
                placa.setModelo(rs.getString("modelo"));
                placa.setPotenciaNominal(rs.getBigDecimal("potencia_nominal"));
                placa.setCidade(rs.getString("cidade"));
                placa.setSituacao((char) rs.getInt("situacao"));
                placas.add(placa);
            }
        }
        return placas;
    }

}