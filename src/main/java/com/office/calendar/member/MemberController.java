package com.office.calendar.member;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    final private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
    회원 가입 양식
     */
    @GetMapping("/signup")
    public String signup() {

        String nextPage = "member/signup_form";

        return nextPage;

    }

    /*
    회원 가입 확인
     */
    @PostMapping("/signup_confirm")
    public String signupConfirm(MemberDto memberDto, Model model) {

        String nextPage = "member/signup_result";

        int result = memberService.signupConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;

    }

    /*
      로그인 양식
     */
    @GetMapping("/signin")
    public String signin() {

        String nextPage = "member/signin_form";

        return nextPage;

    }

    /*
        로그인 확인
     */
    /*
    @PostMapping("/signin_confirm")
    public String signinConfirm(
            MemberDto memberDto,
            Model model,
            HttpSession session
    ) {

        String nextPage = "member/signin_result";

        String loginedID = memberService.signinConfirm(memberDto);
        if (loginedID != null) {
            model.addAttribute("loginedID", loginedID);
            session.setAttribute("loginedID", loginedID);
            session.setMaxInactiveInterval(60 * 30);

        }

        return nextPage;

    }
    */

    /*
        로그 아웃 확인
     */
    /*
    @GetMapping("/signout_confirm")
    public String signoutConfirm(HttpSession session) {

        String nextPage = "redirect:/";

        session.invalidate();

        return nextPage;

    }
    */

    /*
        정보 수정 양식
     */
    @GetMapping("/modify")
    public String modify(Model model, Principal principal) {

        String nextPage = "member/modify_form";

        MemberDto loginedMemberDto = memberService.modify(principal.getName());
        model.addAttribute("loginedMemberDto", loginedMemberDto);

        return nextPage;

    }

    /*
        정보 수정 확인
     */
    @PostMapping("/modify_confirm")
    public String modifyConfirm(MemberDto memberDto, Model model) {

        String nextPage = "member/modify_result";

        int result = memberService.modifyConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;

    }

    /*
        비밀 번호 찾기 양식
     */
    @GetMapping("/findpassword")
    public String findpassword() {

        String nextPage = "member/findpassword_form";

        return nextPage;

    }

    /*
        비밀번호 찾기 확인
     */
    @GetMapping("/findpassword_confirm")
    public String findpasswordConfirm(MemberDto memberDto, Model model) {

        String nextPage = "member/findpassword_result";

        int result = memberService.findpasswordConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;

    }

    @GetMapping("/signin_result")
    public String signinResult(@RequestParam(value = "loginedID", required = false) String loginedID,
                               Model model) {
        log.info("signinResult()");

        String nextPage = "member/signin_result";
        model.addAttribute("loginedID", loginedID);

        return nextPage;

    }

    /*
        접근 거부 시
     */
    @GetMapping("/access_denied")
    public String accessDenied() {
        log.info("accessDenied()");

        String nextPage = "member/access_denied";

        return nextPage;

    }

}
