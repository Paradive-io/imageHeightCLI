package com.height.image.calculate.image.height.by.cli.controller;

import com.height.image.calculate.image.height.by.cli.dto.RequestImageDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
@RequestMapping("/image")
public class ImageController {

    @SneakyThrows
    @PostMapping({"/calculate-height"})
    public int calculateImageHeight(@RequestBody RequestImageDto requestImageDto) {
        System.out.println("Calculating image height for image: " + requestImageDto.getImagePath());
        String captureCommand = """
                    capture-website --width 320 %s --output=%s.jpg --full-page
                """.formatted(requestImageDto.getImagePath(), requestImageDto.getSaveImagePath());
        System.out.println(captureCommand);

        if(!isFileExist(requestImageDto.getSaveImagePath()+".jpg")) {
            executeNpmCommand(captureCommand);
        }
//        getWebImageHeight(requestImageDto.getImagePath());

        return getImageHeight(requestImageDto.getSaveImagePath()+".jpg");

    }

    private boolean isFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    private static void executeNpmCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getImageHeight(String imagePath) throws IOException {
        try {
            // 이미지 읽기
            System.out.println(imagePath);
            BufferedImage image = ImageIO.read(new File(imagePath));

            // 이미지 처리 로직 추가
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("width = " + width + ", height = " + height);

            return height/2;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //lazy loading -> 읽을수 없음
    private void getWebImageHeight(String imagePath) {
        try {
            URL url = new URL(imagePath);
            Image image = ImageIO.read(url);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            System.out.println("width = " + width + ", height = " + height);
        } catch (Exception e) {
            System.out.println("파일이 없습니다.");

        }
    }
}
