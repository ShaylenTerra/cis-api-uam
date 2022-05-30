package com.dw.ngms.cis.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 16/06/21, Wed
 **/
@Slf4j
public final class FileUtils {

    public static final String EXTENSION_SEPARATOR = ".";
    public static final String PATH_SEPARATOR = "/";
    public static final String HYPHEN = "-";
    public static final String ROOT_PATH = System.getProperty("java.io.tmpdir");

    /**
     * @param fileName fileName
     * @return extention of file
     */
    public static String getFileExtension(final String fileName) {
        if (StringUtils.isBlank(fileName))
            return null;
        return Optional.of(fileName)
                .filter(s -> s.contains("."))
                .map(s -> s.substring(fileName.lastIndexOf(".") + 1)).get();
    }

    public static FileSystemResource writeInputStreamToFile(InputStream inputStream, String fileName) {
        try {

            final Path path = Paths.get(ROOT_PATH + PATH_SEPARATOR + fileName);

            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            return new FileSystemResource(path);

        } catch (IOException e) {
            log.error(" error occured while persisting images to file");
            e.printStackTrace();
        }

        return null;
    }

}
