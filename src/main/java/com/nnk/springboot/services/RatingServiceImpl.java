package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    /**
     * Retrieves all Rating entries from the database.
     * @return a list of all rating lists
     */
    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Inserts a new Rating into the database.
     * @param rating the rating list object to be saved
     */
    @Override
    public void insertRatings(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * Updates an existing Rating identified by its ID.
     * If the Rating exists, updates its fields (moodysRating, sandPRating, fitchRating, orderNumber)
     * and saves the new values to the database.
     *
     * @param id ID of the rating list to update
     * @param rating updated rating list data
     * @return  if the update was successful, false otherwise
     */
    @Override
    public Boolean updateRatingService(int id, Rating rating) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        boolean updated = false;
        if(optionalRating.isPresent()){
            Rating newRating = optionalRating.get();
            newRating.setMoodysRating(rating.getMoodysRating());
            newRating.setSandPRating(rating.getSandPRating());
            newRating.setFitchRating(rating.getFitchRating());
            newRating.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(newRating);
            updated = true;
            log.info("Successfully update ! {} ", id);
            return updated;
        } else {
            log.error("Error to update {} !", id);
            return updated;
        }
    }

    /**
     * Retrieves a Rating by its unique ID.
     * @param id ID of the rating list to retrieve
     * @return the Rating object if found, otherwise null
     */
    @Override
    public Rating findById(int id) {
        Optional<Rating> ratingList = ratingRepository.findById(id);
        if(ratingList.isPresent()){
            log.info("Successfully finding by Id {}", id);
            return ratingList.get();
        } else {
            log.info("Error finding by Id {}", id);
            return null;
        }
    }

    /**
     * Deletes a Rating by its unique ID.
     * If the rating list is found, it will be deleted from the database.
     * @param id ID of the rating list to delete
     */
    @Override
    public void deleteById(int id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            ratingRepository.delete(rating.get());
            log.error("Successfully deleted by id {}", id);
        } else {
            log.error("Failed to delete with id " + id);
        }
    }
}
