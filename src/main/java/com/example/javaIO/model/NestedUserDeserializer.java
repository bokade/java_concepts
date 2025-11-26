package com.example.javaIO.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class NestedUserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node == null || node.isNull()) return null;
        int id = node.has("user_id") ? node.get("user_id").asInt() : 0;
        String name = node.has("username") ? node.get("username").asText() : null;
        User u = new User();
        u.setId(id);
        u.setName(name);
        return u;
    }
}