package br.com.ecovolt;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Permite que as requisições do tipo OPTIONS sejam tratadas adequadamente
        if ("OPTIONS".equalsIgnoreCase(requestContext.getRequest().getMethod())) {
            Response.ResponseBuilder response = Response.ok();
            setCorsHeaders(response);
            requestContext.abortWith(response.build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Adiciona cabeçalhos CORS à resposta, se ainda não estiverem presentes
        if (responseContext.getHeaders().get("Access-Control-Allow-Origin") == null ||
                responseContext.getHeaders().get("Access-Control-Allow-Origin").isEmpty()) {
            setCorsHeaders(responseContext);
        }
    }

    // Método para configurar os cabeçalhos CORS na resposta
    private void setCorsHeaders(Response.ResponseBuilder response) {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
        response.header("Access-Control-Allow-Credentials", "true");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.header("Access-Control-Max-Age", "1209600");
    }

    private void setCorsHeaders(ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}