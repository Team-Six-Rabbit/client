package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveEndDetailDto;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveListResponseDto;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveListServiceImpl implements LiveListService {
	private final BattleBoardRepository battleBoardRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final LiveApplyUserRepository liveApplyUserRepository;

	@Override
	public Page<LiveListResponseDto> getActiveLiveList(String keyword, String category, Pageable pageable) {
		return battleBoardRepository.findAllActiveBattleBoards(new Date(), keyword, category, pageable)
			.map(this::convertToDto);
	}

	@Override
	public Page<LiveListResponseDto> getWaitLiveList(String keyword, String category, Pageable pageable) {
		Date currentTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.MINUTE, 20);

		return battleBoardRepository.findAllWaitBattleBoards(currentTime, calendar.getTime(), keyword, category,
			pageable).map(this::convertToDto);
	}

	@Override
	public Page<LiveListResponseDto> getEndLiveList(String keyword, String category, Pageable pageable) {
		return battleBoardRepository.findAllEndBattleBoards(new Date(), keyword, category, pageable)
			.map(this::convertToDto);
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

	@Override
	public LiveEndDetailDto getEndLiveSummary(Long battleId) {
		Optional<BattleBoard> optionalBattleBoard = battleBoardRepository.findById(battleId);
		if (optionalBattleBoard.isPresent()) {
			BattleBoard battleBoard = optionalBattleBoard.get();

			return convertToEndDetailDto(battleBoard);
		} else {
			throw new EntityNotFoundException("BattleBoard not found with id " + battleId);
		}
	}

	private LiveEndDetailDto convertToEndDetailDto(BattleBoard battleBoard) {
		List<VoteOpinion> voteOpinions = voteOpinionRepository.findAllByVoteInfoId(battleBoard.getVoteInfo().getId());
		VoteInfo voteInfo = battleBoard.getVoteInfo();

		if (voteOpinions.size() < 2) {
			return null;
		}
		User registUser = battleBoard.getRegistUser();
		User oppositeUser = battleBoard.getOppositeUser();

		int registPrePercent =
			100 * voteOpinions.get(0).getPreCount() / (voteOpinions.get(0).getPreCount() + voteOpinions.get(1)
				.getPreCount());
		int registFinalPercent =
			100 * voteOpinions.get(0).getFinalCount() / (voteOpinions.get(0).getFinalCount() + voteOpinions.get(1)
				.getFinalCount());

<<<<<<< HEAD
		return new LiveEndDetailDto(
			battleBoard.getId(),
			voteInfo.getTitle(),
			new LiveEndDetailDto.BroadcastUser(registUser.getId(), registUser.getNickname(), registUser.getImgUrl(),
				registUser.getRating(), voteOpinions.get(0).getOpinion()),
			new LiveEndDetailDto.BroadcastUser(oppositeUser.getId(), oppositeUser.getNickname(),
				oppositeUser.getImgUrl(), oppositeUser.getRating(), voteOpinions.get(1).getOpinion()),
			new LiveEndDetailDto.VoteResult(registPrePercent, 100 - registPrePercent),
			new LiveEndDetailDto.VoteResult(registFinalPercent, 100 - registFinalPercent),
			voteInfo.getCategory(),
			battleBoard.getImageUrl(),
			battleBoard.getDetail()
		);
	}

	private LiveListResponseDto convertToDto(BattleBoard battleBoard) {
		List<VoteOpinion> voteOpinions = voteOpinionRepository.findAllByVoteInfoId(battleBoard.getVoteInfo().getId());
=======
        int registPrePercent = 100 * voteOpinions.get(0).getPreCount() / (voteOpinions.get(0).getPreCount() + voteOpinions.get(1).getPreCount());
        int registFinalPercent = 100 * voteOpinions.get(0).getFinalCount() / (voteOpinions.get(0).getFinalCount() + voteOpinions.get(1).getFinalCount());

        return new LiveEndDetailDto(
                battleBoard.getId(),
                voteInfo.getTitle(),
                new LiveEndDetailDto.BroadcastUser(registUser.getId(), registUser.getNickname(), registUser.getImg_url(), registUser.getRating(), voteOpinions.get(0).getOpinion()),
                new LiveEndDetailDto.BroadcastUser(oppositeUser.getId(), oppositeUser.getNickname(), oppositeUser.getImg_url(), oppositeUser.getRating(), voteOpinions.get(1).getOpinion()),
                new LiveEndDetailDto.VoteResult(registPrePercent, 100 - registPrePercent),
                new LiveEndDetailDto.VoteResult(registFinalPercent, 100 - registFinalPercent),
                voteInfo.getCategory(),
                battleBoard.getImageUrl(),
                battleBoard.getDetail()
        );
    }
>>>>>>> 8cb6d8e ([Fix] Response Dto)

		if (voteOpinions.size() < 2) {
			return null;
		}

		User registUser = battleBoard.getRegistUser();
		User oppositeUser = battleBoard.getOppositeUser();

<<<<<<< HEAD
		return new LiveListResponseDto(
			battleBoard.getId(),
			battleBoard.getRoom().getRoomId(),
			battleBoard.getVoteInfo().getTitle(),
			new LiveListResponseDto.BroadcastUser(registUser.getId(), registUser.getNickname(), registUser.getImgUrl(),
				registUser.getRating(), voteOpinions.get(0).getOpinion()),
			new LiveListResponseDto.BroadcastUser(oppositeUser.getId(), oppositeUser.getNickname(),
				oppositeUser.getImgUrl(), oppositeUser.getRating(), voteOpinions.get(1).getOpinion()),
			battleBoard.getVoteInfo().getStartDate(),
			battleBoard.getVoteInfo().getEndDate(),
			liveApplyUserRepository.findAllByRoom_Id(battleBoard.getRoom().getId()).size(),
			battleBoard.getVoteInfo().getCategory(),
			battleBoard.getImageUrl(),
			battleBoard.getBattleRule(),
			battleBoard.getDetail()
=======
        User registUser = battleBoard.getRegistUser();
        User oppositeUser = battleBoard.getOppositeUser();

        return new LiveListResponseDto(
                battleBoard.getId(),
                battleBoard.getRoom().getRoomId(),
                battleBoard.getVoteInfo().getTitle(),
                new LiveListResponseDto.BroadcastUser(registUser.getId(), registUser.getNickname(), registUser.getImg_url(), registUser.getRating(), voteOpinions.get(0).getOpinion()),
                new LiveListResponseDto.BroadcastUser(oppositeUser.getId(), oppositeUser.getNickname(), oppositeUser.getImg_url(), oppositeUser.getRating(),voteOpinions.get(1).getOpinion()),
                battleBoard.getVoteInfo().getStartDate(),
                battleBoard.getVoteInfo().getEndDate(),
                liveApplyUserRepository.findAllByRoom_Id(battleBoard.getRoom().getId()).size(),
                battleBoard.getVoteInfo().getCategory(),
                battleBoard.getImageUrl(),
                battleBoard.getBattleRule(),
                battleBoard.getDetail()
>>>>>>> 8cb6d8e ([Fix] Response Dto)

		);

	}

}
