package com.pado.inflow.attendance.query.service;

import com.pado.inflow.attendance.query.dto.CommuteDTO;
import com.pado.inflow.attendance.query.dto.PageDTO;
import com.pado.inflow.attendance.query.repository.CommuteMapper;
import com.pado.inflow.common.exception.CommonException;
import com.pado.inflow.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuteServiceImpl implements CommuteService {

    private final Integer PAGE_SIZE = 10; // 페이지 간격
    private final Integer ELEMENTS_PER_PAGE = 10; // 한 페이지 당 요소 개수

    private final CommuteMapper commuteMapper;

    @Autowired
    public CommuteServiceImpl(CommuteMapper commuteMapper) {
        this.commuteMapper = commuteMapper;
    }

    // 사원별 출퇴근 내역 조회
    @Override
    public PageDTO<CommuteDTO> findCommutesByEmployeeId(Long employeeId, Integer pageNo) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        Integer totalElements = commuteMapper.getTotalCommutesByEmployeeId(employeeId);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<CommuteDTO> commutes = commuteMapper.findCommutesByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset);
        if (commutes == null || commutes.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        return new PageDTO<>(commutes, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

}
