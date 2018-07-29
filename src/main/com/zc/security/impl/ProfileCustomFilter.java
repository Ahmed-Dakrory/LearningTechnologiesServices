package main.com.zc.security.impl;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.com.zc.services.applicationService.persons.service.IStudentProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


public class ProfileCustomFilter implements Filter{

	@Autowired
	IStudentProfileService studentProfileService;
	
	
    @Override
    public void destroy() {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
    		HttpServletResponse response = (HttpServletResponse) res;
           
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           if(!authentication.getPrincipal().equals("anonymousUser")&&(((HttpServletRequest)req).getRequestURL().toString().contains("pages"))){
        	   String mail=((User) authentication.getPrincipal()).getUsername();
        	  	   if (mail.toLowerCase().startsWith("s-")&&(!((HttpServletRequest)req).getRequestURL().toString().contains("accountSettings/studentProfile"))&&(!studentProfileService.checkStudentProfile(mail))) {
            		   response.sendRedirect(((HttpServletRequest)req).getContextPath()+"/pages/secured/accountSettings/studentProfile.xhtml?faces-redirect=true"); 
            	   }}
        	   chain.doFilter(req, res);
    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}




}