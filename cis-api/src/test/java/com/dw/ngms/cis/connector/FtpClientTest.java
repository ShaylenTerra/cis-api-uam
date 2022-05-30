package com.dw.ngms.cis.connector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 18/02/21, Thu
 **/
public class FtpClientTest {

    public FakeFtpServer fakeFtpServer;

    private FtpService ftpService;

    @BeforeEach
    public void setup() throws IOException {

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        UnixFakeFileSystem unixFakeFileSystem = new UnixFakeFileSystem();
        unixFakeFileSystem.add(new DirectoryEntry("/data"));
        unixFakeFileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(unixFakeFileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpService = new FtpService("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
        ftpService.open();

    }

    @AfterEach
    public void teardown() throws IOException {
        ftpService.close();
        fakeFtpServer.stop();
    }

    @Test
    public void givenRemoteFile_whenListingRemoteFiles_thenItIsContainedInList() throws IOException {
        Collection<String> files = ftpService.listFiles("");
        Assertions.assertThat(files).contains("foobar.txt");
    }

    @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
        ftpService.downloadFile("/buz.txt", "downloaded_buz.txt");
        Assertions.assertThat(new File("downloaded_buz.txt")).exists();
        new File("downloaded_buz.txt").delete();
    }


}