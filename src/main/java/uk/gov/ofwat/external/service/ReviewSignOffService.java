package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.ReviewSignOff;
import uk.gov.ofwat.external.repository.ReviewSignOffRepository;
import uk.gov.ofwat.external.service.dto.ReviewSignOffDTO;
import uk.gov.ofwat.external.service.mapper.ReviewSignOffMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReviewSignOff.
 */
@Service
@Transactional
public class ReviewSignOffService {

    private final Logger log = LoggerFactory.getLogger(ReviewSignOffService.class);

    private final ReviewSignOffRepository reviewSignOffRepository;

    private final ReviewSignOffMapper reviewSignOffMapper;

    public ReviewSignOffService(ReviewSignOffRepository reviewSignOffRepository, ReviewSignOffMapper reviewSignOffMapper) {
        this.reviewSignOffRepository = reviewSignOffRepository;
        this.reviewSignOffMapper = reviewSignOffMapper;
    }

    /**
     * Save a reviewSignOff.
     *
     * @param reviewSignOffDTO the entity to save
     * @return the persisted entity
     */
    public ReviewSignOffDTO save(ReviewSignOffDTO reviewSignOffDTO) {
        log.debug("Request to save ReviewSignOff : {}", reviewSignOffDTO);
        ReviewSignOff reviewSignOff = reviewSignOffMapper.toEntity(reviewSignOffDTO);
        reviewSignOff = reviewSignOffRepository.save(reviewSignOff);
        return reviewSignOffMapper.toDto(reviewSignOff);
    }

    /**
     *  Get all the reviewSignOffs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReviewSignOffDTO> findAll() {
        log.debug("Request to get all ReviewSignOffs");
        return reviewSignOffRepository.findAll().stream()
            .map(reviewSignOffMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one reviewSignOff by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ReviewSignOffDTO findOne(Long id) {
        log.debug("Request to get ReviewSignOff : {}", id);
        ReviewSignOff reviewSignOff = reviewSignOffRepository.findOne(id);
        return reviewSignOffMapper.toDto(reviewSignOff);
    }

    /**
     *  Delete the  reviewSignOff by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReviewSignOff : {}", id);
        reviewSignOffRepository.delete(id);
    }
}
