package com.example.javaIO.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NestedUserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User u, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (u == null) {
            gen.writeNull();
            return;
        }
        gen.writeStartObject();
        gen.writeNumberField("user_id", u.getId());
        gen.writeStringField("username", u.getName());
        gen.writeEndObject();
    }
}