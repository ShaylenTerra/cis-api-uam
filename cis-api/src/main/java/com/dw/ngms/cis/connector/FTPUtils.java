package com.dw.ngms.cis.connector;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author : prateekgoel
 * @since : 17/03/21, Wed
 **/
@Slf4j
public class FTPUtils {
    /**
     * Creates a nested directory structure on a FTP server
     *
     * @param ftpClient an instance of org.apache.commons.net.ftp.FTPClient class.
     * @param dirPath   Path of the directory, i.e /projects/java/ftp/demo
     * @return true if the directory was created successfully, false otherwise
     * @throws java.io.IOException if any error occurred during client-server communication
     */
    public static boolean makeDirectories(FTPClient ftpClient, String dirPath)
            throws IOException {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] pathElements = dirPath.split(pattern);
        if (pathElements.length > 0) {
            for (String singleDir : pathElements) {
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        log.debug("CREATED directory: [{}]", singleDir);
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        log.debug("COULD NOT create directory: [{}]", singleDir);
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
