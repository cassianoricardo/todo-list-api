package br.com.itau.todo.list.api.controller.docs;

import br.com.itau.todo.list.api.model.dto.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;

public interface TodoControllerDocs {

    @Operation(summary = "Cria uma nova tarefa",
            description = "descrição")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = Boolean.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class))))
            })
    void create(@RequestBody(content = @Content(schema = @Schema(
            implementation = Boolean.class))) TaskDTO taskDTO);
}
