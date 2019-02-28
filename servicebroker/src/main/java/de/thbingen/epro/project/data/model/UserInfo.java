package de.thbingen.epro.project.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Entity representing an User who has access to REST-API.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "userinfo")
@Data
@NoArgsConstructor
public class UserInfo extends AbstractLongId implements Serializable {

    @NotNull
    @NotEmpty
    @Column
    private String firstName;

    @NotNull
    @NotEmpty
    @Column
    private String lastName;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @NotEmpty
    @Column
    private String password;

}
