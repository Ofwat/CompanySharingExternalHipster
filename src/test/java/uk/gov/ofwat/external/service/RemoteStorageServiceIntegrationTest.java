package uk.gov.ofwat.external.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class RemoteStorageServiceIntegrationTest
{
    public final String CONTAINER_NAME = "test_container";

    public void shouldAddAFile(){}

    public void shouldGetAFile(){}



}
