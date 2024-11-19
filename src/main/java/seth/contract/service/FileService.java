package seth.contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.exception.FileDownloadException;
import seth.contract.exception.FileUploadException;
import seth.contract.model.FileData;
import seth.contract.model.ImageData;
import seth.contract.repository.FileDataRepository;
import seth.contract.repository.ImageRepository;
import seth.contract.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Value("${file.upload.path}")
    private String FOLDER_PATH;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(FileUtils.compressFile(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images= FileUtils.decompressFile(dbImageData.get().getImageData());
        return images;
    }

    public String uploadFileToFileSystem(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new FileUploadException("Error: No file provided");
            }

            String fileName = file.getOriginalFilename();
            String filePath = FOLDER_PATH + fileName;
//            File dest = new File(filePath);
            Path dest = Paths.get(filePath);

            // Check if file already exists
            if (Files.exists(dest)) {
                throw new FileUploadException("Error: File with that name already exists");
            }

            FileData fileData = FileData.builder()
                    .name(fileName)
                    .type(file.getContentType())
                    .filePath(filePath)
                    .build();

            fileData = fileDataRepository.save(fileData);
//            file.transferTo(dest);
            Files.write(dest, file.getBytes());

            if (fileData != null && fileData.getId() != null) {
                return "file uploaded successfully: " + filePath;
            } else {
                throw new FileUploadException("Error: Failed to upload file to database");
            }
        } catch (IOException e) {
            throw new FileUploadException("Error uploading file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new FileUploadException("Error: An unexpected error occurred during upload: " + e.getMessage(), e);
        }
    }


    public byte[] downloadFileFromFileSystem(String fileName) {
        try {
            Optional<FileData> fileData = fileDataRepository.findByName(fileName);
            if (fileData.isEmpty()) {
                throw new FileDownloadException("Error: File not found");
            }

            String filePath = fileData.get().getFilePath();
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileDownloadException("Error: File not found on the file system");
            }
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileDownloadException("Error downloading file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new FileDownloadException("Error: An unexpected error occurred during download: " + e.getMessage(), e);
        }
    }
}
