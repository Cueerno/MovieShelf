package com.radiuk.movieshelfbackendcore.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.radiuk.movieshelfbackendcore.config.CloudinaryProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final CloudinaryProperties properties;

    public CloudinaryService(@Qualifier("cloudinaryProperties") CloudinaryProperties properties) {
        this.properties = properties;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", properties.getCloudName(),
                "api_key", properties.getApiKey(),
                "api_secret", properties.getApiSecret()
        ));
    }

    public String uploadAvatar(MultipartFile file, Long userId) throws IOException {
        String publicId = properties.getFolder() + "/" + userId;

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        ));

        return (String) uploadResult.get("secure_url");
    }
}
