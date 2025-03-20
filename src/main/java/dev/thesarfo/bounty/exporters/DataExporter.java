package dev.thesarfo.bounty.exporters;

import dev.thesarfo.bounty.core.DataSet;

import java.io.File;
import java.io.IOException;

/**
 * Interface for exporting generated data to various formats.
 */
public interface DataExporter {
    /**
     * Exports a dataset to a file.
     *
     * @param dataSet The dataset to export
     * @param file The destination file
     * @throws IOException If an I/O error occurs
     */
    void export(DataSet dataSet, File file) throws IOException;
}