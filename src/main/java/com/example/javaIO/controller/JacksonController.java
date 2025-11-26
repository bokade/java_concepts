package com.example.javaIO.controller;


import com.example.javaIO.model.Employeee;
import com.example.javaIO.model.User;
import com.example.javaIO.service.JacksonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/json")
public class JacksonController {


    @Autowired
    private JacksonService jacksonService;

    @Autowired
    private ObjectMapper objectMapper;

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


    // 1. Create Employee using JSON
    @PostMapping("/employeee-create")
    public ResponseEntity<Employeee> createEmployee(@RequestBody Employeee emp) {
        return ResponseEntity.ok(jacksonService.save(emp));
    }

    // 2. Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employeee> getEmployee(@PathVariable int id) {
        return ResponseEntity.ok(jacksonService.get(id));
    }

    // 3. Get all employees (pretty JSON using ObjectNode)
    @GetMapping("/get-all-employeee")
    public ResponseEntity<JsonNode> getAllEmployees() {

        ArrayNode array = objectMapper.createArrayNode();

        for (Employeee e : jacksonService.getAll()) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("id", e.getId());
            node.put("name", e.getName());
            node.put("joinDate", e.getJoinDate().toString());
            node.put("lastUpdated", e.getLastUpdated().toString());
            array.add(node);
        }

        return ResponseEntity.ok(array);
    }

    // 4. Modify JSON dynamically using ObjectNode
    @GetMapping("/details/{id}")
    public ResponseEntity<JsonNode> employeeDetails(@PathVariable int id) throws JsonProcessingException {

        Employeee e = jacksonService.get(id);

        JsonNode node = objectMapper.valueToTree(e);   // convert POJO → JsonNode

        ObjectNode root = (ObjectNode) node;

        root.put("status", "ACTIVE");
        root.put("serverTime", LocalDateTime.now().toString());

        return ResponseEntity.ok(root);
    }


    // 1. Custom serialization + deserialization
    @PostMapping("/user-save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(jacksonService.save(user));
    }

    // 2. Convert object → map
    @GetMapping("/convert-to-map/{id}")
    public ResponseEntity<Map<String, Object>> convertUserToMap(@PathVariable int id) {
        User user = jacksonService.gets(id);
        Map<String, Object> map = objectMapper.convertValue(user, Map.class);
        return ResponseEntity.ok(map);
    }

    // 3. Convert map → object
    @PostMapping("/convert-to-object")
    public ResponseEntity<User> convertMapToUser(@RequestBody Map<String, Object> map) {
        User user = objectMapper.convertValue(map, User.class);
        return ResponseEntity.ok(user);
    }

    // 4. Merge two JsonNodes
    @PostMapping("/merge-json")
    public ResponseEntity<JsonNode> mergeJson(@RequestBody JsonNode node1) throws JsonProcessingException {

        ObjectNode base = objectMapper.createObjectNode();
        base.put("server", "active");

        ((ObjectNode) base).setAll((ObjectNode) node1);

        return ResponseEntity.ok(base);
    }


//-------------------------------------------------

    // -------------------------
    // 1) Create / Return (Controller I/O)
    // -------------------------
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = jacksonService.saves(user);
        return ResponseEntity.ok(saved);
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/user" -H "Content-Type: application/json" -d '{ "id": 1, "full_name":"Swapnil", "city":"Mumbai", "dob":"1995-05-10", "age":30 }'

    // -------------------------
    // 2) Logging JSON representation (example endpoint)
    // -------------------------
    @PostMapping("/log-user")
    public ResponseEntity<String> logUser(@RequestBody User user) throws Exception {
        // Example: convert to pretty JSON string for logging
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println("LOG JSON:\n" + json); // replace with logger in real app
        return ResponseEntity.ok(json);
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/log-user" -H "Content-Type: application/json" -d '{ "id": 2, "full_name":"Akash", "city":"Pune", "dob":"1990-03-01", "age":35 }'

    // -------------------------
    // 3) Read JSON from file
    // -------------------------
    @GetMapping("/read-file")
    public ResponseEntity<User> readFromFile() throws IOException {
        File file = new File("D:\\Practical_Java\\JavaConcepts\\data\\user-5.json"); // path relative to app root
        User u = jacksonService.readFromFile(file);
        return ResponseEntity.ok(u);
    }
    // (You should first create the file on disk via write-file endpoint)
    // cURL:
    // curl -X GET "http://localhost:8080/json/read-file"

    // -------------------------
    // 4) Write JSON to file
    // -------------------------
    @PostMapping("/write-file")
    public ResponseEntity<String> writeToFile(@RequestBody User user) throws IOException {
        new File("data").mkdirs();
        File file = new File("data/user-" + user.getId() + ".json");
        jacksonService.writeToFile(user, file);
        return ResponseEntity.ok("Wrote to " + file.getAbsolutePath());
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/write-file" -H "Content-Type: application/json" -d '{ "id": 5, "full_name":"FileUser", "city":"Mumbai", "dob":"1992-07-07", "age":28 }'

    // -------------------------
    // 5) JsonNode / ObjectNode / ArrayNode dynamic creation
    // -------------------------
    @GetMapping("/dynamic-json")
    public ResponseEntity<JsonNode> dynamicJson() {
        ObjectNode root = objectMapper.createObjectNode();
        root.put("serverTime", LocalDateTime.now().toString());
        root.put("status", "OK");

        ArrayNode arr = objectMapper.createArrayNode();
        arr.add("java");
        arr.add("spring");
        arr.add(123);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("id", 10);
        userNode.put("name", "DynamicUser");
        userNode.set("skills", arr);

        root.set("user", userNode);
        return ResponseEntity.ok(root);
    }
    // cURL:
    // curl -X GET "http://localhost:8080/json/dynamic-json"

    // -------------------------
    // 6) Convert Object -> Map and Map -> Object
    // -------------------------
    @GetMapping("/user-to-map/{id}")
    public ResponseEntity<Map<String, Object>> userToMap(@PathVariable int id) {
        User u = jacksonService.getss(id);
        Map<String, Object> map = objectMapper.convertValue(u, Map.class);
        return ResponseEntity.ok(map);
    }
    // cURL:
    // curl -X GET "http://localhost:8080/json/user-to-map/1"

    @PostMapping("/map-to-user")
    public ResponseEntity<User> mapToUser(@RequestBody Map<String, Object> map) {
        User u = objectMapper.convertValue(map, User.class);
        // optional: save
        jacksonService.saves(u);
        return ResponseEntity.ok(u);
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/map-to-user" -H "Content-Type: application/json" -d '{ "id": 11, "full_name":"MapUser", "city":"Delhi", "dob":"1998-11-11", "age":24 }'

    // -------------------------
    // 7) Merge two JsonNodes (setAll)
    // -------------------------
    @PostMapping("/merge-jsons")
    public ResponseEntity<JsonNode> mergeJsons(@RequestBody JsonNode incoming) {
        ObjectNode base = objectMapper.createObjectNode();
        base.put("server", "active");
        // merge: incoming overrides base if same key
        ((ObjectNode) base).setAll((ObjectNode) incoming);
        return ResponseEntity.ok(base);
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/merge-jsons" -H "Content-Type: application/json" -d '{ "name":"MergeUser", "age":40 }'

    // -------------------------
    // 8) PATCH - partial update using JsonMerge (simple approach)
    // -------------------------
    @PatchMapping(path = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> patchUser(@PathVariable int id, @RequestBody JsonNode patchNode) {
        User existing = jacksonService.getss(id);
        if (existing == null) return ResponseEntity.notFound().build();

        // convert existing user to ObjectNode and merge
        ObjectNode existingNode = (ObjectNode) objectMapper.valueToTree(existing);
        // setAll will overwrite existing fields with patchNode fields
        existingNode.setAll((ObjectNode) patchNode);

        // convert back to User and save
        User updated = objectMapper.convertValue(existingNode, User.class);
        jacksonService.save(updated);

        return ResponseEntity.ok(existingNode);
    }
    // cURL:
    // curl -X PATCH "http://localhost:8080/json/users/1" -H "Content-Type: application/json" -d '{ "city":"UpdatedCity", "age":99 }'

    // -------------------------
    // 9) Custom serializer / deserializer demonstration (nestedUser)
    // -------------------------
    @PostMapping("/user-nested")
    public ResponseEntity<User> createUserWithNested(@RequestBody User user) {
        jacksonService.saves(user);
        return ResponseEntity.ok(user);
    }
    // cURL:
    // curl -X POST "http://localhost:8080/json/user-nested" -H "Content-Type: application/json" -d '{ "id": 20, "full_name":"Parent", "city":"Hyd", "dob":"1993-06-06", "age":32, "nestedUser": { "user_id": 99, "username":"Child" } }'

    // -------------------------
    // 10) Streaming API (example: write sequence of objects to OutputStream)
    // -------------------------
    @GetMapping("/stream-users")
    public void streamUsers(jakarta.servlet.http.HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (var gen = objectMapper.getFactory().createGenerator(response.getOutputStream())) {
            gen.writeStartArray();
            for (User u : jacksonService.getAlls()) {
                objectMapper.writeValue(gen, u); // streaming each object
            }
            gen.writeEndArray();
        }
    }
    // cURL:
    // curl -X GET "http://localhost:8080/json/stream-users"
}
