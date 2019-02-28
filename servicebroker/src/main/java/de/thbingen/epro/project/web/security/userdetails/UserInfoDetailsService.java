package de.thbingen.epro.project.web.security.userdetails;

import de.thbingen.epro.project.data.model.UserInfo;
import de.thbingen.epro.project.data.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for accessing and managing UserInfoRepository.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Slf4j
@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * @param s username of existing user
     * @return UserDetails - Details of found User.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoRepository.findByUsername(s);

        if (userInfo == null) {
            throw new UsernameNotFoundException(s);
        }

        return new UserInfoPrincipal(userInfo);

    }


}
