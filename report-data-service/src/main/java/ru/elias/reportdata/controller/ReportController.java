package ru.elias.reportdata.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.elias.reportdata.domain.ReportFormat;
import ru.elias.reportdata.service.MinioService;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final MinioService minioService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadReport(@RequestPart("file") MultipartFile file) {
        try {
            return minioService.uploadFile(file.getBytes());
        } catch (Exception e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getReport(@PathVariable String fileName, @RequestParam ReportFormat format) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName + format.getExtension())
                .build());
        var file = minioService.getFile(fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(file);
    }

}
