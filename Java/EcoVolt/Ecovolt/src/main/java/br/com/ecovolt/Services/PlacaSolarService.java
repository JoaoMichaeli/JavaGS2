package br.com.ecovolt.Services;

import br.com.ecovolt.DAO.PlacaSolarDAO;
import br.com.ecovolt.Model.PlacaSolar;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class PlacaSolarService {
    private final PlacaSolarDAO placaSolarDAO;
    public PlacaSolarService() {
        this.placaSolarDAO = new PlacaSolarDAO();
    }

    public void cadastrarPlaca(PlacaSolar placa) throws SQLException {
        if (placa.getModelo() == null || placa.getModelo().isEmpty()) {
            throw new RuntimeException("O modelo da placa não pode ser vazio!");
        }
        if (placa.getPotenciaNominal() == null || placa.getPotenciaNominal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("A potência nominal deve ser maior que zero!");
        }
        if (placa.getIdCadastro() == null) {
            throw new RuntimeException("O idCadastro não pode ser nulo!");
        }
        if (!placaSolarDAO.verificarIdCadastroExistente(placa.getIdCadastro())) {
            throw new RuntimeException("O idCadastro informado não existe no banco de dados!");
        }
        placaSolarDAO.cadastrarPlaca(placa);
    }

    public List<PlacaSolar> buscarPlacasPorUsuario(int idCadastro) throws SQLException {
        if (idCadastro <= 0) {
            throw new RuntimeException("ID de usuário inválido.");
        }
        return placaSolarDAO.buscarPlacasPorUsuario(idCadastro);
    }

    public PlacaSolar buscarPlacaPorId(int idPlaca) throws SQLException {
        PlacaSolar placa = placaSolarDAO.buscarPlacaPorId(idPlaca);
        if (placa == null) {
            throw new RuntimeException("Placa não encontrada com o ID fornecido!");
        }
        return placa;
    }

    public void editarPlaca(PlacaSolar placa) throws SQLException {
        PlacaSolar placaExistente = buscarPlacaPorId(placa.getIdPlaca());
        if (placaExistente == null) {
            throw new RuntimeException("Não é possível editar uma placa inexistente!");
        }
        placaSolarDAO.editarPlaca(placa);
    }

    public void desativarPlaca(int idPlaca) throws SQLException {
        PlacaSolar placa = placaSolarDAO.buscarPlacaPorId(idPlaca);
        if (placa == null) {
            throw new RuntimeException("Placa não encontrada!");
        }
        placaSolarDAO.desativarPorId(idPlaca);
    }

    public void ativarPlaca(int idPlaca) throws SQLException {
        PlacaSolar placa = placaSolarDAO.buscarPlacaPorId(idPlaca);
        if (placa == null) {
            throw new RuntimeException("Placa não encontrada!");
        }
        if (placa.getSituacao() == 1) {
            throw new RuntimeException("A placa já está ativada!");
        }
        placaSolarDAO.ativarPorId(idPlaca);
    }

    public List<PlacaSolar> listarTodasPlacas() throws SQLException {
        List<PlacaSolar> placas = placaSolarDAO.listarTodasPlacas();
        if (placas.isEmpty()) {
            throw new RuntimeException("Nenhuma placa encontrada no sistema.");
        }
        return placas;
    }
}