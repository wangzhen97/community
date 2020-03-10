package life.wz.community.controller;

import life.wz.community.dto.QuestionDTO;
import life.wz.community.mapper.QuestionMapper;
import life.wz.community.mapper.UserMapper;
import life.wz.community.model.Question;
import life.wz.community.model.User;
import life.wz.community.service.PublishService;
import life.wz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String alertPublish(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDTO questionDTO=questionService.selectById(id);
        System.out.println("ji1231231");
        model.addAttribute("question",questionDTO);
        return "publish";
    }



    @RequestMapping("/publish")
    public String Publish(Model model) {
        Question question = new Question();
        model.addAttribute("question",question);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Model model, Question question, HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        System.out.println("jinru111");
        //model添加发布数据
        model.addAttribute("question",question);
        System.out.println(question);

        //判断数据是否为空
        if(question.getTitle()==null||"".equals(question.getTitle())){
           model.addAttribute("msg","请输入标题");
           return "publish";
        }else if(question.getDescription()==null||"".equals(question.getDescription())){
            model.addAttribute("msg","请输入问题");
            return "publish";
        }if(question.getTag()==null||"".equals(question.getTag())){
            model.addAttribute("msg","请输入标签");
            return "publish";
        }
        question.setCreator(user.getId());
        questionService.insertOrUpdate(question);
        return "redirect:/";
    }
}
