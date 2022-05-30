package com.dw.ngms.cis.service;

import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.exception.FileNotFoundException;
import com.dw.ngms.cis.exception.FileStorageException;
import com.dw.ngms.cis.utilities.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 * <p>
 * this service used to store file and load them as resource
 * for providing them to end user
 **/
@Service
@Slf4j
public class FileStorageService {


    private final Path storageLocation;

    @Autowired
    public FileStorageService(AppPropertiesConfig appPropertiesConfig) {
        this.storageLocation = Paths.get(appPropertiesConfig.getFileStorage().getStorageLocation())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.storageLocation);
        } catch (Exception ex) {
            log.error("Could not create the directory where the uploaded files will be stored.", ex);
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public String storeFile(final File file, final String targetLocation) {
        if (null == file)
            return null;

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getName());

        try {


            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Create contextCode directory if does not exist
            File directory = new File(this.storageLocation.resolve(targetLocation).toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Copy file to the target location (Replacing existing file with the same name)
            final String fileExtension = FileUtils.getFileExtension(fileName);
            Path finalLocation = this.storageLocation.resolve(targetLocation + FileUtils.EXTENSION_SEPARATOR + fileExtension);
            Files.copy(new FileInputStream(file), finalLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;

        } catch (IOException ex) {
            log.error("Could not store file {} Please try again! ", fileName, ex);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(final MultipartFile file, final String targetLocation) {
        if (null == file)
            return null;
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            // Copy file to the target location (Replacing existing file with the same name)
            final String fileExtension = FileUtils.getFileExtension(fileName);
            Path finalLocation = this.storageLocation.resolve(targetLocation + FileUtils.EXTENSION_SEPARATOR + fileExtension);
            File dir = new File(finalLocation.toString());
            if(!dir.exists())
                dir.mkdirs();

            Files.copy(file.getInputStream(), finalLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            log.error("Could not store file {} Please try again! ", fileName, ex);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Boolean deleteFile(final String location) {

        try {

            final Path resolve = this.storageLocation.resolve(location);
            Files.delete(resolve);
            return true;
        }catch (IOException e) {
            log.error(" exception {} occur while deleting file at path {}  ", e.getMessage(), location);
            return false;
        }

    }

    /**
     * this method takes file name and make them available
     * as resource {@link Resource}
     *
     * @param targetPath fileName
     * @return resource {@link Resource}
     */
    public Resource loadFileAsResource(final String targetPath) {
        try {
            Path filePath = this.storageLocation.resolve(targetPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + targetPath);
            }
        } catch (MalformedURLException ex) {
            log.error("File {} not found ", targetPath, ex);
            throw new FileNotFoundException("File not found " + targetPath, ex);
        }
    }

}
