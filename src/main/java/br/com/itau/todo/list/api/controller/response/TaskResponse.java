package br.com.itau.todo.list.api.controller.response;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class TaskResponse {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Calendar dateCreated;
    private String summary;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Calendar dateLastUpdate;
    private StatusTaskEnum status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user;
}
