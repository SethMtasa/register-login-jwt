package seth.contract.validation;

public enum FileSizeConstants {
    FIVE_MEGABYTES(5242880);

    private final int bytes;

    FileSizeConstants(int bytes) {
        this.bytes = bytes;
    }

    public int bytes(){ return bytes; }
}
