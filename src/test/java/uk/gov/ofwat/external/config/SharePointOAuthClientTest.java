package uk.gov.ofwat.external.config;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SharePointOAuthClientTest {

    @Mock
    HttpClient client;


    @Mock
    HttpResponse response;


    @Mock
    StatusLine statusLine;

    @Mock
    File file;


    @InjectMocks
    SharePointOAuthClient service;

    /**
     * Set up.
     */
    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SharePointOAuthClient();
    }


 /*   @Test
    @Transactional
    public void getSharePointAccessTokenTest() throws IOException, JSONException {
        when(client.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        service.getSharePointAccessToken();
        assertThat(statusLine.getStatusCode()).isEqualTo(200);
    }*/

/*    @Test
    @Transactional
    public void uploadFileToSharePointTest() throws IOException, JSONException {
        when(client.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        service.uploadFileToSharePoint(file);
        assertThat(statusLine.getStatusCode()).isEqualTo(200);
    }*/
}
