package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {
    private final CurvePointRepository curvePointRepository;

    /**
     * Retrieves all CurvePoint entries from the database.
     * @return a list of all CurvePoint lists
     */
    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Inserts a new CurvePoint into the database.
     * @param curvePoint the curvePoint list object to be saved
     */
    @Override
    public void insertCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    /**
     * Updates an existing CurvePoint identified by its ID.
     * If the CurvePoint exists, updates its fields (curveId, term, value )
     * and saves the new values to the database.
     *
     * @param id ID of the curvePoint list to update
     * @param curvePoint updated curvePoint list data
     * @return  if the update was successful, false otherwise
     */
    @Override
    public Boolean updateCurvePoint(int id, CurvePoint curvePoint) {
        Optional<CurvePoint> list = curvePointRepository.findById(id);
        boolean updated = false;
        if(list.isPresent()){
            CurvePoint updatedCurvePoint = list.get();
            updatedCurvePoint.setCurveId(curvePoint.getCurveId());
            updatedCurvePoint.setTerm(curvePoint.getTerm());
            updatedCurvePoint.setValue(curvePoint.getValue());
            curvePointRepository.save(updatedCurvePoint);
            updated = true;
            log.error("Successfully updated the CurvePoint {}", id);
        } else {
            log.error("Failed to update CurvePoint {}", id);
        }
        return updated;
    }

    /**
     * Retrieves a CurvePoint by its unique ID.
     * @param id ID of the curvePoint list to retrieve
     * @return the CurvePoint object if found, otherwise null
     */
    @Override
    public CurvePoint findById(int id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if(curvePoint.isPresent()){
            log.info("Successfully finding by id {}", id);
            return curvePoint.get();
        } else {
            log.error("Error finding by id {}", id);
            return null;
        }
    }

    /**
     * Deletes a CurvePoint by its unique ID.
     * If the curvePoint list is found, it will be deleted from the database.
     * @param id ID of the curvePoint list to delete
     */
    @Override
    public void deleteById(int id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            curvePointRepository.delete(curvePoint.get());
            log.error("Successfully deleted by id {}", id);
        } else {
            log.error("Failed to delete with id " + id);
        }
    }


}
