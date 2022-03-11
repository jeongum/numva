package com.egongil.numva.api.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponseEntity {
    private boolean isSuccess;
    private int code;
    private String message;

    @Builder
    public BaseResponseEntity(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }


}
