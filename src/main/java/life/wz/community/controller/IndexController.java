package life.wz.community.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.wz.community.dto.PageHelpDTO;
import life.wz.community.dto.QuestionDTO;
import life.wz.community.mapper.UserMapper;
import life.wz.community.model.User;
import life.wz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="pageNo",defaultValue = "2") Integer pageNo) {
        PageHelpDTO pageHelpDTO = questionService.list(pageNo);
        model.addAttribute("questions", pageHelpDTO);
        return "index";
    }


}
