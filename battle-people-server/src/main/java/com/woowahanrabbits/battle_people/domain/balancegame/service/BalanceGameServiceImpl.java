package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.balancegame.domain.BalanceGameBoardComment;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.AddBalanceGameCommentRequest;
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

	@Autowired
	private VoteInfoRepository voteInfoRepository;
	@Autowired
	private VoteOpinionRepository voteOpinionRepository;
	@Autowired
	private BattleRepository battleRepository;
	@Autowired
	private BalanceGameRepository balanceGameRepository;
	@Autowired
	private UserVoteOpinionRepository userVoteOpinionRepository;
	@Autowired
	private UserRepository userRepository;

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
			.currentState(5)
			.build();

		battleRepository.save(board);
	}

	@Override
	public List<BalanceGameResponse> getBalanceGameByConditions(Integer category, int status, int page, User user) {
		Pageable pageable = PageRequest.of(page, 12);
		List<Object[]> list = (category == null)
			? voteInfoRepository.findAllByStatus(status)
			: voteInfoRepository.findAllByCategoryAndStatus(category, status);

		List<BalanceGameResponse> dtoResults = list.stream()
			.map(result -> {
				Long voteInfoId = ((Number)result[1]).longValue();
				BalanceGameResponse dto = convertToBalanceGameResponse(result);
				List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
				List<VoteOpinionDto> voteOpinionDtos = convertToVoteOpinionDtos(voteInfoId, voteOpinions);
				dto.setOpinions(voteOpinionDtos);
				setUserVoteInfo(dto, user, voteInfoId);
				return dto;
			})
			.collect(Collectors.toList());

		return new PageImpl<>(dtoResults, pageable, dtoResults.size()).toList();
	}

	@Override
	public void deleteBalanceGame(Long id, User user) {
		BattleBoard battleBoard = battleRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Battle not found"));

		if (battleBoard.getRegistUser().getId() != (user.getId())) {
			throw new RuntimeException("User not owned by this battle");
		}

		battleRepository.changeStatusById(id, 9);
	}

	@Override
	public List<BalanceGameCommentResponse> getCommentsByBattleId(Long id) {
		List<BalanceGameBoardComment> list = balanceGameRepository.findByBattleBoardId(id);
		return list.stream()
			.map(BalanceGameCommentResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	public void addComment(AddBalanceGameCommentRequest addBalanceGameCommentRequest, User user) {
		BalanceGameBoardComment bgbcomment = BalanceGameBoardComment.builder()
			.user(user)
			.content(addBalanceGameCommentRequest.getContent())
			.battleBoard(battleRepository.findById(addBalanceGameCommentRequest.getBattleId()).orElseThrow())
			.build();

		balanceGameRepository.save(bgbcomment);
	}

	@Override
	public List<UserVoteOpinion> getUserVotelist(User user) {
		return userVoteOpinionRepository.findByUserId(user.getId());
	}

	@Override
	public BalanceGameResponse getBalanceGameById(Long id, User user) {
		Object[] obj = voteInfoRepository.findByBattleId(id).get(0);
		Long voteInfoId = ((Number)obj[1]).longValue();
		BalanceGameResponse balanceGameResponse = convertToBalanceGameResponse(obj);
		List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
		List<VoteOpinionDto> voteOpinionDtos = convertToVoteOpinionDtos(voteInfoId, voteOpinions);
		balanceGameResponse.setOpinions(voteOpinionDtos);
		setUserVoteInfo(balanceGameResponse, user, voteInfoId);
		return balanceGameResponse;
	}

	private BalanceGameResponse convertToBalanceGameResponse(Object[] obj) {
		Long battleId = ((Number)obj[0]).longValue();
		String title = (String)obj[2];
		Date startDate = (Date)obj[3];
		Date endDate = (Date)obj[4];
		Integer categoryId = ((Number)obj[5]).intValue();
		int currentStatus = ((Number)obj[6]).intValue();
		String detail = (String)obj[7];

		return BalanceGameResponse.builder()
			.id(battleId)
			.title(title)
			.detail(detail)
			.startDate(startDate)
			.endDate(endDate)
			.category(categoryId)
			.currentState(currentStatus)
			.build();
	}

	private List<VoteOpinionDto> convertToVoteOpinionDtos(Long voteInfoId, List<VoteOpinion> voteOpinions) {
		List<VoteOpinionDto> voteOpinionDtos = new ArrayList<>();
		int totalVotes = 0;
		int[] cnt = new int[voteOpinions.size()];

		for (int i = 0; i < voteOpinions.size(); i++) {
			VoteOpinion vote = voteOpinions.get(i);
			VoteOpinionDto voteOpinionDto = new VoteOpinionDto(vote);
			cnt[i] = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(voteInfoId, vote.getVoteOpinionIndex())
				.size();
			voteOpinionDto.setCount(cnt[i]);
			voteOpinionDtos.add(voteOpinionDto);
			totalVotes += cnt[i];
		}

		if (totalVotes > 0) {
			for (int i = 0; i < voteOpinions.size(); i++) {
				int percentage = (cnt[i] * 100) / totalVotes;
				voteOpinionDtos.get(i).setPercentage(percentage);
			}
		} else {
			voteOpinionDtos.forEach(dto -> dto.setPercentage(0));
		}

		return voteOpinionDtos;
	}

	private void setUserVoteInfo(BalanceGameResponse dto, User user, Long voteInfoId) {
		UserVoteOpinion uvo = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(), voteInfoId);
		if (uvo != null) {
			dto.setUserVote(uvo.getVoteInfoIndex());
		}
	}
}
