export default function() {
    const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);

    function beforeImgUpload(file) {
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
    
    function beforeSongUpload(file) {
        const ltCode = 10;
        const isLt10M = file.size / 1024 / 1024 < ltCode;
        const testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
        const extension = testmsg === "mp3";
    
        if (!extension) {
          this.$message({
            message: "上传文件只能是mp3格式!",
            type: "error",
          });
        }
        if (!isLt10M) {
          this.$message.error(`上传头像图片大小不能超过 ${ltCode}MB!`);
        }
    
        return extension && isLt10M;
      }
}