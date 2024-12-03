package com.example.dicodingeventjava.data.server.response;

import androidx.annotation.NonNull;

import com.example.dicodingeventjava.data.server.dto.EventDto;

import java.io.Serializable;
import java.util.List;

public class EventResponse implements Serializable {

    private boolean error;
    private String message;
    private List<EventDto> listEvents;

    @NonNull
    @Override
    public String toString() {
        return "EventResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", listEvents=" + listEvents +
                '}';
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EventDto> getListEvents() {
        return listEvents;
    }

    public void setListEvents(List<EventDto> listEvents) {
        this.listEvents = listEvents;
    }
}
