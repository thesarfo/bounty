package dev.thesarfo.testgenius.exporters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.thesarfo.testgenius.core.DataSet;

import java.io.File;
import java.io.IOException;

/**
 * Exporter for JSON format.
 */
public class JsonExporter implements DataExporter {
    @Override
    public void export(DataSet dataSet, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(file, dataSet.getAllEntities());
    }
}