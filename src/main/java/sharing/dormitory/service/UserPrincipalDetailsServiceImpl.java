package sharing.dormitory.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sharing.dormitory.auth.UserPrincipal;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.repository.UserRepository;

@Service
public class UserPrincipalDetailsServiceImpl implements UserPrincipalDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
