package seth.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.ImageData;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);
}