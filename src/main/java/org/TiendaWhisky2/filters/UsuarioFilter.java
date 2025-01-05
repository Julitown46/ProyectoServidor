package org.TiendaWhisky2.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.TiendaWhisky2.model.Usuario;

@WebFilter(
        urlPatterns = {"/informatica/categorias/*", "/informatica/productos/*", "/informatica/usuarios/*"},
        initParams = {@WebInitParam(
                name = "acceso-concedido-a-rol",
                value = "Admin"
        )}
)
public class UsuarioFilter extends HttpFilter implements Filter {
    private String rolAcceso;

    public UsuarioFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String url = httpRequest.getRequestURL().toString();
        Usuario usuario = null;
        if (session != null && (usuario = (Usuario) session.getAttribute("usuario-logado")) != null && "Admin".equals(usuario.getRol())) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else if (!url.endsWith("/categorias/crear") && !url.contains("/categorias/editar") && !url.contains("/categorias/borrar") && !url.endsWith("/productos/crear") && !url.contains("/productos/editar") && !url.contains("/productos/borrar") && !url.endsWith("/usuarios/crear") && !url.contains("/usuarios/editar") && !url.contains("/usuarios/borrar")) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/tienda/usuarios/login");
        }
    }

    public void destroy() {
    }
}