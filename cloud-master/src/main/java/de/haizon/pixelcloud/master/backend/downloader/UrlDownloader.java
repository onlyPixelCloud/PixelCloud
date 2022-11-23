package de.haizon.pixelcloud.master.backend.downloader;

import de.haizon.pixelcloud.api.backend.DownloadType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class UrlDownloader {

    private final String urlLink;
    private final File file;
    private final DownloadType downloadType;

    public UrlDownloader(String urlLink, File file, DownloadType downloadType) {
        this.urlLink = urlLink;
        this.file = file;
        this.downloadType = downloadType;
    }

    public UrlDownloader(String urlLink, File file) {
        this.urlLink = urlLink;
        this.file = file;
        this.downloadType = DownloadType.UNKNOWN;
    }

    public void download(){
        try {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");

            URL url = new URL(urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
            long fileSize = connection.getContentLengthLong();


            byte[] buffer = new byte[1024];
            int read;
            while((read  = bufferedInputStream.read(buffer, 0, 1024)) >= 0){
                bufferedOutputStream.write(buffer, 0, read);

            }

            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
