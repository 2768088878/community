package lift.majiang.community.service;

import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.CommentDTO;

import java.util.List;

public interface CommentService {
    void insert(Comment comment,Integer id);

    public List<CommentDTO> listByQuestionId(Integer id);

    public List<CommentDTO> listComments(Integer id);

}
