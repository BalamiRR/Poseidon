package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceTest {

    Rating rating = new Rating();

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void initsRatingService(){
        rating.setMoodysRating("Moody");
        rating.setSandPRating("SandP");
        rating.setFitchRating("Fitch");
        rating.setOrderNumber(12);
        ratingService.insertRatings(rating);
    }

    @Test
    public void test_updateRating() {
        Integer id = rating.getId();
        Rating foundId = ratingService.findById(id);
        foundId.setMoodysRating("MoodyRating");
        foundId.setSandPRating("SandRating");
        foundId.setFitchRating("FitchRating");

        Boolean updated = ratingService.updateRatingService(id, foundId);
        assertTrue(updated);
    }

    @Test
    public void test_deleteRatingById() {
        Integer id = rating.getId();
        ratingService.deleteById(id);
        Rating foundId = ratingService.findById(id);
        assertNull(foundId);
    }

}