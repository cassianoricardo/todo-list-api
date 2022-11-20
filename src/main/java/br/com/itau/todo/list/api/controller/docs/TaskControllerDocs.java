package br.com.itau.todo.list.api.controller.docs;

import br.com.itau.todo.list.api.controller.request.TaskCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface TaskControllerDocs {

    @Operation(summary = "Create task",
            description = "Create task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = Boolean.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "401", description = "Not Authenticated", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "403", description = "Not Authorized", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class))))
            })
    void create(@RequestBody(content = @Content(schema = @Schema(
            implementation = Boolean.class))) TaskCreateRequest taskCreateRequest);

    @Operation(summary = "get all tasks",
            description = "get all tasks")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = Boolean.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "401", description = "Not Authenticated", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "403", description = "Not Authorized", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class))))
            })
    ResponseEntity<List<TaskResponse>> getAll();

    @Operation(summary = "get task by status",
            description = "get task by status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = Boolean.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "401", description = "Not Authenticated", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class)))),
                    @ApiResponse(responseCode = "403", description = "Not Authorized", content = @Content(array = @ArraySchema(schema = @Schema(
                            implementation = HttpStatus.class))))
            })
    ResponseEntity<List<TaskResponse>> getTaskByStatus(@RequestParam(required = false) Optional<StatusTaskEnum> status);
}
