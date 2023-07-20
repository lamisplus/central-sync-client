package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDTO {
    @NotBlank(message = "serverUrl is mandatory")
    private String serverUrl;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "password is mandatory")
    private String password;
}
