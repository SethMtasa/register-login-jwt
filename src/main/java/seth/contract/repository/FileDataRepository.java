package seth.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.FileData;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Long> {
    Optional<FileData> findByName(String fileName);
}