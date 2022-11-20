package br.com.itau.todo.list.api.controller;

import br.com.itau.todo.list.api.AbstractTodoListTest;
import br.com.itau.todo.list.api.controller.request.TaskCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.controller.request.LoginRequest;
import br.com.itau.todo.list.api.service.TaskService;
import br.com.itau.todo.list.api.service.UserLoggedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class TaskControllerTest extends AbstractTodoListTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserLoggedService userLoggedService;

    private String tokenUser;
    private String tokenAdmin;

    @BeforeEach
    public void setup() throws Exception {
        var bodyUser = LoginRequest.builder().username("cassiano_ricardo@hotmail.com").password("123").build();
        tokenUser = callEndpointLogin(bodyUser);
        var bodyAdmin = LoginRequest.builder().username("cassiano_ricardo@gmail.com").password("123").build();
        tokenAdmin = callEndpointLogin(bodyAdmin);
    }

    @Test
    @DisplayName("create :: success")
    void create_success() throws Exception {

        var taskCreateRequest = TaskCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/task")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                        .content(mapper.writeValueAsString(taskCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("create :: summary is mandatory")
    void create_mandatory_summary() throws Exception {

        var taskCreateRequest = TaskCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .description("Description Test")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/task")
                        .content(mapper.writeValueAsString(taskCreateRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message").value("summary is mandatory"));
    }

    @Test
    @DisplayName("create :: description is mandatory")
    void create_mandatory_description() throws Exception {

        var taskCreateRequest = TaskCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary Test")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/task")
                        .content(mapper.writeValueAsString(taskCreateRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("description is mandatory"));
    }

    @Test
    @DisplayName("create :: status is mandatory")
    void create_mandatory_status() throws Exception {

        var taskCreateRequest = TaskCreateRequest.builder()
                .description("Description Test")
                .summary("Summary Test")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/task")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                        .content(mapper.writeValueAsString(taskCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("status is mandatory"));
    }

    @Test
    @DisplayName("getTaskByStatus :: with status")
    void getTaskByStatus_with_status() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,11,19, 00,00,00);
        calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        var taskResponseList = List.of(TaskResponse.builder()
                                                        .id(1L)
                                                        .dateCreated(calendar)
                                                        .summary("test")
                                                        .description("test")
                                                        .dateLastUpdate(calendar)
                                                        .status(StatusTaskEnum.PENDING).build());

        Optional<StatusTaskEnum> optional = Optional.of(StatusTaskEnum.PENDING);

        doReturn(taskResponseList).when(taskService).getTaskByUserAndStatus(eq(optional));

            mvc.perform(MockMvcRequestBuilders
                            .get("/task")
                            .param("status", StatusTaskEnum.PENDING.name())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].id").value(1))
                    //.andExpect(jsonPath("$.[0].dateCreated").value("2022-11-19T00:00:00"))
                    .andExpect(jsonPath("$.[0].summary").value("test"))
                    .andExpect(jsonPath("$.[0].description").value("test"))
                    //.andExpect(jsonPath("$.[0].dateLastUpdate").value("2022-11-19T00:00:00"))
                    .andExpect(jsonPath("$.[0].status").value(StatusTaskEnum.PENDING.name()));

        verify(taskService).getTaskByUserAndStatus(eq(optional));
        }

    @Test
    @DisplayName("getTaskByStatus :: without status")
    void getTaskByStatus_without_status() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,11,19, 00,00,00);
        calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        var taskResponseList = List.of(TaskResponse.builder()
                .id(1L)
                .dateCreated(calendar)
                .summary("test")
                .description("test")
                .dateLastUpdate(calendar)
                .status(StatusTaskEnum.PENDING).build());

        Optional<StatusTaskEnum> optional = Optional.empty();

        doReturn(taskResponseList).when(taskService).getTaskByUserAndStatus(eq(optional));

        mvc.perform(MockMvcRequestBuilders
                        .get("/task")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                //.andExpect(jsonPath("$.[0].dateCreated").value("2022-11-19T00:00:00"))
                .andExpect(jsonPath("$.[0].summary").value("test"))
                .andExpect(jsonPath("$.[0].description").value("test"))
                //.andExpect(jsonPath("$.[0].dateLastUpdate").value("2022-11-19T00:00:00"))
                .andExpect(jsonPath("$.[0].status").value(StatusTaskEnum.PENDING.name()));

        verify(taskService).getTaskByUserAndStatus(eq(optional));
    }

    @Test
    @DisplayName("getAll :: success")
    void getAll_success() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,11,19, 00,00,00);
        calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        var taskResponseList = List.of(TaskResponse.builder()
                .id(1L)
                .dateCreated(calendar)
                .summary("test")
                .description("test")
                .dateLastUpdate(calendar)
                .status(StatusTaskEnum.PENDING).build());

        doReturn(taskResponseList).when(taskService).getAllTasks();

        mvc.perform(MockMvcRequestBuilders
                        .get("/task/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenAdmin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                //.andExpect(jsonPath("$.[0].dateCreated").value("2022-11-19T00:00:00"))
                .andExpect(jsonPath("$.[0].summary").value("test"))
                .andExpect(jsonPath("$.[0].description").value("test"))
                //.andExpect(jsonPath("$.[0].dateLastUpdate").value("2022-11-19T00:00:00"))
                .andExpect(jsonPath("$.[0].status").value(StatusTaskEnum.PENDING.name()));
    }

    private String callEndpointLogin(LoginRequest body) throws Exception {
        var mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        JsonObject jsonObj = new Gson().fromJson(mvcResult.getResponse().getContentAsString(),JsonObject.class);
        return jsonObj.get("token").getAsString();
    }
}