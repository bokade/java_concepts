package com.example.javaIO.controller;


import com.example.javaIO.model.User;
import com.example.javaIO.service.JacksonService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/json")
public class JacksonController {


    @Autowired
    private JacksonService jacksonService;

    // 1. Java Object → JSON
    @GetMapping("/object-to-json")
    public String objectToJson() throws Exception {
        User user = new User("Swapnil",  "Mumbai", LocalDate.of(1998, 11, 24), 30);
        return jacksonService.convertObjectToJson(user);
    }

    // 2. JSON → Java Object
    @PostMapping("/json-to-object")
    public User jsonToObject(@RequestBody String json) throws Exception {
        return jacksonService.convertJsonToObject(json);
    }

    // 3. JSON → Map
    @PostMapping("/json-to-map")
    public Map<String, Object> jsonToMap(@RequestBody String json) throws Exception {
        return jacksonService.convertJsonToMap(json);
    }

    // 4. Pretty Print JSON
    @GetMapping("/pretty")
    public String prettyJson() throws Exception {
        User user = new User("Swapnil",  "Mumbai", LocalDate.of(1998, 11, 24), 30);
        return jacksonService.prettyPrint(user);
    }

    // 1. Parse JSON → JsonNode
    @PostMapping("/parse")
    public JsonNode parseJson(@RequestBody String json) throws Exception {
        return jacksonService.parseJson(json);
    }

    // 2. Extract fields from JsonNode
    @PostMapping("/extract")
    public String extractFields(@RequestBody String json) throws Exception {
        return jacksonService.extractFields(json);
    }

    // 3. Create ObjectNode dynamically
    @GetMapping("/create-object")
    public JsonNode createObject() {
        return jacksonService.createDynamicObjectNode();
    }

    // 4. Create ArrayNode dynamically
    @GetMapping("/create-array")
    public JsonNode createArray() {
        return jacksonService.createDynamicArrayNode();
    }

    // 5. Add/Remove/Update JSON fields
    @PostMapping("/modify-object")
    public JsonNode modifyObject(@RequestBody String json) throws Exception {
        return jacksonService.modifyObjectNode(json);
    }

    // 6. Merge Two JSON Objects
    @PostMapping("/merge")
    public JsonNode mergeObjects(@RequestBody Map<String, String> data) throws Exception {
        return jacksonService.mergeJsonObjects(data.get("json1"), data.get("json2"));
    }

    // 7. Convert JsonNode → User
    @PostMapping("/jsonnode-to-user")
    public User jsonNodeToUser(@RequestBody String json) throws Exception {
        return jacksonService.convertJsonNodeToUser(json);
    }

    // 8. Convert User → JsonNode
    @GetMapping("/user-to-jsonnode")
    public JsonNode userToJsonNode() {
        User user = new User("Swapnil",  "Mumbai", LocalDate.of(1998, 11, 24), 30);
        return jacksonService.convertUserToJsonNode(user);
    }

    // 9. Pretty Print JsonNode
    @PostMapping("/pretty-node")
    public String prettyNode(@RequestBody String json) throws Exception {
        return jacksonService.prettyPrintNode(json);
    }

    // 10. Validate fields exist
    @PostMapping("/validate")
    public String validateJson(@RequestBody String json) throws Exception {
        return jacksonService.validateJson(json);
    }

    // 11. Iterate all fields of ObjectNode
    @PostMapping("/iterate-fields")
    public String iterateObjectFields(@RequestBody String json) throws Exception {
        return jacksonService.iterateObjectFields(json);
    }

    // 12. Iterate array elements
    @PostMapping("/iterate-array")
    public String iterateArrayElements(@RequestBody String json) throws Exception {
        return jacksonService.iterateArray(json);
    }

    // 13. Deep Copy JsonNode
    @PostMapping("/deep-copy")
    public JsonNode deepCopy(@RequestBody String json) throws Exception {
        return jacksonService.deepCopyNode(json);
    }

    @PostMapping("/serialize")
    public String serialize(@RequestBody User user) throws Exception {
        return jacksonService.serializeUser(user);
    }

    // 2. Deserialize JSON → User
    @PostMapping("/deserialize")
    public User deserialize(@RequestBody String json) throws Exception {
        return jacksonService.deserializeUser(json);
    }
}
