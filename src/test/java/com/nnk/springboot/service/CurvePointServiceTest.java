package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointServiceTest {

    CurvePoint curvePoint = new CurvePoint();

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeAll
    public void initsCurvePoint(){
        curvePoint.setTerm(12.9);
        curvePoint.setValue(12.1);
        curvePoint.setCurveId(2);
        curvePointService.insertCurvePoint(curvePoint);

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
    public void test_deleteCurvePointById() {
        Integer id = curvePoint.getId();
        curvePointService.deleteById(id);
        CurvePoint byId = curvePointService.findById(id);
        assertNull(byId);
    }
}