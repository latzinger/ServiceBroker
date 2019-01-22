/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);

}
