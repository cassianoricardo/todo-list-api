package br.com.itau.todo.list.api.controller.request;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "summary is mandatory")
    private String summary;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotNull(message = "status is mandatory")
    private StatusTaskEnum status;
}
