package pl.edu.pb.wi.bai.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class PasswordPreprocessingFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    	if(request instanceof HttpServletRequest) {
    		chain.doFilter(new HttpServletPasswordWrapper((HttpServletRequest) request), response);
    	} else {
    		chain.doFilter(request, response);
    	}
    }
}
