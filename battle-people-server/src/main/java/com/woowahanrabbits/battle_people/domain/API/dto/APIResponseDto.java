package com.woowahanrabbits.battle_people.domain.API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class APIResponseDto<T> {
    private String code;

    private String msg;

    private T data;

}
