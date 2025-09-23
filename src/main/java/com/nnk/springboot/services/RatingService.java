package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
}
