package br.com.itau.todo.list.api.model.dto;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.model.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar dateCreated;

    @NotBlank(message = "summary is mandatory")
    private String summary;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NonNull
    private StatusTaskEnum status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar dateLastUpdate;

    public Task toEntity(){
        return Task.builder()
            .id(this.id)
            .dateCreated(this.dateCreated)
            .dateLastUpdate(this.dateLastUpdate)
            .status(this.status)
            .description(this.description)
            .summary(this.summary)
            .build();
    }
}
