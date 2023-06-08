package org.lamisplus.modules.central.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class JsonUtility {
    private JsonUtility() {
    }

    public static final Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    public static <T> T json2Object(String str, Class<T> clazz)
            throws   IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(str, clazz);
    }

    public static <T> String object2Json(T obj) throws   IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }
    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static <T> T jsonFile2Object(String fileName, Class<T> clazz)
            throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //Ignoring missing fields in model objects
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(new File(concatenate(fileName)), clazz);
    }

    private static String concatenate(String fileName) {
        Path path = Paths.get("src","main", "resources", "test", fileName);
        return path.toString();
    }

    public static List readJsonFile(Object obj, String jsonFile){
        ObjectMapper objectMapper = new ObjectMapper();
        List entityObject = null;
        InputStream input;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            input = new FileInputStream(jsonFile);

            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, obj.getClass());

            entityObject = objectMapper.readValue(input, javaType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityObject;
    }

    public static List<String> traverse(JsonNode root, List <String> jsonFieldNames, Boolean withValues){
        if(root.isObject()){
            Iterator<String> fieldNames = root.fieldNames();

            while(fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                jsonFieldNames.add(fieldName);
                JsonNode fieldValue = root.get(fieldName);
                traverse(fieldValue, jsonFieldNames, false);
            }
        } else if(root.isArray()){
            ArrayNode arrayNode = (ArrayNode) root;
            for(int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                traverse(arrayElement, jsonFieldNames, false);
            }
        } else {
            if(withValues){
                jsonFieldNames.add(root.toString());
            }
            //get the json value
            // JsonNode root represents a single value field - do something with it.
        }
        return jsonFieldNames;
    }

    public static JsonNode getJsonNode(Object object){
        if(object != null){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(object, JsonNode.class);
        }else return null;
    }

    public static String traverse(JsonNode root, String field) {
        return root.get(field).toString();
    }

    public static <T> T getObjectFromJson(String json, Class<T> clazz) {

//        ObjectMapper mapper = JsonObjectMapper.makeMapper();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String getJsonFromObject(T obj) {

//        ObjectMapper mapper = JsonObjectMapper.makeMapper();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getJsonFilePath(MultipartFile file) throws IOException {
        String fileDir = Files.createTempDirectory("nom_export").toFile().getAbsolutePath();
        new File(fileDir).mkdir();
        String filePath = fileDir + File.separator + file.getOriginalFilename();
        File inputFile = new File(filePath);
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(inputFile.toPath()))) {

            byte[] data = file.getBytes();
            bos.write(data);

            return filePath;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "None";
    }

    public static ObjectMapper getObjectMapperWriter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

        return objectMapper;
    }

    public static ObjectMapper getObjectMapperReader() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

        return objectMapper;
    }

    public static void writeJsonObjectToFile(String jsonObject, String jsonFilePath) throws IOException {
        try(FileWriter writer = new FileWriter(jsonFilePath)){
            writer.write(jsonObject);
        } catch (IOException exception) {
            log.debug("Exception: {}", exception.getMessage());
        }

    }


}

