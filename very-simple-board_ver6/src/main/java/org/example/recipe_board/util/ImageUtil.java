package org.example.recipe_board.util;

import org.example.recipe_board.domain.Image;
import org.example.recipe_board.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageUtil {
    public static List<Image> saveImages(MultipartFile[] uploadFile) throws IOException {
        List<Image> imageList = new ArrayList<>();
        if (uploadFile != null && uploadFile.length > 0) { // 첨부된 파일이 있는 경우 저장 절차 진행
            // 웹 접근 경로와 파일 시스템 경로 설정
            String uploadPath = "/Users/kimjaehee/Desktop/aaabbb/";
            if (!new File(uploadPath).exists()) {
                new File(uploadPath).mkdir(); // 폴더가 없으면 생성
            }

            for (MultipartFile f : uploadFile) {
                String savedName = UUID.randomUUID() + "_" + f.getOriginalFilename();
                File savedFile = new File(uploadPath + savedName);

                f.transferTo(savedFile); // 파일을 서버에 저장

                Image savedFileInfo = new Image();
                savedFileInfo.setSavedPath(uploadPath + savedName);
                savedFileInfo.setOriginalName(f.getOriginalFilename());

                imageList.add(savedFileInfo);
            }
        }
        return imageList;
    }
}
