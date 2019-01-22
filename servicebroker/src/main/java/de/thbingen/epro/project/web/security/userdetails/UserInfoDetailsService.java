/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.security.userdetails;

import de.thbingen.epro.project.data.model.UserInfo;
import de.thbingen.epro.project.data.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoRepository.findByUsername(s);

        if (userInfo == null) {
            throw new UsernameNotFoundException(s);
        }

        return new UserInfoPrincipal(userInfo);

    }



}
