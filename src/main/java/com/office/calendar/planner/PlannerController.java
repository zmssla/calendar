package com.office.calendar.planner;

import com.office.calendar.planner.util.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/planner")
public class PlannerController {

    final private PlannerService plannerService;
    final private UploadFileService uploadFileService;

    public PlannerController(PlannerService plannerService,
                             UploadFileService uploadFileService) {
        this.plannerService = plannerService;
        this.uploadFileService = uploadFileService;

    }

    @GetMapping({"", "/"})
    public String home() {
        log.info("home()");

        String nextPage = "planner/home";

        return nextPage;

    }

    /*
        일정 등록 확인
     */
    @PostMapping("/plan")
    public ResponseEntity<Map<String, Object>> writePlan(PlannerDto plannerDto,
                                                          @RequestParam("file") MultipartFile file,
                                                          Principal principal) {
        log.info("writePlan()");

        String signInedMemberID = principal.getName();

        // SAVE FILE
        String savedFileName = uploadFileService.upload(signInedMemberID, file);
        if (savedFileName != null) {
            plannerDto.setImg_name(savedFileName);
            plannerDto.setOwner_id(signInedMemberID);

            Map<String, Object> resultMap = plannerService.writePlan(plannerDto);

            // 200: OK
            return ResponseEntity.ok(resultMap);

        } else {

            // 400: NG
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "File upload failed!!");
            return ResponseEntity.badRequest().body(errorMap);

        }

    }

    @GetMapping("/plans")
    public ResponseEntity<Map<String, Object>> getPlans(
            @RequestParam Map<String, Object> reqData,
            Principal principal) {
        log.info("getPlans()");

        String signInedMemberId = principal.getName();
        reqData.put("owner_id", signInedMemberId);

        Map<String, Object> resultMap = plannerService.getPlans(reqData);

        return ResponseEntity.ok(resultMap);

    }

    @GetMapping("/plan")
    public ResponseEntity<Map<String, Object>> getPlan(
            @RequestParam Map<String, Object> reqData) {
        log.info("getPlan()");

        Map<String, Object> resultMap = plannerService.getPlan(reqData);

        return  ResponseEntity.ok(resultMap);

    }

    @DeleteMapping("/plan/{no}")
    public ResponseEntity<Map<String, Object>> removePlan(@PathVariable("no") int no) {
        log.info("removePlan()");

        Map<String, Object> resultMap = plannerService.removePlan(no);

        return ResponseEntity.ok(resultMap);

    }

    @PutMapping("/plan/{no}")
    public ResponseEntity<Map<String, Object>> modifyPlan(
            PlannerDto plannerDto,
            @PathVariable("no") int no,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Principal principal
    ) {
        log.info("modifyPlan()");

        plannerDto.setNo(no);
        if (file != null) {
            String savedFileName = uploadFileService.upload(principal.getName(), file);
            if (savedFileName != null) {
                plannerDto.setOwner_id(principal.getName());
                plannerDto.setImg_name(savedFileName);

                return ResponseEntity.ok(plannerService.modifyPlan(plannerDto));

            } else {
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", "File upload FAILED!!");
                return  ResponseEntity.badRequest().body(errorMap);

            }

        } else {
            return ResponseEntity.ok(plannerService.modifyPlan(plannerDto));

        }

    }

}
