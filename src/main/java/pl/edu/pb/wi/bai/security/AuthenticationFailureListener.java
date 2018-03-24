package pl.edu.pb.wi.bai.security;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
 
    @Autowired
    private UserRepository userRepository;
 
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        UsernamePasswordAuthenticationToken tk = (UsernamePasswordAuthenticationToken) e.getSource();
        User user = userRepository.findByUsername(tk.getName());
        if(user != null) {
        	user.setLoginAttempts(user.getLoginAttempts()+1);
        	user.setLastFailedLogin(Calendar.getInstance().getTime());
        	userRepository.save(user);
        }
    }
}
