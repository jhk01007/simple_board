package org.example.recipe_board_jpa.dto;

import org.springframework.web.multipart.MultipartFile;

public class WriteDto {

    private String foodName;
    private String process;
    private Long writerId;
//    @Nullable
    private MultipartFile[] images;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
