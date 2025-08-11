package com.office.calendar.planner;

import com.office.calendar.planner.jpa.PlannerEntity;
import com.office.calendar.planner.jpa.PlannerRepository;
import com.office.calendar.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlannerService {

    public static final int PLAN_MODIFY_SUCCESS     = 1;
    public static final int PLAN_MODIFY_FAIL        = 0;

    private final PlannerRepository plannerRepository;

    public PlannerService(PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
    }

    @Transactional
    public Map<String, Object> removePlan(int no) {
        log.info("removePlan()");

        Map<String, Object> resultMap = new HashMap<>();

        int result = plannerRepository.deleteByPlanNo(no);
        resultMap.put("result", result);

        return resultMap;

    }

    public Map<String, Object> writePlan(PlannerDto plannerDto) {
        log.info("writePlan()");

        Map<String, Object> resultMap = new HashMap<>();

        int result = 0;
        PlannerEntity savedPlannerEntity =
                plannerRepository.save(plannerDto.toEntity());
        if (savedPlannerEntity != null) {
            log.info("INSERT NEW PLAN SUCCESS!!");
            result = 1;

        } else {
            log.info("INSERT NEW PLAN FAIL!!");

        }

        resultMap.put("result", result);

        return resultMap;

    }

    public Map<String, Object> getPlans(Map<String, Object> reqData) {
        log.info("getPlans()");

        Map<String, Object> resultMap = new HashMap<>();

        List<PlannerEntity> plannerEntities =
                plannerRepository.findByPlanYearAndPlanMonthAndPlanOwnerId(
                Integer.valueOf(String.valueOf(reqData.get("year"))),
                Integer.valueOf(String.valueOf(reqData.get("month"))),
                String.valueOf(reqData.get("owner_id")));

        List<PlannerDto> plannerDtos = plannerEntities.stream()
                .map(PlannerEntity::toDto)
                .collect(Collectors.toList());

        resultMap.put("plans", plannerDtos);

        return resultMap;

    }

    public Map<String, Object> getPlan(Map<String, Object> reqData) {
        log.info("getPlan()");

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put(
                "plan",
                plannerRepository.findByPlanNo(Util.castObjectToInteger(reqData.get("no"))).toDto()
                );

        return resultMap;

    }

    @Transactional
    public Map<String, Object> modifyPlan(PlannerDto plannerDto) {
        log.info("modifyPlan()");

        Map<String, Object> resultMap = new HashMap<>();

        int result = PLAN_MODIFY_FAIL;
        PlannerEntity plannerEntity = plannerRepository.findByPlanNo(plannerDto.getNo());
        if (plannerEntity != null) {
            plannerEntity.setPlanYear(plannerDto.getYear());
            plannerEntity.setPlanMonth(plannerDto.getMonth());
            plannerEntity.setPlanDate(plannerDto.getDate());
            plannerEntity.setPlanTitle(plannerDto.getTitle());
            plannerEntity.setPlanBody(plannerDto.getBody());

            if (plannerDto.getImg_name() != null) {
                plannerEntity.setPlanImgName(plannerDto.getImg_name());
            }

            result = PLAN_MODIFY_SUCCESS;

        }

        resultMap.put("result", result);

        return resultMap;

    }
}
