package br.com.itau.todo.list.api.model.dto;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.model.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar dateCreated;
    private String summary;
    private String description;
    private StatusTaskEnum status;
    @Temporal(TemporalType.DATE)
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
