package org.myjerry.web.multipart;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartException;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class StreamingMultipartFile implements MultipartFile {


    private FileItemStream item;
    private long size = -1;
    byte[] bytes;

    public StreamingMultipartFile(FileItemStream item) throws IOException {

        this.item = item;
        getBytes();

    }


    public String getName() {
        return item.getName();
    }

    public String getOriginalFilename() {
        return item.getFieldName();
    }

    public String getContentType() {
        return item.getContentType();
    }

    public boolean isEmpty() {
        return false; // TODO
    }

    public long getSize() {
        if (size > 0) {
            try {
                return getBytes().length;
            } catch (IOException e) {

                throw new MultipartException("Something went wrong here");
            }

        }
        return size;
    }

    public byte[] getBytes() throws IOException {
        if(bytes == null) {
            bytes =  IOUtils.toByteArray(item.openStream());
        }

        return bytes;


    }

    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("not possible");

    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("not possible");
    }
}