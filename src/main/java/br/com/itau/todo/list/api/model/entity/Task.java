package br.com.itau.todo.list.api.model.entity;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = -3808269286877328785L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Calendar dateCreated;
    private String summary;
    private String description;
    private Calendar dateLastUpdate;
    private StatusTaskEnum status;

}

