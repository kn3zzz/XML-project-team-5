package com.dislinkt.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private long id;
    private List<Long> connectionIdList;
    private List<Long> seenIdList;
    private String text;
    private Date dateAndTimeCreated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getConnectionIdList() {
        return connectionIdList;
    }

    public void setConnectionIdList(List<Long> connectionIdList) {
        this.connectionIdList = connectionIdList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateAndTimeCreated() {
        return dateAndTimeCreated;
    }

    public void setDateAndTimeCreated(Date dateAndTimeCreated) {
        this.dateAndTimeCreated = dateAndTimeCreated;
    }

    public List<Long> getSeenIdList() {
        return seenIdList;
    }

    public void setSeenIdList(List<Long> seenIdList) {
        this.seenIdList = seenIdList;
    }
}
