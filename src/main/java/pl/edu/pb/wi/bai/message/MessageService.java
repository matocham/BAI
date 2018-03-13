package pl.edu.pb.wi.bai.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.bai.models.Message;
import pl.edu.pb.wi.bai.repositories.AllowedMessageRepository;
import pl.edu.pb.wi.bai.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    //class for all interactions between repository and controller

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AllowedMessageRepository allowedMessageRepository;

    public List<MessageDto> getAllMessages(){
        List<MessageDto> resultingMessages = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();

        for (Message msg : allMessages) {
            MessageDto messageDto = new MessageDto();
            messageDto.setText(msg.getText());
            messageDto.setId(msg.getMessageId());
            messageDto.setOwner(msg.getModerator());
            List<String> grantedUsers = allowedMessageRepository
                    .findByAllowedId_MessageId_MessageId(msg.getMessageId())
                    .stream()
                    .map(al -> al.getAllowedId()
                            .getUserId()
                            .getUsername())
                    .collect(Collectors.toList());
            messageDto.setAllowedUsers(grantedUsers);
            resultingMessages.add(messageDto);
        }
        return resultingMessages;
    }
}
