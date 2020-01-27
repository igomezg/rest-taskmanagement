package com.mimacom.taskmanagement.taskManagement.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    @NonNull
    private String code;
    @NonNull
    private String message;
}
