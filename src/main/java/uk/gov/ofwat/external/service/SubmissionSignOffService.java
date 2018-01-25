package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.SubmissionSignOff;
import uk.gov.ofwat.external.repository.SubmissionSignOffRepository;
import uk.gov.ofwat.external.service.dto.SubmissionSignOffDTO;
import uk.gov.ofwat.external.service.mapper.SubmissionSignOffMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SubmissionSignOff.
 */
@Service
@Transactional
public class SubmissionSignOffService {

    private final Logger log = LoggerFactory.getLogger(SubmissionSignOffService.class);

    private final SubmissionSignOffRepository submissionSignOffRepository;

    private final SubmissionSignOffMapper submissionSignOffMapper;

    public SubmissionSignOffService(SubmissionSignOffRepository submissionSignOffRepository, SubmissionSignOffMapper submissionSignOffMapper) {
        this.submissionSignOffRepository = submissionSignOffRepository;
        this.submissionSignOffMapper = submissionSignOffMapper;
    }

    /**
     * Save a submissionSignOff.
     *
     * @param submissionSignOffDTO the entity to save
     * @return the persisted entity
     */
    public SubmissionSignOffDTO save(SubmissionSignOffDTO submissionSignOffDTO) {
        log.debug("Request to save SubmissionSignOff : {}", submissionSignOffDTO);
        SubmissionSignOff submissionSignOff = submissionSignOffMapper.toEntity(submissionSignOffDTO);
        submissionSignOff = submissionSignOffRepository.save(submissionSignOff);
        return submissionSignOffMapper.toDto(submissionSignOff);
    }

    /**
     *  Get all the submissionSignOffs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SubmissionSignOffDTO> findAll() {
        log.debug("Request to get all SubmissionSignOffs");
        return submissionSignOffRepository.findAll().stream()
            .map(submissionSignOffMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one submissionSignOff by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SubmissionSignOffDTO findOne(Long id) {
        log.debug("Request to get SubmissionSignOff : {}", id);
        SubmissionSignOff submissionSignOff = submissionSignOffRepository.findOne(id);
        return submissionSignOffMapper.toDto(submissionSignOff);
    }

    /**
     *  Delete the  submissionSignOff by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubmissionSignOff : {}", id);
        submissionSignOffRepository.delete(id);
    }
}
