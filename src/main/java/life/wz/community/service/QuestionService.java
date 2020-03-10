package life.wz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.wz.community.dto.PageHelpDTO;
import life.wz.community.dto.QuestionDTO;
import life.wz.community.mapper.QuestionMapper;
import life.wz.community.mapper.UserMapper;
import life.wz.community.model.Question;
import life.wz.community.model.QuestionExample;
import life.wz.community.model.User;
import life.wz.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageHelpDTO list(Integer pageNo) {
        PageHelper.startPage(pageNo, 2);

        List<Question> questionList = questionMapper.selectByExample(new QuestionExample());
        PageInfo<Question> page = new PageInfo<>(questionList);
        PageHelpDTO pageHelpDTO = new PageHelpDTO();

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users= userMapper.selectByExample(userExample);

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        BeanUtils.copyProperties(page, pageHelpDTO);
        pageHelpDTO.setList(questionDTOList);
        return pageHelpDTO;
    }

    public PageHelpDTO listByCreator(Integer creator, Integer pageNo) {
        PageHelper.startPage(pageNo, 2);
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(creator);
        List<Question> questionList = questionMapper.selectByExample(questionExample);
        System.out.println(questionList);
        PageInfo<Question> page = new PageInfo<>(questionList);
        PageHelpDTO pageHelpDTO = new PageHelpDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        BeanUtils.copyProperties(page, pageHelpDTO);
        pageHelpDTO.setList(questionDTOList);
        return pageHelpDTO;
    }

    public QuestionDTO selectById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }


    public int insertOrUpdate(Question question) {

        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            return questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            return questionMapper.updateByExample(question,questionExample);
        }

    }
}
