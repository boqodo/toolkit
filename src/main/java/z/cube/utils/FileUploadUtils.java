package z.cube.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KingSoft on 2014/6/14.
 */
public class FileUploadUtils {
    private static String[] PHOTO_TYPE = {".gif", ".png", ".jpg", ".jpeg",".bmp"};


    /**
     * 得到文件上传的相对路径
     *
     * @param fileName
     *            文件名
     * @return
     */
    public static String getUploadPath(String fileName, long time) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String uploadPath = "upload/" + formater.format(new Date()) + "/"
                + time + FilenameUtils.EXTENSION_SEPARATOR_STR+FilenameUtils.getExtension(fileName);
        return uploadPath;
    }
    public static File getRealSaveFile(String filepath){
        try {
            File webapp = ResourceUtils.getFile("classpath:../../");
            File uploadFile=new File(webapp.getPath()+File.separator+filepath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            return uploadFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("未发现相应的文件(%s)!",filepath));
        }
    }


    public static  boolean isFileType(String fileName, String[] typeArray) {
        for (String type : typeArray) {
            if (fileName.toLowerCase().endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPhotoFileType(String fileName){
        return isFileType(fileName,PHOTO_TYPE);
    }
}
