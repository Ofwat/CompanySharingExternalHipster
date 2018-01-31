package uk.gov.ofwat.external.service;

import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.service.mapper.DataCollectionMapper;
import static org.hamcrest.CoreMatchers.*;
import javax.transaction.Transactional;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class DataCollectionServiceIntTest {

    @Autowired
    DataCollectionService dataCollectionService;

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    @Autowired
    DataCollectionMapper dataCollectionMapper;

    @Autowired
    PublishingStatusRepository publishingStatusRepository;

    @Autowired
    UserRepository userRepository;

    DataBundleService dataBundleService;

    DataCollection dataCollection;
    DataCollection retrievedCollection;

    @Before
    public void setup(){
        this.dataCollectionService = new DataCollectionService(dataCollectionRepository, dataCollectionMapper, publishingStatusRepository, dataBundleService);
    }

    @Test
    @Transactional
    public void shouldRetrieveDataCollectionById(){
        Given:
        {
            User user = userRepository.findOneByLogin("admin").get();
            dataCollection = new DataCollection();
            dataCollection.setDescription("testDesc");
            dataCollection.setGuidance("testGuidance");
            dataCollection.setName("testDC");
            dataCollection.setOwner(user);
            dataCollection.setPublishingStatus(publishingStatusRepository.findOneByStatus("DRAFT").get());
            dataCollection.setReviewer(user);
            dataCollection.setCreatedBy(user.getLogin());
            dataCollection.setCreatedDate(Calendar.getInstance().toInstant());
            dataCollectionRepository.save(dataCollection);
        }
        When:{
            retrievedCollection = dataCollectionRepository.findOne(dataCollection.getId());
        }
        Then:
        {
            assertThat(retrievedCollection, is(notNullValue()));
        }
    }

    @Test
    public void shouldRetrieveDataCollectionByName(){
        Given:
        {
            User user = userRepository.findOneByLogin("admin").get();
            dataCollection = new DataCollection();
            dataCollection.setDescription("testDesc");
            dataCollection.setGuidance("testGuidance");
            dataCollection.setName("testDC");
            dataCollection.setOwner(user);
            dataCollection.setPublishingStatus(publishingStatusRepository.findOneByStatus("DRAFT").get());
            dataCollection.setReviewer(user);
            dataCollection.setCreatedBy(user.getLogin());
            dataCollection.setCreatedDate(Calendar.getInstance().toInstant());
            dataCollectionRepository.save(dataCollection);
        }
        When:{
            retrievedCollection = dataCollectionRepository.findOneByName(dataCollection.getName());
        }
        Then:
        {
            assertThat(retrievedCollection, is(notNullValue()));
        }
    }

}
