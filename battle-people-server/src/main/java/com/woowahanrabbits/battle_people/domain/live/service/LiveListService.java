package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.LiveEndDetailDto;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LiveListService {
    Page<LiveListResponseDto> getActiveLiveList(String keyword, String category, Pageable pageable);
    Page<LiveListResponseDto> getWaitLiveList(String keyword, String category, Pageable pageable);
    Page<LiveListResponseDto> getEndLiveList(String keyword, String category, Pageable pageable);

    LiveListResponseDto getLiveInfo(Long battleId);
    LiveEndDetailDto getEndLiveSummary(Long battleId);

}
