package sharing.dormitory.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserPrincipalDetailsService extends UserDetailsService {

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

}
