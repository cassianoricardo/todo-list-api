package br.com.itau.todo.list.api.model.entity;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Enumerated(EnumType.STRING)
    private StatusTaskEnum status;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;


}

