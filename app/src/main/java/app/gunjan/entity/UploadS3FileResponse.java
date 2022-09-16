package app.gunjan.entity;

import java.io.Serializable;

public class UploadS3FileResponse implements Serializable {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * code : 1
     * message : success
     * data : {"path_data":{"path":"https://media-appsinvo.s3.us-east-2.amazonaws.com/16633104776922833.png","file_type":"image/png"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public PathDataBean getPath_data() {
            return path_data;
        }

        public void setPath_data(PathDataBean path_data) {
            this.path_data = path_data;
        }

        /**
         * path_data : {"path":"https://media-appsinvo.s3.us-east-2.amazonaws.com/16633104776922833.png","file_type":"image/png"}
         */

        private PathDataBean path_data;

        public static class PathDataBean implements Serializable {
            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            /**
             * path : https://media-appsinvo.s3.us-east-2.amazonaws.com/16633104776922833.png
             * file_type : image/png
             */

            private String path;
            private String file_type;
        }
    }
}
