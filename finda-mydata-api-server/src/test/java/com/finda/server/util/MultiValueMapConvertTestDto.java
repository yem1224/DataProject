package com.finda.server.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MultiValueMapConvertTestDto {
    private String name;
    private Integer amount;
    private Boolean check;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;
    private Status status;

    public enum Status {
        SUCCESS
    }

    @Builder
    public MultiValueMapConvertTestDto(String name, Integer amount, Boolean check, LocalDateTime localDateTime, Status status) {
        this.name = name;
        this.amount = amount;
        this.check = check;
        this.localDateTime = localDateTime;
        this.status = status;
    }
}
