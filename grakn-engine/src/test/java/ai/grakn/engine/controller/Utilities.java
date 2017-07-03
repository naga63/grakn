package ai.grakn.engine.controller;

import static ai.grakn.engine.tasks.TaskSchedule.now;
import static ai.grakn.util.REST.Response.EXCEPTION;
import static ai.grakn.util.REST.Response.Graql.ORIGINAL_QUERY;
import static ai.grakn.util.REST.Response.Graql.RESPONSE;

import com.jayway.restassured.response.Response;

import ai.grakn.engine.tasks.BackgroundTask;
import ai.grakn.engine.tasks.TaskSchedule;
import ai.grakn.engine.tasks.TaskState;
import ai.grakn.engine.tasks.mock.ShortExecutionMockTask;
import mjson.Json;


public class Utilities {
    static String exception(Response response) {
        return response.getBody().as(Json.class, new JsonMapper()).at(EXCEPTION).asString();
    }
    
    static String stringResponse(Response response) {
        return response.getBody().as(Json.class, new JsonMapper()).at(RESPONSE).asString();
    }

    static Json jsonResponse(Response response) {
        return response.getBody().as(Json.class, new JsonMapper()).at(RESPONSE);
    }
    
    static String originalQuery(Response response) {
        return response.getBody().as(Json.class, new JsonMapper()).at(ORIGINAL_QUERY).asString();
    }
    
    public static TaskState createTask() {
        return createTask(ShortExecutionMockTask.class);
    }

    public static TaskState createTask(Class<? extends BackgroundTask> clazz) {
        return createTask(clazz, now());
    }

    public static TaskState createTask(Class<? extends BackgroundTask> clazz, TaskSchedule schedule) {
        return TaskState.of(clazz, Utilities.class.getName(), schedule, TaskState.Priority.LOW);
    }
    
}
