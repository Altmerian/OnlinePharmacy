package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Auxiliary class for specifying what to do after handling command from request
 */
public class Path {
    private boolean isForward;
    private String url;

    public Path(boolean isForward, String url) {
        this.isForward = isForward;
        this.url = url;
    }

    public Path() {
    }

    public boolean isForward() {
        return isForward;
    }

    public void setForward(boolean forward) {
        isForward = forward;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
