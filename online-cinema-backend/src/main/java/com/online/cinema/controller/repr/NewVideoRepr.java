package com.online.cinema.controller.repr;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class NewVideoRepr {

    private String description;

    private MultipartFile file;
}
