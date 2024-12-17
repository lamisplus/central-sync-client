package org.lamisplus.modules.central.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
public class RemoteAccessTokenDto implements Serializable {
    private Long id;

    @NotEmpty(message = "url is mandatory")
    private String url;

    @NotEmpty(message = "username is mandatory")
    private String username;

    @NotEmpty(message = "password is mandatory")
    private String password;

    private Long organisationUnitId;

    private String prKey;
}
