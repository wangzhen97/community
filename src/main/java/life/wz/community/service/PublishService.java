package life.wz.community.service;

import life.wz.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishService {

    @Autowired
    private QuestionMapper questionMapper;


}
