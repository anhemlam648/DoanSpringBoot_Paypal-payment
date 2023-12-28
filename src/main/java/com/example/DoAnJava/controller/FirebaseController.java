package com.example.DoAnJava.controller;

import com.example.DoAnJava.services.FirebaseService;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class FirebaseController {
    @Autowired
    private FirebaseService firebaseService;

    // TODO: Upload one file
    // trả về kiểu dữ liệu xử lý là String truyền tham số file chưa thông tin tệp tải lên từ MultipartFile
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); //để lấy tên gốc từ original name của MultipartFile
        String contentType = file.getContentType();// file chứa thông tin tệp được tải lên là một đối tượng của MultipartFile
        String storageFileName = UUID.randomUUID().toString() + "_" + fileName; // sử dụng để tạo một tên tệp tin duy nhất cho mục đích lưu trữ hoặc xử lý tệp tin.
        // UUID xác định ko trùng lặp,UUID.randomUUID() tạo ra một UUID mới.

        // tạo ra một BlobId với tên storageFileName cho đối tượng lưu trữ trong bucket có tên là "filetrasua.appspot.com".
        BlobId blobId = BlobId.of("filetrasua.appspot.com", storageFileName);//sử dụng Google Cloud Storage (GCS) để xác định BlobId và BlobInfo cho một đối tượng lưu trữ trong GCS.
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId) //BlobId là một đối tượng đại diện cho định danh duy nhất của một đối tượng lưu trữ trong Google Cloud Storage .
                .setContentType(contentType) //setContentType(contentType) được sử dụng để đặt kiểu nội dung (content type) cho đối tượng lưu trữ
                .build(); // kết thúc việc xây dựng BlobInfo

        // Cấu hình StorageClient và lấy đối tượng Storage
        Storage storage = StorageClient.getInstance().bucket().getStorage();// trả về một đối tượng StorageClient, một client cho các dịch vụ của Google Cloud Storage.
        storage.create(blobInfo, file.getBytes());

        // Lấy đối tượng Blob từ GCS
        Blob blob = storage.get(blobId);
        // Ký một URL có thời hạn (signed URL) cho việc truy cập đối tượng
        URL signedUrl = blob.signUrl(2000, TimeUnit.DAYS);

        // Chuyển đổi signedUrl thành chuỗi
        String fileUrl = signedUrl.toString();
        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    }

    //tải tệp  lên phía máy chủ
    @PostMapping("/upload-multi")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("file") List<MultipartFile> files) throws IOException {

        var fileUrls = firebaseService.uploadImages(files);// Gọi phương thức uploadImages của đối tượng firebaseService.
        return new ResponseEntity<>(fileUrls, HttpStatus.OK);//chứa danh sách các URL của các tệp tin đã được tải lên, với mã trạng thái HTTP là HttpStatus.OK.
    }
}
