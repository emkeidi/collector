package com.blobplop.collector.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result<T> {
    private ActionStatus status = ActionStatus.SUCCESS;
    private final ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public Result() {
    }

    public Result(T payload) {
        this.payload = payload;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void addMessage(ActionStatus status, String message) {
        this.status = status;
        messages.add(message);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return status == result.status && Objects.equals(messages,
                result.messages) && Objects.equals(payload, result.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, messages, payload);
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", messages=" + messages +
                ", payload=" + payload +
                '}';
    }
}
