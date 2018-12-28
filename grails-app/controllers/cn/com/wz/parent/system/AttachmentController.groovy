package cn.com.wz.parent.system
/**
 * @Description二维码附件控制器类
 * @CreateTIme 2015-05-21 zhuwei
 */
class AttachmentController {
    def grailsApplication
    /**
     * 显示图片，在上传图片然后显示图片缩略图处使用
     * @return
     */
    def getImg() throws IOException {
//        def picPath=params.picPath
//        if(params.id){
//            UFile ufile = UFile.get(params.id)
//            if (!ufile) {
//                def msg = messageSource.getMessage("fileupload.download.nofile", [params.id] as Object[], request.locale)
//                log.debug msg
//                flash.message = msg
//                redirect controller: params.errorController, action: params.errorAction
//                return
//            }
//            picPath=ufile.path
//        }
        def picPath=params.QRpiUrl
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/png"); // 设置图片格式格式，这里不可以忽略
        FileInputStream fis = new FileInputStream(picPath);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
        return null;
    }

}
