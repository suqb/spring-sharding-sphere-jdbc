package com.suqb.www.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/file")
@CrossOrigin
public class FileController
{

    private static final String FILEPATH = "C:/Users/wumingjie/Downloads/PEWallpaper.jpg";
    private static final String UPLOAD_FILEPATH = "F:/data/";

    @RequestMapping
    public void getFile(HttpServletRequest request, HttpServletResponse response)
    {
        try (RandomAccessFile randomFile = new RandomAccessFile(FILEPATH, "r"))
        {
            long fileLength = randomFile.length();
            String range = request.getHeader("Range");

            long start = 0;
            long end = fileLength - 1;
            if (range != null && range.startsWith("bytes="))
            {
                String[] ranges = range.substring(6).split("-");
                try
                {
                    start = Long.parseLong(ranges[0]);
                    if (ranges.length > 1)
                    {
                        end = Long.parseLong(ranges[1]);
                    }
                }
                catch (NumberFormatException e)
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }

            long contentLength = end - start + 1;
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"picture.xlsx\"");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", Long.toString(contentLength));
            if (range != null)
            {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
            }

            try (ServletOutputStream outputStream = response.getOutputStream())
            {
                randomFile.seek(start);
                byte[] buffer = new byte[1024];
                long bytesToRead = contentLength;
                int bytesRead;
                while ((bytesRead = randomFile.read(buffer, 0, (int) Math.min(buffer.length, bytesToRead))) != -1 && bytesToRead > 0)
                {
                    outputStream.write(buffer, 0, bytesRead);
                    bytesToRead -= bytesRead;
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<?> getData(@RequestParam(value = "type", required = false) String type) throws IOException
    {
        if ("file".equalsIgnoreCase(type))
        {
            File file = new File(FILEPATH);
            try
            {
                InputStream inputStream = Files.newInputStream(file.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.txt");
                headers.add("Access-Control-Expose-Headers", HttpHeaders.CONTENT_DISPOSITION);

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(new InputStreamResource(inputStream));
            }
            finally
            {
                file.delete();
            }

        } else
        {
            // 返回 JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "This is a JSON response");

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @CrossOrigin
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException
    {
        String filename = file.getOriginalFilename();

        byte[] bytes = new byte[1024];
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(UPLOAD_FILEPATH + filename)); InputStream inputStream = file.getInputStream())
        {
            while (inputStream.read(bytes) != -1)
            {
                outputStream.write(bytes);
            }
        }

        return ResponseEntity.ok().build();
    }

}
