package com.shiyu.utils;

import com.shiyu.entity.repository.Video;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * photo-manager
 * 2020/8/14 15:29
 *
 * @since
 **/

public abstract class VideoUtil {

    // 获取要取得的帧数
    private static final int GET_FRAMES_LENGTH = 100;

    private static final String IMAGE = "image";

    public static Boolean isPhotoFile(MultipartFile file) {
        String type = file.getContentType().substring(0, file.getContentType().lastIndexOf("/"));
        return IMAGE.equals(type);
    }

    public static String getImageType(MultipartFile file) {
        return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
    }

    /**
     * 保存文件
     * @param multipartFile
     * @param path
     * @return
     * @throws IOException
     */
    public static File saveFile(MultipartFile multipartFile, String path) throws IOException {
        File saveFile = new File(path);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        multipartFile.transferTo(saveFile);
        return saveFile;
    }

    /**
     * 生成文件名
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


    /**
     * 获取图片参数
     * @param file
     * @return
     * @throws IOException
     */
    public static Video getVideoParams(File file) {
//        BufferedReader videoSource = new BufferedReader(new FileReader(file));
        FFmpegFrameGrabber grabber;
        Video params = new Video();
        try {
            grabber = FFmpegFrameGrabber.createDefault(file);
            grabber.start();
            params.setDuration(grabber.getLengthInTime());
            params.setHeight(grabber.getImageHeight());
            params.setWidth(grabber.getImageWidth());
            params.setVideoType(grabber.getVideoCodec());
            grabber.getLengthInTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.setSize(file.length());
        params.setFileName(file.getName());
        String typeName = file.getName().substring(file.getName().lastIndexOf(".")+1);
        params.setFileType(typeName);
        params.setPath(file.getAbsolutePath());
        return params;
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

    /**
     * 获取一张视频截图
     *
     * @param filePath 视频文件地址
     * @return
     */
    public static BufferedImage getScreenshot(String filePath) {
        FFmpegFrameGrabber grabber;
        try {
            grabber = FFmpegFrameGrabber.createDefault(filePath);
            grabber.start();
            // 视频总帧数
            int videoLength = grabber.getLengthInFrames();
            Frame frame = null;
            int i = 0;
            while (i < videoLength) {
                // 过滤前5帧,避免出现全黑的图片,依自己情况而定(每循环一次取一帧)
                frame = grabber.grabFrame();
                if ((i > GET_FRAMES_LENGTH) && (frame.image != null)) {
                    break;
                }
                i++;
            }
            // 视频旋转度
//            String rotate = grabber.getVideoMetadata("rotate");
            Java2DFrameConverter converter = new Java2DFrameConverter();
            // 绘制图片
            BufferedImage bi = converter.getBufferedImage(frame);
            grabber.stop();
            return bi;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
