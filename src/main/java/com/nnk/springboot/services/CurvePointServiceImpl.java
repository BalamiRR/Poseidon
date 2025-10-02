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

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public void insertCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

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
