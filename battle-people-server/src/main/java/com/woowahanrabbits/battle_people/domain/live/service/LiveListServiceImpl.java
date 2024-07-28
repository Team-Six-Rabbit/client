package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveListResponseDto;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiveListServiceImpl implements LiveListService{
    private final BattleBoardRepository battleBoardRepository;
    private final VoteOpinionRepository voteOpinionRepository;
    private final LiveApplyUserRepository liveApplyUserRepository;

    @Override
    public Page<LiveListResponseDto> getActiveLiveList(String keyword, String category, Pageable pageable) {
        return battleBoardRepository.findAllActiveBattleBoards(new Date(), keyword, category, pageable).map(this::convertToDto);
    }

    @Override
    public Page<LiveListResponseDto> getWaitLiveList(String keyword, String category, Pageable pageable) {
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.MINUTE, 20);

        return battleBoardRepository.findAllWaitBattleBoards(currentTime, calendar.getTime(), keyword, category, pageable).map(this::convertToDto);
    }

    @Override
    public Page<LiveListResponseDto> getEndLiveList(String keyword, String category, Pageable pageable) {
        return  battleBoardRepository.findAllEndBattleBoards(new Date(), keyword, category, pageable).map(this::convertToDto);
    }

    @Override
    public LiveListResponseDto getLiveInfo(Long battleId) {
        Optional<BattleBoard> optionalBattleBoard = battleBoardRepository.findById(battleId);
        if (optionalBattleBoard.isPresent()) {
            BattleBoard battleBoard = optionalBattleBoard.get();

            return convertToDto(battleBoard);
        } else {
            throw new EntityNotFoundException("BattleBoard not found with id " + battleId);
        }
    }


    private LiveListResponseDto convertToDto(BattleBoard battleBoard) {
        List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId());

        if(voteOpinions.size() < 2)
            return null;

        return new LiveListResponseDto(
                battleBoard.getId(),
                battleBoard.getRoom().getRoomId(),
                battleBoard.getVoteInfo().getTitle(),
                new LiveListResponseDto.BroadcastUser(battleBoard.getRegistUser().getId(), battleBoard.getRegistUser().getNickname(), voteOpinions.get(0).getOpinion()),
                new LiveListResponseDto.BroadcastUser(battleBoard.getOppositeUser().getId(), battleBoard.getOppositeUser().getNickname(),voteOpinions.get(1).getOpinion()),
                battleBoard.getVoteInfo().getStartDate(),
                battleBoard.getVoteInfo().getEndDate(),
                liveApplyUserRepository.findAllByRoom_Id(battleBoard.getRoom().getId()).size(),
                battleBoard.getVoteInfo().getCategory(),
                battleBoard.getImageUrl(),
                battleBoard.getBattleRule(),
                battleBoard.getDetail()

        );

    }

}
