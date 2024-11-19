package seth.contract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.dto.user.ApiResponse;
import seth.contract.exception.FileDownloadException;
import seth.contract.exception.FileUploadException;
import seth.contract.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService service;

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }


    @PostMapping("/fileSystem")
    public ResponseEntity<ApiResponse<String>> uploadImageToFIleSystem(@RequestParam("file") MultipartFile file) {
        try {
            String uploadResult = service.uploadFileToFileSystem(file);
            return ResponseEntity.ok(new ApiResponse<>(true, "Success", uploadResult));
        } catch (FileUploadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<ApiResponse<byte[]>> downloadImageFromFileSystem(@PathVariable String fileName) {
        try {
            byte[] imageData = service.downloadFileFromFileSystem(fileName);
            return ResponseEntity.ok(new ApiResponse<>(true, "Success", imageData));
        } catch (FileDownloadException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }


}
