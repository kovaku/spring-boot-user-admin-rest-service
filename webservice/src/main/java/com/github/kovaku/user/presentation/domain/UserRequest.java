package com.github.kovaku.user.presentation.domain;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserRequest {
    @NonNull
    private String name;
    @NonNull
    private String email;
}
