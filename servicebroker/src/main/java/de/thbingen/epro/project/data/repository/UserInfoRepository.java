

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for accessing User from Database.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    /**
     * Find a User by his username.
     *
     * @param username
     * @return UserInfo or null
     */
    public UserInfo findByUsername(String username);

}
