package br.com.itau.todo.list.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusTaskEnum {

    PENDING(1),
    COMPLETED(2);

    int code;
}
