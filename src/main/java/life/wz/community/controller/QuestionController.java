package life.wz.community.controller;

import life.wz.community.dto.QuestionDTO;
import life.wz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question")
    public String question(@RequestParam("id") Integer id,
                           Model model){
      QuestionDTO questionDTO= questionService.selectById(id);
        System.out.println(questionDTO);
      model.addAttribute("question",questionDTO);
      return "question";
    }

}
