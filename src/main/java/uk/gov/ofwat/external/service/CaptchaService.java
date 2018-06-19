package uk.gov.ofwat.external.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.ofwat.external.config.ApplicationProperties;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaptchaService {

    private final Logger log = LoggerFactory.getLogger(CaptchaService.class);

    private final ApplicationProperties applicationProperties;

    public CaptchaService(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public Boolean verifyCaptcha(String captchaResponse){
        String secret = applicationProperties.getRecaptcha().secret;
        String url = applicationProperties.getRecaptcha().url;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("response", captchaResponse));
        urlParameters.add(new BasicNameValuePair("secret", secret));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse response = client.execute(post);
            String json = EntityUtils.toString(response.getEntity());
            JSONObject result = new JSONObject(json);
            return result.getBoolean("success");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

}
