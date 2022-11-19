package br.com.itau.todo.list.api.controller.request;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "summary is mandatory")
    private String summary;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NonNull
    private StatusTaskEnum status;
}
