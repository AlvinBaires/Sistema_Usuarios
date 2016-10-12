/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utils;

import com.sv.udb.controlador.LoginBean;
import com.sv.udb.controlador.PermisoBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sv.udb.controlador.UsuarioRolBean;
import com.sv.udb.modelo.Permiso;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Roberto Cerón
 */
public class FilterManager implements Filter {
    @Inject
    private LoginBean logiBean; //Bean de session
    String prefix; //Prefijo de la aplicación

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("************************************************");
        System.out.println("***** Iniciando Despegue De la Aplicación ******");
        System.out.println("************************************************");
        this.prefix = filterConfig.getServletContext().getInitParameter("prefix");  //poo ---> web.xml
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServRequ = (HttpServletRequest) request;
        String page = httpServRequ.getRequestURI(); //Página actual
        System.out.println(page);
        String p = "";
        HttpServletResponse httpServResp = (HttpServletResponse) response;
        //Nombre del Contexto
        String nombCtxt = String.format("%s/%s/", httpServRequ.getContextPath(), this.prefix);
        List<String> initPage = new ArrayList<>(); //Páginas iniciales
        List<String> accePage = new ArrayList<>();
        initPage.add(httpServRequ.getContextPath() + "/");
        initPage.add(nombCtxt + "login.xhtml");
        accePage.add(httpServRequ.getContextPath() + "/");
        accePage.add(nombCtxt + "index.xhtml");
        List<String> ignoPageFilt = new ArrayList<>(); //Páginas ignoradas por el filtro
        ignoPageFilt.addAll(initPage);
        ignoPageFilt.add(nombCtxt + "otra.xhtml");
        this.logiBean = this.logiBean != null ? this.logiBean : new LoginBean();
        //System.err.println("Usted está navegando a: " + page);
        if(this.logiBean.getListPerm()!= null){
            for(Permiso temp : logiBean.getListPerm())
            {
                accePage.add(nombCtxt + temp.getCodiPagi().getNombPagi() + ".xhtml");
            }
        }
        try
        {
             // Si esta en pagina inicial y está logeado
            if(initPage.contains(page) && this.logiBean.isLoge())
            {
                httpServResp.sendRedirect(nombCtxt + "index.xhtml"); //Se va para el index
            }
            if(ignoPageFilt.contains(page)) //Si está en las páginas ignoradas, deja pasar
            {
                chain.doFilter(request, response);
            }
            else
            {
                if(logiBean.isLoge()) //Si está logeado, deja pasar
                {
                     if(accePage.contains(page)){
                        String pageArray[] = page.split("/|\\.");
                        p = pageArray[3];
                        for(Permiso temp : logiBean.getListPerm())
                        {
                            if(temp.getCodiPagi().getNombPagi().equals(p)){
                                this.logiBean.setObjePerm(temp);
                            }
                        }
                        chain.doFilter(request, response);
                     }
                     else
                        httpServResp.sendRedirect(nombCtxt + "index.xhtml"); 
                }
                else // Sino enviarlo al login
                {
                    httpServResp.sendRedirect(nombCtxt + "login.xhtml");
                }
            }
        }
        catch (Exception e) {
        }
    }

    @Override
    public void destroy() {
        System.out.println("**************************************************");
        System.out.println("***** Finalizando Despliegue De la Aplicación ******");
        System.out.println("**************************************************");
    }

}
