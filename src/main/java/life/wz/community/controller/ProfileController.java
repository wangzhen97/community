package life.wz.community.controller;

import com.github.pagehelper.PageInfo;
import life.wz.community.dto.PageHelpDTO;
import life.wz.community.dto.QuestionDTO;
import life.wz.community.model.User;
import life.wz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return  "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("sessionTag","question");
            model.addAttribute("sessionName","我的提问");
            PageHelpDTO pageHelpDTO =questionService.listByCreator(user.getId(),pageNo);
            System.out.println(pageHelpDTO);
            model.addAttribute("questions",pageHelpDTO);
        }else if("replies".equals(action)){
            model.addAttribute("sessionTag","replies");
            model.addAttribute("sessionName","最新回复");
            PageHelpDTO pageHelpDTO =questionService.listByCreator(user.getId(),pageNo);
            System.out.println(pageHelpDTO);
            model.addAttribute("questions",pageHelpDTO);
        }
        return "profile";
    }
}
