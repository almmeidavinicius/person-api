package br.com.vinicius.personapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PhoneType {

    HOME("HOME"),
    MOBILE("MOBILE"),
    COMMERCIAL("COMMERCIAL");

    private final String description;

}
