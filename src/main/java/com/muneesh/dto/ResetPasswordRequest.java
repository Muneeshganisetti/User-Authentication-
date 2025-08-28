package com.muneesh.dto;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import lombok.Data;


@Data
public class ResetPasswordRequest {

    private String token;
    private String NewPassword;
    private String ConfirmPassword;
}
