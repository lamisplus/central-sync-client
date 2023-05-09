package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadConfirmationDTO {
    @NotNull(message = "syncQueueId is mandatory")
    private Long syncQueueId;

    @NotNull(message = "remoteAccessTokenId is mandatory")
    private Long remoteAccessTokenId;

    private String error;
}
