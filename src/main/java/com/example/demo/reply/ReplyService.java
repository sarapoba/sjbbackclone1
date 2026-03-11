package com.example.demo.reply;

import com.example.demo.reply.model.Reply;
import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.AuthUserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    public ReplyDto.ReplyRes reg(AuthUserDetails user, Long boardIdx, ReplyDto.ReplyReq dto) {
       Reply reply = replyRepository.save(dto.toEntity(user, boardIdx));

       return ReplyDto.ReplyRes.from(reply);
    }
}
