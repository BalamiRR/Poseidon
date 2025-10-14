package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CurvePointServiceTest {

    CurvePoint curvePoint = new CurvePoint();

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    private CurvePoint existingCurvePoint;

    @BeforeAll
    public void initsCurvePoint(){
        curvePoint.setTerm(12.9);
        curvePoint.setValue(12.1);
        curvePoint.setCurveId(2);
        curvePointService.insertCurvePoint(curvePoint);

        curvePointService.insertCurvePoint(curvePoint);

        // DB'den geri al
        existingCurvePoint = curvePointRepository.findAll().get(0);

        List<CurvePoint> all = curvePointRepository.findAll();
        if(!all.isEmpty()){
            curvePoint = all.get(0);
        }
    }

    @AfterAll
    public void cleanCurvePoint(){
        curvePointRepository.deleteAll();
    }

    @Test
    public void test_getCurvePointById() {
        Integer id = curvePoint.getId();
        CurvePoint foundId = curvePointService.findById(id);
        assertEquals(2, foundId.getCurveId(), 0.01);
        assertEquals(12.9, curvePoint.getTerm(), 0.01);
        assertEquals(12.1, curvePoint.getValue(), 0.01);
    }

    @Test
    public void test_updateRuleName() {
        Integer id = curvePoint.getId();
        CurvePoint foundId = curvePointService.findById(id);
        foundId.setCurveId(12);
        foundId.setTerm(12.0);
        foundId.setValue(12.0);
        Boolean updated = curvePointService.updateCurvePoint(id, foundId);
        assertTrue(updated);
    }

    @Test
    public void test_deleteCurvePointById() {
        Integer id = existingCurvePoint.getId();

        CurvePoint updatedData = new CurvePoint();
        updatedData.setCurveId(200);
        updatedData.setTerm(22.2);
        updatedData.setValue(33.3);

        boolean updated = curvePointService.updateCurvePoint(id, updatedData);

        assertTrue(updated, "Updatede successfully");

        CurvePoint found = curvePointService.findById(id);
        assertNotNull(found);
        assertEquals(200, found.getCurveId());
        assertEquals(22.2, found.getTerm(), 0.01);
        assertEquals(33.3, found.getValue(), 0.01);
    }
}