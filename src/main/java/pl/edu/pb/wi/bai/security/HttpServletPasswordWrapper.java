package pl.edu.pb.wi.bai.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletPasswordWrapper extends HttpServletRequestWrapper{

	public HttpServletPasswordWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		if(!name.equals("password")) {
			return super.getParameter(name);
		}
		return "paod1";
	}

}
