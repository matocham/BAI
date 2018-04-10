package pl.edu.pb.wi.bai.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.bai.models.AllowedMessage;
import pl.edu.pb.wi.bai.models.AllowedMessagesPK;
import pl.edu.pb.wi.bai.models.Message;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.AllowedMessageRepository;
import pl.edu.pb.wi.bai.repositories.MessageRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.SecurityPrincipal;

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
    @Autowired
    UserRepository userRepository;

    public List<DisplayMessageDto> getAllMessages() {
        List<DisplayMessageDto> resultingMessages = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();

        for (Message msg : allMessages) {
            DisplayMessageDto displayMessageDto = new DisplayMessageDto();
            displayMessageDto.setText(msg.getText());
            displayMessageDto.setId(msg.getMessageId());
            displayMessageDto.setOwner(msg.getModerator());
            List<String> grantedUsers = allowedMessageRepository
                    .findByAllowedId_MessageId_MessageId(msg.getMessageId())
                    .stream()
                    .map(al -> al.getAllowedId()
                            .getUserId()
                            .getUsername())
                    .collect(Collectors.toList());
            displayMessageDto.setAllowedUsers(grantedUsers);
            resultingMessages.add(displayMessageDto);
        }
        return resultingMessages;
    }

    public void newMessage(String textMessage) {
        Message message = new Message();
        message.setText(textMessage);
        SecurityPrincipal myUserPrincipal=(SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userRepository.findByUsername(myUserPrincipal.getUsername());
        message.setModerator(user);
        messageRepository.save(message);
    }

    public DisplayMessageDto getMessageById(Long id) {
        Message msg = messageRepository.findOne(id);
        DisplayMessageDto displayMessageDto = new DisplayMessageDto();
        displayMessageDto.setText(msg.getText());
        displayMessageDto.setId(msg.getMessageId());
        displayMessageDto.setOwner(msg.getModerator());
        List<String> grantedUsers = allowedMessageRepository
                .findByAllowedId_MessageId_MessageId(msg.getMessageId())
                .stream()
                .map(al -> al.getAllowedId()
                        .getUserId()
                        .getUsername())
                .collect(Collectors.toList());
        displayMessageDto.setAllowedUsers(grantedUsers);
        return displayMessageDto;

    }

    public void editMessage(EditMessageDto editMessageDto) {
        Message message = messageRepository.findOne(editMessageDto.getId());
        message.setText(editMessageDto.getText());
        messageRepository.save(message);
    }


    public void deleteMessage(Long id) {
        Message message = messageRepository.findOne(id);
                SecurityPrincipal myUserPrincipal=(SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(message.getModerator().getUsername().equals(myUserPrincipal.getUsername())){

            messageRepository.delete(id);
        }
    }

    public void addPermission(Long messageId, Long userToAddPermissionId) {
        Message message = messageRepository.findOne(messageId);
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (message.getModerator().getUsername().equals(myUserPrincipal.getUsername())) {
            User user = userRepository.findOne(userToAddPermissionId);
            if(allowedMessageRepository.findByAllowedId_MessageIdAndAllowedId_UserId(message,user)==null&&!myUserPrincipal.getUsername().equals(user.getUsername())){//czy uprawnienia już nie są nadane oraz czy podany user nie jest włascicielem
            AllowedMessagesPK allowedMessagesPK = new AllowedMessagesPK();
            allowedMessagesPK.setMessageId(message);
            allowedMessagesPK.setUserId(user);
            AllowedMessage allowedMessage = new AllowedMessage();
            allowedMessage.setAllowedId(allowedMessagesPK);
            allowedMessageRepository.save(allowedMessage);
            }
        }
    }
    public void deletePermission(Long messageId, String userToDeletePermissionName) {
        Message message = messageRepository.findOne(messageId);
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (message.getModerator().getUsername().equals(myUserPrincipal.getUsername())) {
            User user = userRepository.findByUsername(userToDeletePermissionName);
            if(allowedMessageRepository.findByAllowedId_MessageIdAndAllowedId_UserId(message,user)!=null&&!myUserPrincipal.getUsername().equals(user.getUsername())) {
                allowedMessageRepository.deleteAllowedMessageByAllowedId_MessageIdAndAllowedId_UserId(message, user);
            }
        }
    }
}
