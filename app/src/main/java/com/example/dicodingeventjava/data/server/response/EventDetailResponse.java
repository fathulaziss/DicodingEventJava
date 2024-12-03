package com.example.dicodingeventjava.data.server.response;

import androidx.annotation.NonNull;

import com.example.dicodingeventjava.data.server.dto.EventDto;

import java.io.Serializable;

public class EventDetailResponse implements Serializable {

    private boolean error;
    private String message;
    private EventDto event;

    @NonNull
    @Override
    public String toString() {
        return "EventDetailResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", event=" + event +
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

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }
}
