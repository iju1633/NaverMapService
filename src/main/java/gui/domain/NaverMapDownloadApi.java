package gui.domain;

import console.domain.NaverMapApi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.CommonsConstant.HTTP_STATE_CODE_200_OK;
import static utils.CommonsConstant.UTF_8;


public class NaverMapDownloadApi {

    private static final String FORMAT_DATE_TIME = "yyyyMMddHHmmss";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_CENTER_KEY = "center=";
    private static final String COMMA = ",";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_LEVEL_VALUE = "&level=16&w=700&h=500";
    private static final String IMAGE_TYPE = ".jpg";
    // 네이버 Static Map API 사용
    private static final String NAVER_MAP_DOWNLOAD_API_URL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
    // raster : 이미지 요청 시 헤더에 ClientId, SecretKey를 추가하여 인증
    // https://jdbc.tistory.com/16 참고
    private static final String INVALID_SERVER_RESPONSE_MESSAGE = "서버와의 응답이 올바르지 않습니다.";


    public File downloadNaverMapImage(Address addressVo) throws IOException {
        File file;
        String address = initNaverMapDownloadApiUrl(addressVo);
        HttpURLConnection httpConnection = NaverMapApi.requestNaverMapApi(address);

        int responseCode = httpConnection.getResponseCode();
        System.out.println(responseCode);

        if (responseCode == HTTP_STATE_CODE_200_OK) { // jpg 파일로 저장하기
            InputStream inputStream = httpConnection.getInputStream();

            byte[] bytes = new byte[1024];
            file = createFile(createFileName());

            OutputStream outputStream = new FileOutputStream(file);
            createImageFile(inputStream, bytes, outputStream);
            inputStream.close();
            outputStream.close();

            return file;
        }
        throw new RuntimeException(INVALID_SERVER_RESPONSE_MESSAGE);
    }

    private void createImageFile(InputStream inputStream, byte[] bytes, OutputStream outputStream) throws IOException {
        int read;
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }

    // https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?center=127.1054221,37.3591614&level=16&w=700&h=500
    private String initNaverMapDownloadApiUrl(Address address) throws UnsupportedEncodingException {
        String position = URLEncoder.encode(address.getX() + " " + address.getY(), UTF_8);
        StringBuilder imageDownloadApiUrl = new StringBuilder();
        imageDownloadApiUrl.append(NAVER_MAP_DOWNLOAD_API_URL)
                .append(NAVER_MAP_DOWNLOAD_API_URL_CENTER_KEY)
                .append(address.getX())
                .append(COMMA)
                .append(address.getY())
                .append(NAVER_MAP_DOWNLOAD_API_URL_LEVEL_VALUE);
        return imageDownloadApiUrl.toString();
    }

    private String createFileName() { // 생성되는 jpg file의 이름은 시각으로 대체
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME));
    }

    private static File createFile(String imageFileName) {
        return new File(imageFileName + IMAGE_TYPE);
    } // 파일 형식은 .jpg

}
