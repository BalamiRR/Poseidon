package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
    void insertRatings(Rating rating);
    Boolean updateRatingService(int id, Rating rating);
    Rating findById(int id);
    void deleteById(int id);
}
