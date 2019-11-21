package com.lesso.module.me.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/21
 * @FileName： UploadHeadFileResultBean
 * @Describe :
 */
public class UploadCardFileResultBean {

    private FileTextInfoBean fileTextInfo;
    private String uploadFails;
    private FilePathInfoBean filePathInfo;
    private String uploadSuccess;

    public static class FilePathInfoBean {
        private String IdCard;

        private IdCardBackBean IdCardBack;

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String idCard) {
            IdCard = idCard;
        }

        public IdCardBackBean getIdCardBack() {
            return IdCardBack;
        }

        public void setIdCardBack(IdCardBackBean idCardBack) {
            IdCardBack = idCardBack;
        }

        @Override
        public String toString() {
            return "FilePathInfoBean{" +
                    "IdCard='" + IdCard + '\'' +
                    ", IdCardBack=" + IdCardBack +
                    '}';
        }

        public static class IdCardBackBean{
            private Data data;
            private boolean isSuccess;

            public static class Data{
                private String Nation;
                private String Address;
                private String RequestId;
                private String Sex;
                private String IdNum;
                private String Birth;
                private String Name;

                public String getNation() {
                    return Nation;
                }

                public void setNation(String nation) {
                    Nation = nation;
                }

                public String getAddress() {
                    return Address;
                }

                public void setAddress(String address) {
                    Address = address;
                }

                public String getRequestId() {
                    return RequestId;
                }

                public void setRequestId(String requestId) {
                    RequestId = requestId;
                }

                public String getSex() {
                    return Sex;
                }

                public void setSex(String sex) {
                    Sex = sex;
                }

                public String getIdNum() {
                    return IdNum;
                }

                public void setIdNum(String idNum) {
                    IdNum = idNum;
                }

                public String getBirth() {
                    return Birth;
                }

                public void setBirth(String birth) {
                    Birth = birth;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String name) {
                    Name = name;
                }

                @Override
                public String toString() {
                    return "Data{" +
                            "Nation='" + Nation + '\'' +
                            ", Address='" + Address + '\'' +
                            ", RequestId='" + RequestId + '\'' +
                            ", Sex='" + Sex + '\'' +
                            ", IdNum='" + IdNum + '\'' +
                            ", Birth='" + Birth + '\'' +
                            ", Name='" + Name + '\'' +
                            '}';
                }
            }

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "IdCardBackBean{" +
                        "data='" + data + '\'' +
                        ", isSuccess=" + isSuccess +
                        '}';
            }
        }


    }

    public static class FileTextInfoBean {
        private IdCardBean IdCard;

        public IdCardBean getIdCard() {
            return IdCard;
        }

        public void setIdCard(IdCardBean idCard) {
            IdCard = idCard;
        }


        public static class IdCardBean {
            private String msg;
            private String code;
            private String data;
            private String isSuccess;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getIsSuccess() {
                return isSuccess;
            }

            public void setIsSuccess(String isSuccess) {
                this.isSuccess = isSuccess;
            }

            @Override
            public String toString() {
                return "IdCardBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }
        @Override
        public String toString() {
            return "FileTextInfoBean{" +
                    "IdCard='" + IdCard + '\'' +
                    '}';
        }
    }
    public FileTextInfoBean getFileTextInfo() {
        return fileTextInfo;
    }

    public void setFileTextInfo(FileTextInfoBean fileTextInfo) {
        this.fileTextInfo = fileTextInfo;
    }

    public String getUploadFails() {
        return uploadFails;
    }

    public void setUploadFails(String uploadFails) {
        this.uploadFails = uploadFails;
    }

    public FilePathInfoBean getFilePathInfo() {
        return filePathInfo;
    }

    public void setFilePathInfo(FilePathInfoBean filePathInfo) {
        this.filePathInfo = filePathInfo;
    }

    public String getUploadSuccess() {
        return uploadSuccess;
    }

    public void setUploadSuccess(String uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    @Override
    public String toString() {
        return "UploadCardFileResultBean{" +
                "fileTextInfo=" + fileTextInfo +
                ", uploadFails='" + uploadFails + '\'' +
                ", filePathInfo=" + filePathInfo +
                ", uploadSuccess='" + uploadSuccess + '\'' +
                '}';
    }
}
