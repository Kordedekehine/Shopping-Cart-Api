package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.config.StorageProperties;
import ShoppingCartsAPI.carts.exceptions.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private StorageProperties properties = new StorageProperties();

    Path rootLocation = Paths.get(properties.getLocation()); //trace the upload directory

    public String store(MultipartFile multipartFile){
        try {
            if (multipartFile.isEmpty()){
                throw new StorageException("failed to store empty file");
            }
             /**
             * General file name and file(FilenameUtils) path manipulation utilities.
             * When dealing with file names you can hit problems when moving from a Windows based development machine to a
             * Unix based production machine.This class aims to help avoid those problems.
             */
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String uploadedFilenames = UUID.randomUUID().toString() + " . " + extension;

            Path fileDestination = rootLocation.resolve(Paths.get(uploadedFilenames)).normalize()
                    .toAbsolutePath();

            try(InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream,fileDestination, StandardCopyOption.REPLACE_EXISTING);

                final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

                return baseUrl+"/fileUpload/files/"+uploadedFilenames;
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file ", e);
        }
    }

//   public Stream<Path> loadAll(){
//        try{
//            return Files.walk(this.rootLocation,1).filter(path -> !path.equals(this.rootLocation))
//                    .map(this.rootLocation::relativize);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//   }

    public Stream<Path> loadAll() { //TABNINE
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    public Resource load(String filename){
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error : "+ e.getMessage());
        }
    }

}
