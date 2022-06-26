package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.model.FileInfo;
import ShoppingCartsAPI.carts.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/file-Upload")
public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/files")
    public String uploadFile(@RequestParam("files") MultipartFile multipartFile) {
        return fileStorageService.store(multipartFile);
    }

    @GetMapping("/listFiles")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileStorageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.
                    fromMethodName(FileUploadController.class, "getFile", path.getFileName()
                            .toString()).build().toString();

            return new FileInfo(fileName, url);
        }).collect(Collectors.toList());

        Stream<Path> readFiles = fileStorageService.loadAll();
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"ATTACHMENT; filename =\""+
                file.getFilename()).body(file);
}


}
