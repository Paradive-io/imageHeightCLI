package com.height.image.calculate.image.height.by.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestImageDto {

    private String imagePath;
    private String saveImagePath;

    public RequestImageDto() {
    }

    public RequestImageDto(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSaveImagePath() {
        String[] split = this.imagePath.split("\\/");
        return split[split.length - 1];
    }

    @Override
    public String toString() {
        return "RequestImageDto{" +
                "imagePath='" + imagePath + '\'' +
                '}';
    }
}
