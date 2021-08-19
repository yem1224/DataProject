package com.finda.server.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MultiValueMapPropertyNameTestDto {

    @JsonProperty("na")
    private String name;
    private Integer amount;
    private Boolean check;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @JsonProperty("lo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;

    @JsonProperty("st")
    private Status status;

    public enum Status {
        SUCCESS
    }

    @Builder
    public MultiValueMapPropertyNameTestDto(String name,
                                            Integer amount,
                                            Boolean check,
                                            LocalDateTime localDateTime,
                                            Status status) {
        this.name = name;
        this.amount = amount;
        this.check = check;
        this.localDateTime = localDateTime;
        this.status = status;
    }
}
