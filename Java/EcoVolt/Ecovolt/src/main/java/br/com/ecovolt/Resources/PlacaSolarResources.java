package br.com.ecovolt.Resources;

import br.com.ecovolt.Model.PlacaSolar;
import br.com.ecovolt.Services.PlacaSolarService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("placas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlacaSolarResources {

    PlacaSolarService placaSolarService = new PlacaSolarService();

    @POST
    @Path("cadastrar")
    public Response cadastrarPlaca(PlacaSolar placa) {
        try {
            placaSolarService.cadastrarPlaca(placa);
            return Response.status(Response.Status.CREATED).entity("Placa cadastrada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao cadastrar a placa.").build();
        }
    }

    @GET
    @Path("buscarId/{idCadastroEv}")
    public Response buscarPlacasPorUsuario(@PathParam("idCadastroEv") int idCadastroEv) {
        try {
            if (idCadastroEv <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("ID do usuário inválido.").build();
            }
            List<PlacaSolar> placas = placaSolarService.buscarPlacasPorUsuario(idCadastroEv);
            if (placas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Nenhuma placa encontrada para o usuário.").build();
            }
            return Response.status(Response.Status.OK).entity(placas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar placas do usuário.").build();
        }
    }

    @GET
    @Path("{idPlaca}")
    public Response buscarPlacaPorId(@PathParam("idPlaca") int idPlaca) {
        try {
            PlacaSolar placa = placaSolarService.buscarPlacaPorId(idPlaca);
            return Response.status(Response.Status.OK).entity(placa).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao buscar a placa.").build();
        }
    }

    @PUT
    @Path("editar/{idPlaca}")
    public Response editarPlaca(@PathParam("idPlaca") int idPlaca, PlacaSolar placa) {
        try {
            placa.setIdPlaca(idPlaca); // Garantir que o ID da placa está correto
            placaSolarService.editarPlaca(placa);
            return Response.status(Response.Status.OK).entity("Placa editada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao editar a placa.").build();
        }
    }

    @DELETE
    @Path("desativar/{idPlaca}")
    public Response desativarPlaca(@PathParam("idPlaca") int idPlaca) {
        try {
            placaSolarService.desativarPlaca(idPlaca);
            return Response.status(Response.Status.OK).entity("Placa deletada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao desativar a placa.").build();
        }
    }

    @PUT
    @Path("ativar/{idPlaca}")
    public Response ativarPlaca(@PathParam("idPlaca") int idPlaca) {
        try {
            placaSolarService.ativarPlaca(idPlaca);
            return Response.status(Response.Status.OK).entity("Placa ativada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao ativar a placa.").build();
        }
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodasPlacas() {
        try {
            List<PlacaSolar> placas = placaSolarService.listarTodasPlacas();
            return Response.status(Response.Status.OK).entity(placas).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro no servidor ao listar placas.").build();
        }
    }
}