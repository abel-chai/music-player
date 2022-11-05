export function beforeImgUpload(file) {
    const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);
    const ltCode = 2;
    const isLt2M = file.size / 1024 / 1024 < ltCode;
    const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

    if (!isExistFileType) {
      this.$message.error(`图片只支持 ${uploadTypes.value.join("、")} 格式!`);
    }
    if (!isLt2M) {
      this.$message.error(`上传头像图片大小不能超过 ${ltCode}MB!`);
    }
    
    return isExistFileType && isLt2M;
}

export function beforeSongUpload(file) {
    
    
    function beforeSongUpload(file) {
        
    }
}