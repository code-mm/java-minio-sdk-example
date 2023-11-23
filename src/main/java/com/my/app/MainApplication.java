package com.my.app;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MainApplication {

    private static void listBuckets() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("V2eI4HdxpJeu1gJN", "6yJVYWUp5YKYY8qPt1YT1zV0pSeOzIfV")
                .build();
        // 列出所有的存储桶
        List<Bucket> buckets = client.listBuckets();
        for (Bucket bucket : buckets) {
            // 打印存储桶名称
            System.out.println(bucket.name());
        }
    }

    private static void makeBucket()throws Exception{
        MinioClient client = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("V2eI4HdxpJeu1gJN", "6yJVYWUp5YKYY8qPt1YT1zV0pSeOzIfV")
                .build();

        client.makeBucket(MakeBucketArgs.builder()
                // 存储桶名称
                .bucket("mytest1")
                .objectLock(true)
                .build());
    }


    private static void putObject()throws Exception{
        MinioClient client = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("V2eI4HdxpJeu1gJN", "6yJVYWUp5YKYY8qPt1YT1zV0pSeOzIfV")
                .build();
        File file = new File("100.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        client.putObject(PutObjectArgs.builder().object(file.getName()).bucket("public").stream(fileInputStream, fileInputStream.available(), 1024 * 1024 * 1024 * 1000).build());

    }


    public static void main(String[] args) throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("V2eI4HdxpJeu1gJN", "6yJVYWUp5YKYY8qPt1YT1zV0pSeOzIfV")
                .build();
        String downloadUrl = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket("public")
                .object("100.txt")
                .build());
        System.out.println(downloadUrl);

    }
}
