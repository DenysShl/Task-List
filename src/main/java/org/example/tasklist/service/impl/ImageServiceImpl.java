package org.example.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.model.TaskImage;
import org.example.tasklist.service.ImageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Override
    public String upload(TaskImage image) {
        return "";
    }
}
