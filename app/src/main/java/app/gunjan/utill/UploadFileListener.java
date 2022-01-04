package app.gunjan.utill;

public interface UploadFileListener {
    void onSuccess(String localUrl, String awsUrl);
    void onFailure(String error);
}
