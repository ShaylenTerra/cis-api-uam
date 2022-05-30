package com.dw.ngms.cis.connector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 18/02/21, Thu
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Slf4j
public class FtpService {

    @Value("${app.ftp.server}")
    private String server;

    @Value("${app.ftp.port}")
    private Integer port;

    @Value("${app.ftp.user}")
    private String user;

    @Value("${app.ftp.password}")
    private String password;

    private FTPClient ftpClient;

    public FtpService(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }


    public void downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        ftpClient.retrieveFile(source, out);
    }

    public Collection<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftpClient.listFiles(path);
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    /**
     * @throws IOException ioexception {@link IOException}
     */
    public void open() throws IOException {

        ftpClient = new FTPClient();

        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftpClient.connect(server, port);
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftpClient.login(user, password);

    }

    /**
     * @throws IOException ioexception {@link IOException}
     */
    public void close() throws IOException {
        ftpClient.disconnect();
    }

    public void logout() throws IOException {
        ftpClient.logout();
    }

    /**
     * @param file file to be upload
     * @param path path where file need to be upload
     * @throws IOException ioexception {@link IOException}
     */
    public void putFileToPath(File file, String path) throws IOException {
        String fileLocation = path + file.getName();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.storeFile(fileLocation, new FileInputStream(file));
    }

    public void putFileToDatedPath(File file, String path) throws IOException {
        String fileLocation = path + File.separator + file.getName();
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile(fileLocation, new FileInputStream(file));
    }

    public void createDirectory(String path) throws IOException {
        ftpClient.makeDirectory(path);
    }

    public void createDirectories(String path) throws IOException {
        FTPUtils.makeDirectories(ftpClient, path);
    }

    public void putResourceToPath(Resource resource, String path) {
        try {

            String fileLocation = path + File.separator + resource.getFilename();
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileLocation, resource.getInputStream());

        } catch (IOException e) {
          log.error(" error occurred while sending resource to ftp path [{}]", path);
          e.printStackTrace();
        }
    }

}
