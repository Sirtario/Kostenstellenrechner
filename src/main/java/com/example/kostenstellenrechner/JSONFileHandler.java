package com.example.kostenstellenrechner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

public class JSONFileHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    public List<CurrentData> deseriseCurrentData(String file) throws IOException {
        return mapper.readValue(new File(file), new TypeReference<List<CurrentData>>() {});
    }

    public List<Machine> deseriseMaschine(String file) throws IOException {
        return mapper.readValue(new File(file), new TypeReference<List<Machine>>() {});
    }

    //TODO: Verallgemeinern
    private <T>T DeserializeFromFile(File file, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(file, type);
    }
}
