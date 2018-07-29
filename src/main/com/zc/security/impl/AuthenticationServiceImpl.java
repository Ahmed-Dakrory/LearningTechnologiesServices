package main.com.zc.security.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.com.zc.security.AuthenticationService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

	@Inject
	private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
	@Inject
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;
	@Inject
	private SecurityContextRepository securityContextRepository;
	@Inject
	private UserDetailsService userDetailsService;
	private ExternalContext ctx;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Authentication result;
	private HttpRequestResponseHolder holder;
	private SecurityContext securityContext;

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public boolean login(String username, String password) {
		try {
			ctx = FacesContext.getCurrentInstance().getExternalContext();
			request = (HttpServletRequest) ctx.getRequest();
			response = (HttpServletResponse) ctx.getResponse();
			holder = new HttpRequestResponseHolder(request, response);
			securityContext = securityContextRepository.loadContext(holder);
			SecurityContextHolder.setContext(securityContext);

			usernamePasswordAuthenticationFilter.setUsernameParameter("Mail");
			usernamePasswordAuthenticationFilter
					.setPasswordParameter("Password");
			result = usernamePasswordAuthenticationFilter
					.attemptAuthentication(request, response);

			sessionAuthenticationStrategy.onAuthentication(result, request,
					response);

			if (result == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Security Error", "Security Error"));
				return false;
			}

			// below : do the same thing as in
			// AbstractAuthenticationProcessingFilter.successfulAuthentication(),
			// except for the redirection to the login success URL that is
			// managed by JSF

			securityContext.setAuthentication(result);

			usernamePasswordAuthenticationFilter.getRememberMeServices()
					.loginSuccess(request, response, result);

			if (applicationEventPublisher != null) {
				applicationEventPublisher
						.publishEvent(new InteractiveAuthenticationSuccessEvent(
								result, getClass()));
			}

			return true;
		} catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);
			failed.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error login", ""));
			return false;
		}

	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		// currentUser.unauthenticate();
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) {
		SecurityContextHolder.clearContext();
		usernamePasswordAuthenticationFilter.getRememberMeServices().loginFail(
				request, response);
	}

	@Override
	public boolean autoLogin(String username, String password) {
		try {
	    	FacesContext context = FacesContext.getCurrentInstance();
	    	
	    	
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(username), password);
	        ctx = FacesContext.getCurrentInstance().getExternalContext();
			request = (HttpServletRequest) ctx.getRequest();
			
	        token.setDetails(new WebAuthenticationDetails(request));
	        Authentication authentication = (Authentication)token;
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        SecurityContextHolder.getContext().setAuthentication(null);
	       return false;
	    }
		/*
		 * 
		 * try {

			LoginStaffDTO dao = loginSecAppService.getUserByMail(username);
			Collection<GrantedAuthority> studentAuthorities = new ArrayList<GrantedAuthority>();
			studentAuthorities.add(new GrantedAuthorityImpl("ROLE_REGISTERED"));
			UserDetails user = new User(username, dao.getPassword(), true,
					true, true, true, studentAuthorities);
			return user;
		} catch (IndexOutOfBoundsException ex) {
			throw new UsernameNotFoundException("UserAccount for name \""
					+ username + "\" not found.");
		}
	    try {
	    	FacesContext context = FacesContext.getCurrentInstance();
	    	UserDetailsServiceImpl userDetailsService= (UserDetailsServiceImpl) context
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(context.getELContext(),
							"#{userDetailsService}", UserDetailsServiceImpl.class)
					.getValue(context.getELContext());
	    	
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(username), password);
	        ctx = FacesContext.getCurrentInstance().getExternalContext();
			request = (HttpServletRequest) ctx.getRequest();
			
	        token.setDetails(new WebAuthenticationDetails(request));
	        Authentication authentication = (Authentication)token;
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        SecurityContextHolder.getContext().setAuthentication(null);
	       return false;
	    }*/
		
	}

}
