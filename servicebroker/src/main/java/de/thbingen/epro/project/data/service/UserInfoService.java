/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.model.UserInfo;
import de.thbingen.epro.project.data.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoService implements UserService{

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo registerAccount(UserInfo user) {
        //TODO implement method
        return null;
    }

}
