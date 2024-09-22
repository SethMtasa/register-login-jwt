package seth.contract.dto.contract;

public class FileMetaData {
    private final String name;
    private final String contentType;
    private final String directory;

    public FileMetaData(String name, String contentType, String directory) {
        this.name = name;
        this.contentType = contentType;
        this.directory = directory;
    }

    public String getName() { return name; }

    public String getContentType() { return contentType; }

    public String getDirectory() { return directory; }
}
