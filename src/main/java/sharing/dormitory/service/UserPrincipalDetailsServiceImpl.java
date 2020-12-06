package sharing.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sharing.dormitory.auth.UserPrincipal;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.repository.UserRepository;

@Service
public class UserPrincipalDetailsServiceImpl implements UserPrincipalDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserPrincipalDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
