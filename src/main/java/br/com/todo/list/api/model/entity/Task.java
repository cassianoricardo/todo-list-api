package br.com.todo.list.api.model.entity;

import br.com.todo.list.api.enums.StatusTaskEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @Column
    private Calendar dateCreated;
    @Column
    private String summary;
    @Column
    private String description;
    @Column
    private Calendar dateLastUpdate;
    @Column
    private StatusTaskEnum status;

}

