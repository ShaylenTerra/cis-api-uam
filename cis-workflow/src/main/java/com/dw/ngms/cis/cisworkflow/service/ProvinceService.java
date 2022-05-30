package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MProvinces;
import com.dw.ngms.cis.cisworkflow.persistence.repository.ProvinceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class ProvinceService {


    private final ProvinceRepository provinceRepository;

    public ResponseEntity<?> getProvinceList() {
        List<MProvinces> provincesList = this.provinceRepository.findAll();
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (MProvinces prov : provincesList) {
            Map<String, Object> internalMap = new HashMap<>();
            internalMap.put("ProvinceId", prov.getProvinceId());
            internalMap.put("Name", prov.getProvinceName());
            internalMap.put("Code", prov.getProvinceCode());
            returnList.add(internalMap);
        }
        return ResponseEntity.status(HttpStatus.OK).body(returnList);
    }
}
