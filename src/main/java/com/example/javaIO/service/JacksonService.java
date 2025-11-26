package com.example.javaIO.service;

import com.example.javaIO.model.Employeee;
import com.example.javaIO.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class JacksonService {

    @Autowired
    private ObjectMapper objectMapper;

    private final Map<Integer, Employeee> map = new HashMap<>();

    // 1. Convert User object to JSON
    public String convertObjectToJson(User user) throws Exception {
        return objectMapper.writeValueAsString(user);
    }

    // 2. Convert JSON to Java Object
    public User convertJsonToObject(String json) throws Exception {
        return objectMapper.readValue(json, User.class);
    }

    // 3. Convert JSON to Map
    public Map<String, Object> convertJsonToMap(String json) throws Exception {
        return objectMapper.readValue(json, new TypeReference<>() {});
    }

    // 4. Pretty Print JSON
    public String prettyPrint(Object obj) throws Exception {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj);
    }

    // 1. Parse → JsonNode
    public JsonNode parseJson(String json) throws Exception {
        return objectMapper.readTree(json);
    }

    // 2. Extract fields
    public String extractFields(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);

        String name = node.path("name").asText("defaultName");
        int age = node.path("age").asInt(-1);
        boolean isActive = node.path("active").asBoolean(false);

        return "Name=" + name + ", Age=" + age + ", Active=" + isActive;
    }

    // 3. Create ObjectNode
    public JsonNode createDynamicObjectNode() {
        ObjectNode obj = objectMapper.createObjectNode();
        obj.put("name", "Swapnil");
        obj.put("age", 28);
        obj.put("city", "Mumbai");
        obj.put("active", true);

        ArrayNode skills = objectMapper.createArrayNode();
        skills.add("Java").add("Spring").add("Microservices");

        obj.set("skills", skills);

        return obj;
    }

    // 4. Create ArrayNode
    public JsonNode createDynamicArrayNode() {
        ArrayNode arr = objectMapper.createArrayNode();
        arr.add("Java");
        arr.add(100);

        ObjectNode temp = objectMapper.createObjectNode();
        temp.put("city", "Pune");

        arr.add(temp);

        return arr;
    }

    // 5. Modify ObjectNode
    public JsonNode modifyObjectNode(String json) throws Exception {
        ObjectNode node = (ObjectNode) objectMapper.readTree(json);

        node.put("status", "ACTIVE");
        node.remove("password");

        node.put("updated", true);

        return node;
    }

    // 6. Merge Two JSON Objects
    public JsonNode mergeJsonObjects(String json1, String json2) throws Exception {
        ObjectNode node1 = (ObjectNode) objectMapper.readTree(json1);
        ObjectNode node2 = (ObjectNode) objectMapper.readTree(json2);

        node1.setAll(node2); // merge all

        return node1;
    }

    // 7. JsonNode → User
    public User convertJsonNodeToUser(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        return objectMapper.treeToValue(node, User.class);
    }

    // 8. User → JsonNode
    public JsonNode convertUserToJsonNode(User user) {
        return objectMapper.valueToTree(user);
    }

    // 9. Pretty Print JsonNode
    public String prettyPrintNode(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    // 10. Validate JSON fields
    public String validateJson(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);

        if (!node.has("name")) return "Missing: name";
        if (!node.has("age")) return "Missing: age";

        return "Valid JSON";
    }

    // 11. Iterate object fields
    public String iterateObjectFields(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        StringBuilder sb = new StringBuilder();

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            var entry = fields.next();
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    // 12. Iterate Array
    public String iterateArray(String json) throws Exception {
        JsonNode arr = objectMapper.readTree(json);
        StringBuilder sb = new StringBuilder();

        for (JsonNode x : arr) {
            sb.append(x.toString()).append("\n");
        }
        return sb.toString();
    }

    // 13. Deep Copy
    public JsonNode deepCopyNode(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        JsonNode copy = node.deepCopy();
        return copy;
    }

    // Serialize User → JSON
    public String serializeUser(User user) throws Exception {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }

    // Deserialize JSON → User
    public User deserializeUser(String json) throws Exception {
        return objectMapper.readValue(json, User.class);
    }


    public Employeee save(Employeee emp) {
        map.put(emp.getId(), emp);
        return emp;
    }

    public Employeee get(int id) {
        return map.get(id);

    }


    public Collection<Employeee> getAll() {
        return map.values();
    }

    private final Map<Integer, User> store = new HashMap<>();

        public User save(User user) {
            store.put(user.getId(), user);
            return user;
        }

        public User gets(int id) {
            return store.get(id);
        }




       // ---------

    public User saves(User u) {
        store.put(u.getId(), u);
        return u;
    }

    public User getss(int id) { return store.get(id); }

    public Collection<User> getAlls() { return store.values(); }

    // write a user to file (JSON)
    public void writeToFile(User user, File file) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, user);
    }

    // read user from file
    public User readFromFile(File file) throws IOException {
        return objectMapper.readValue(file, User.class);
    }

    // for JsonMergePatch / merge: returns JsonNode
    public JsonNode toJsonNode(Object obj) {
        return objectMapper.valueToTree(obj);
    }


}
