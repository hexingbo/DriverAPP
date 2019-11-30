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
        private String IdCardBack;
        private String LifePhoto;
        private String DriverCard;
        private String DriverCardBack;
        private String CarInsurance;
        private String CarAPhoto;
        private String CarBPhoto;
        private String CarCPhoto;
        private String DrivingCard;
        private String DrivingCardBack;

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String idCard) {
            IdCard = idCard;
        }

        public String getIdCardBack() {
            return IdCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            IdCardBack = idCardBack;
        }

        public String getLifePhoto() {
            return LifePhoto;
        }

        public void setLifePhoto(String lifePhoto) {
            LifePhoto = lifePhoto;
        }

        public String getDriverCard() {
            return DriverCard;
        }

        public void setDriverCard(String driverCard) {
            DriverCard = driverCard;
        }

        public String getDriverCardBack() {
            return DriverCardBack;
        }

        public void setDriverCardBack(String driverCardBack) {
            DriverCardBack = driverCardBack;
        }

        public String getCarInsurance() {
            return CarInsurance;
        }

        public void setCarInsurance(String carInsurance) {
            CarInsurance = carInsurance;
        }

        public String getCarAPhoto() {
            return CarAPhoto;
        }

        public void setCarAPhoto(String carAPhoto) {
            CarAPhoto = carAPhoto;
        }

        public String getCarBPhoto() {
            return CarBPhoto;
        }

        public void setCarBPhoto(String carBPhoto) {
            CarBPhoto = carBPhoto;
        }

        public String getCarCPhoto() {
            return CarCPhoto;
        }

        public void setCarCPhoto(String CarCPhoto) {
            CarCPhoto = CarCPhoto;
        }

        public String getDrivingCard() {
            return DrivingCard;
        }

        public void setDrivingCard(String drivingCard) {
            DrivingCard = drivingCard;
        }

        public String getDrivingCardBack() {
            return DrivingCardBack;
        }

        public void setDrivingCardBack(String drivingCardBack) {
            DrivingCardBack = drivingCardBack;
        }
    }

    public static class FileTextInfoBean {
        private IdCardBean IdCard;
        private IdCardBackBean IdCardBack;
        private LifePhotoBean LifePhoto;
        private DriverCardBean DriverCard;
        private DriverCardBackBean DriverCardBack;
        private CarInsuranceBean CarInsurance;
        private CarAPhotoBean CarAPhoto;
        private CarBPhotoBean CarBPhoto;
        private CarCPhotoBean CarCPhoto;
        private DrivingCardBean DrivingCard;
        private DrivingCardBackBean DrivingCardBack;

        public IdCardBean getIdCard() {
            return IdCard;
        }

        public void setIdCard(IdCardBean idCard) {
            IdCard = idCard;
        }

        public IdCardBackBean getIdCardBack() {
            return IdCardBack;
        }

        public void setIdCardBack(IdCardBackBean idCardBack) {
            IdCardBack = idCardBack;
        }

        public LifePhotoBean getLifePhoto() {
            return LifePhoto;
        }

        public void setLifePhoto(LifePhotoBean lifePhoto) {
            LifePhoto = lifePhoto;
        }

        public DriverCardBean getDriverCard() {
            return DriverCard;
        }

        public void setDriverCard(DriverCardBean driverCard) {
            DriverCard = driverCard;
        }

        public DriverCardBackBean getDriverCardBack() {
            return DriverCardBack;
        }

        public void setDriverCardBack(DriverCardBackBean driverCardBack) {
            DriverCardBack = driverCardBack;
        }

        public CarInsuranceBean getCarInsurance() {
            return CarInsurance;
        }

        public void setCarInsurance(
                CarInsuranceBean carInsurance) {
            CarInsurance = carInsurance;
        }

        public CarAPhotoBean getCarAPhoto() {
            return CarAPhoto;
        }

        public void setCarAPhoto(
                CarAPhotoBean carAPhoto) {
            CarAPhoto = carAPhoto;
        }

        public CarBPhotoBean getCarBPhoto() {
            return CarBPhoto;
        }

        public void setCarBPhoto(
                CarBPhotoBean carBPhoto) {
            CarBPhoto = carBPhoto;
        }

        public DrivingCardBean getDrivingCard() {
            return DrivingCard;
        }

        public void setDrivingCard(
                DrivingCardBean drivingCard) {
            DrivingCard = drivingCard;
        }

        public static class IdCardBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
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

        public static class IdCardBackBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "IdCardBackBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class LifePhotoBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "LifePhotoBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class DriverCardBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "DriverCardBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class DriverCardBackBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "DriverCardBackBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class CarInsuranceBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "DriverCardBackBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class CarAPhotoBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "CarAPhotoBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class CarBPhotoBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "CarBPhotoBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }
        public static class CarCPhotoBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "CarCPhotoBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class DrivingCardBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "DrivingCardBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
        }

        public static class DrivingCardBackBean {
            private String msg;
            private String code;
            private String data;
            private boolean isSuccess;

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

            public boolean isSuccess() {
                return isSuccess;
            }

            public void setSuccess(boolean success) {
                isSuccess = success;
            }

            @Override
            public String toString() {
                return "DrivingCardBackBean{" +
                        "msg='" + msg + '\'' +
                        ", code='" + code + '\'' +
                        ", data='" + data + '\'' +
                        ", isSuccess='" + isSuccess + '\'' +
                        '}';
            }
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
