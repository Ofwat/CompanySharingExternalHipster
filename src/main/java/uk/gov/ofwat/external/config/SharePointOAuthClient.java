package uk.gov.ofwat.external.config;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SharePointOAuthClient {

    private final Logger logger = LoggerFactory.getLogger(SharePointOAuthClient.class);


    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${security.oauth2.client.grantType}")
    private String grantType;

    @Value("${security.oauth2.client.resource}")
    private String resource;

    @Value("${security.oauth2.client.url}")
    private String url;

    @Value("${security.oauth2.client.uploadFolder}")
    private String uploadFolder;

    public String getSharePointAccessToken() throws IOException, JSONException {


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", grantType));
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("resource", resource));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        logger.debug("Response Code : " + response.getStatusLine().getStatusCode());
        String json_string = EntityUtils.toString(response.getEntity());
        JSONObject temp1 = new JSONObject(json_string);
        if (temp1 != null)
            return temp1.get("access_token").toString();

        return Constants.OAUTH_FAIL_MESSAGE;
    }

    public String uploadFileToSharePoint(File file) throws IOException, JSONException {

        String token = getSharePointAccessToken();

        if (!token.equalsIgnoreCase(Constants.OAUTH_FAIL_MESSAGE)) {

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://ofwat.sharepoint.com/sites/rms/pr-test/_api/web/lists/GetByTitle('DCS')/RootFolder/Files/Add(url='"+file.getName()+"',overwrite=true)");
                post.setHeader("Authorization", "Bearer " + token);
            post.setHeader("Accept", "application/json;odata=verbose");
            post.setHeader("X-RequestDigest", "0x08FFC8FC2CA1FDD7D6D93D4BB87C2E0D65CA904327404A7DD44B8C6B0450D3B352C54E04D51C7D4B34F9FC391A9311DD304B06214B49952751716FEFAA1D8420");
            post.setEntity(new FileEntity(file));
            HttpResponse response = client.execute(post);
            logger.debug("Response Code : " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value() || response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
                return Constants.UPLOAD_SUCCESS_MESSAGE;
            } else {
                   return Constants.UPLOAD_FAIL_MESSAGE;
            }
        }
        return token;
    }

}
