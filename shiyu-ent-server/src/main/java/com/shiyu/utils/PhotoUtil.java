package com.shiyu.utils;

import com.shiyu.entity.repository.Photo;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * photo-manager
 * 2020/8/14 15:29
 *
 * @since
 **/

public abstract class PhotoUtil {

    private static final String IMAGE = "image";

    private static final double THUMB_RATIO = 300.0;

    public static Boolean isPhotoFile(MultipartFile file) {
        String type = file.getContentType().substring(0, file.getContentType().lastIndexOf("/"));
        return IMAGE.equals(type);
    }

    public static String getImageType(MultipartFile file) {
        return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
    }

    /**
     * 保存文件
     *
     * @param multipartFile
     * @param path
     * @return
     * @throws IOException
     */
    public static File saveFile(MultipartFile multipartFile, String path) throws IOException {
        File saveFile = new File(path);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        multipartFile.transferTo(saveFile);
        return saveFile;
    }

    /**
     * 生成文件名
     *
     * @param multipartFile
     * @return
     */
    public static String creatPathName(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        return System.currentTimeMillis() + prefix;
    }

    /**
     * 转变为文件
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static File transToFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File excelFile = File.createTempFile(System.currentTimeMillis() + "", prefix);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
        return excelFile;
    }

    public static Photo getPhotoParams(MultipartFile multipartFile) throws IOException {
        final File excelFile = transToFile(multipartFile);
        Photo params = getPhotoParams(excelFile);
        deleteFile(excelFile);
        return params;
    }

    /**
     * 获取图片参数
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Photo getPhotoParams(File file) throws IOException {
        BufferedImage sourceImg = ImageIO.read(file);
        Photo params = new Photo();
        params.setPictureHeight(sourceImg.getHeight());
        params.setPictureWidth(sourceImg.getWidth());
        params.setImageType(sourceImg.getType());
        params.setSize(file.length());
        params.setFileName(file.getName());
        String typeName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        params.setFileType(typeName);
        params.setPath(file.getAbsolutePath());
        return params;
    }

    public static BufferedImage thumbImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        // Image Itemp = image.getScaledInstance(300, 300, image.SCALE_SMOOTH);
        double ratio = 0.0;
        if ((image.getHeight() > 300) || (image.getWidth() > 300)) {
            //图片要缩放的比例
            if (image.getHeight() > image.getWidth()) {
                ratio = THUMB_RATIO / image.getHeight();
            }
            else {
                ratio = THUMB_RATIO / image.getWidth();
            }
            // 根据仿射转换和插值类型构造一个 AffineTransformOp。
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            // 转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            image = op.filter(image, null);
            //image.getScaledInstance(300,300,image.SCALE_SMOOTH);
        }
        return image;
    }

    public static BufferedImage thumbImage(BufferedImage image) throws IOException {
        // Image Itemp = image.getScaledInstance(300, 300, image.SCALE_SMOOTH);
        double ratio = 0.0;
        if ((image.getHeight() > 300) || (image.getWidth() > 300)) {
            //图片要缩放的比例
            if (image.getHeight() > image.getWidth()) {
                ratio = THUMB_RATIO / image.getHeight();
            }
            else {
                ratio = THUMB_RATIO / image.getWidth();
            }
            // 根据仿射转换和插值类型构造一个 AffineTransformOp。
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            // 转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            image = op.filter(image, null);
            //image.getScaledInstance(300,300,image.SCALE_SMOOTH);
        }
        return image;
    }

    /**
     * 删除文件
     */
    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
