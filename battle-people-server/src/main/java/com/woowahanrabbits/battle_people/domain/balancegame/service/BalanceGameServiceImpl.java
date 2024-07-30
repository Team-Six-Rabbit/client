package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.balancegame.domain.BalanceGameBoardComment;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameCommentDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameCommentResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.balancegame.infrastructure.BalanceGameRepository;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDto;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

@Service
public class BalanceGameServiceImpl implements BalanceGameService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final BattleRepository battleRepository;
	private final BalanceGameRepository balanceGameRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final UserRepository userRepository;

	public BalanceGameServiceImpl(VoteInfoRepository voteInfoRepository, VoteOpinionRepository voteOpinionRepository,
		BattleRepository battleRepository, BalanceGameRepository balanceGameRepository,
		UserVoteOpinionRepository userVoteOpinionRepository, UserRepository userRepository) {
		this.voteInfoRepository = voteInfoRepository;
		this.voteOpinionRepository = voteOpinionRepository;
		this.battleRepository = battleRepository;
		this.balanceGameRepository = balanceGameRepository;
		this.userVoteOpinionRepository = userVoteOpinionRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void addBalanceGame(CreateBalanceGameRequest createBalanceGameRequest, int userId) {
		User registUser = userRepository.findById((long)userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		VoteInfo voteInfo = VoteInfo.builder()
			.title(createBalanceGameRequest.getTitle())
			.startDate(createBalanceGameRequest.getStartDate())
			.endDate(createBalanceGameRequest.getEndDate())
			.category(createBalanceGameRequest.getCategory())
			.build();
		voteInfoRepository.save(voteInfo);

		for (int i = 0; i < 2; i++) {
			VoteOpinion voteOpinion = VoteOpinion.builder()
				.voteOpinionIndex(i)
				.voteInfoId(voteInfo.getId())
				.user(registUser)
				.opinion(createBalanceGameRequest.getOpinions().get(i))
				.build();

			voteOpinionRepository.save(voteOpinion);

		}
		BattleBoard board = BattleBoard.builder()
			.registUser(registUser)
			.voteInfo(voteInfo)
			.detail(createBalanceGameRequest.getDetail())
			.build();

		battleRepository.save(board);
	}

	@Override
	public Page<BalanceGameResponse> getBalanceGameByConditions(Integer category, int status, int page, User user) {
		Pageable pageable = PageRequest.of(page, 12);
		List<Object[]> list = null;

		//category가 있는지 체크
		if (category == null) {
			list = voteInfoRepository.findAllByStatus(status);
		} else {
			list = voteInfoRepository.findAllByCategoryAndStatus(category, status);
		}

		List<BalanceGameResponse> dtoResults = new ArrayList<>();

		for (Object[] result : list) {

			Long battleId = ((Number)result[0]).longValue();
			Long voteInfoId = ((Number)result[1]).longValue();
			String title = (String)result[2];
			Date startDate = (Date)result[3];
			Date endDate = (Date)result[4];
			Integer categoryId = ((Number)result[5]).intValue();
			int currentStatus = ((Number)result[6]).intValue();
			String detail = (String)result[7];

			BalanceGameResponse dto = BalanceGameResponse.builder()
				.id(battleId)
				.title(title)
				.detail(detail)
				.startDate(startDate)
				.endDate(endDate)
				.category(categoryId)
				.currentState(currentStatus)
				.build();

			List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
			List<VoteOpinionDto> voteOpinionDtos = new ArrayList<>();

			for (VoteOpinion vote : voteOpinions) {
				VoteOpinionDto voteOpinionDto = new VoteOpinionDto(vote);
				voteOpinionDto.setFinalCount(
					userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(voteInfoId, vote.getVoteOpinionIndex())
						.size());
				voteOpinionDtos.add(voteOpinionDto);
			}
			dto.setOpinions(voteOpinionDtos);

			//유저 투표정보 확인
			UserVoteOpinion uvo = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(), voteInfoId);
			if (uvo != null) {
				dto.setUserVote(uvo.getVoteInfoIndex());
			}

			dtoResults.add(dto);
		}
		return new PageImpl<>(dtoResults, pageable, dtoResults.size());
	}

	@Override
	public void deleteBalanceGame(Long id, User user) {
		BattleBoard battleBoard = battleRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Battle not found"));

		if (battleBoard.getRegistUser().getId() != user.getId()) {
			throw new RuntimeException("User not owned by this battle");
		}

		battleRepository.changeStatusById(id, 9);
	}

	@Override
	public List<BalanceGameCommentResponse> getCommentsByBattleId(Long id) {
		List<BalanceGameBoardComment> list = balanceGameRepository.findByBattleBoardId(id);
		List<BalanceGameCommentResponse> returnList = new ArrayList<>();

		for (BalanceGameBoardComment bgbc : list) {
			returnList.add(new BalanceGameCommentResponse(bgbc));
		}
		return returnList;
	}

	@Override
	public void addComment(BalanceGameCommentDto balanceGameCommentDto) {

		BalanceGameBoardComment bgbcomment = BalanceGameBoardComment.builder()
			.user(balanceGameCommentDto.getUser())
			.content(balanceGameCommentDto.getContent())
			.battleBoard(BattleBoard.builder().id(balanceGameCommentDto.getBattleBoardId()).build())
			.build();

		balanceGameRepository.save(bgbcomment);
	}

	@Override
	public List<UserVoteOpinion> getUserVotelist(User user) {
		List<UserVoteOpinion> list = userVoteOpinionRepository.findByUserId(user.getId());
		return list;
	}
}
