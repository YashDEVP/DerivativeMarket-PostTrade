package com.derivativemarket.posttrade.org.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh-mm-ss dd-MM-yyyy")
    private LocalDateTime timestamn;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timestamn = LocalDateTime.now();
    }
    public ApiResponse(T data) {
    this();
    this.data=data;
    }
    public ApiResponse(ApiError error){
        this();
        this.error=error;
    }

}
