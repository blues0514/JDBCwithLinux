package exceptions;

import java.time.LocalDateTime;

public class WrongUpdateException extends RuntimeException{
    private int trackId;
    private LocalDateTime at;

    public WrongUpdateException(int trackId, LocalDateTime at) {
        this.trackId = trackId;
        this.at = at;
    }

    public WrongUpdateException(String message, int trackId, LocalDateTime at) {
        super(message);
        this.trackId = trackId;
        this.at = at;
    }

    public int getTrackId() {
        return trackId;
    }

    public LocalDateTime getAt() {
        return at;
    }
}
