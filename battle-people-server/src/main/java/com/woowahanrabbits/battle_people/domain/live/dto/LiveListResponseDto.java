package com.woowahanrabbits.battle_people.domain.live.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveListResponseDto {
    private Long id;
    private String roomId;

    private String title;

    private BroadcastUser registerUser;
    private BroadcastUser oppositeUser;

    private Date startDate;
    private Date endDate;
    private int currentPeopleCount;

    private String category;
    private String imageUri;

    @Data
    @AllArgsConstructor
    public static class BroadcastUser{
        long id;
        String opinion;
    }

}
