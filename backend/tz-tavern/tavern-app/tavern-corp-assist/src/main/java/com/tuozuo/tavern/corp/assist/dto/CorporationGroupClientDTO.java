package com.tuozuo.tavern.corp.assist.dto;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class CorporationGroupClientDTO {
    private String status;
    private CorporationGroupChat groupChat;

    public CorporationGroupClientDTO() {
    }

    public CorporationGroupClientDTO(String status, CorporationGroupChat groupChat) {
        this.status = status;
        this.groupChat = groupChat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CorporationGroupChat getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(CorporationGroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public static CorporationGroupClientDTO construct(String chatId, String chatName) {
        CorporationGroupClientDTO dto = new CorporationGroupClientDTO();
        CorporationGroupChat groupChat = new CorporationGroupChat();
        groupChat.setChatId(chatId);
        groupChat.setName(chatName);
        dto.setGroupChat(groupChat);
        dto.setStatus("2");
        return dto;
    }

}
