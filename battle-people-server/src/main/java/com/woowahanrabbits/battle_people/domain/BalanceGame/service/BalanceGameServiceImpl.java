package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.balancegame.infrastructure.BalanceGameRepository;
import com.woowahanrabbits.battle_people.domain.battle.dto.BalanceGameReturnDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
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

	public BalanceGameServiceImpl(VoteInfoRepository voteInfoRepository, VoteOpinionRepository voteOpinionRepository,
		BattleRepository battleRepository, BalanceGameRepository balanceGameRepository,
		UserVoteOpinionRepository userVoteOpinionRepository) {
		this.voteInfoRepository = voteInfoRepository;
		this.voteOpinionRepository = voteOpinionRepository;
		this.battleRepository = battleRepository;
		this.balanceGameRepository = balanceGameRepository;
		this.userVoteOpinionRepository = userVoteOpinionRepository;
	}

	@Override
	public void addBalanceGame(BattleReturnDto battleReturnDto) {
		voteInfoRepository.save(battleReturnDto.getBattleBoard().getVoteInfo());
		for (int i = 0; i < battleReturnDto.getOpinionList().size(); i++) {
			VoteOpinion voteOpinion = battleReturnDto.getOpinionList().get(i);
			voteOpinion.setVoteInfoId(battleReturnDto.getBattleBoard().getVoteInfo().getId());
			voteOpinion.setVoteOpinionIndex(i);
			if (i == 0) {
				voteOpinion.setUser(battleReturnDto.getBattleBoard().getRegistUser());
			}
			voteOpinionRepository.save(voteOpinion);
		}
		battleReturnDto.getBattleBoard().setCurrentState(4);
		battleRepository.save(battleReturnDto.getBattleBoard());
	}

	@Override
	public List<BalanceGameReturnDto> getBalanceGameByConditions(int category, int status, int page, User user) {
		Pageable pageable = PageRequest.of(page, 12);
		List<Object[]> list = userVoteOpinionRepository.findAllVotesWithCountsAndUserVoteStatus(user.getId());

		System.out.println(list.toString());

		List<BalanceGameReturnDto> dtoResults = new ArrayList<>();
		Long currentBattleId = null;
		BalanceGameReturnDto currentDto = null;
		List<VoteOpinionDto> currentOpinions = null;

		for (Object[] result : list) {
			Long battleId = ((Number)result[0]).longValue();
			String title = (String)result[1];
			String opinion = (String)result[2];
			int voteCount = ((Number)result[3]).intValue();
			Date startDate = (Date)result[4];
			Date endDate = (Date)result[5];

			if (!battleId.equals(currentBattleId)) {
				currentBattleId = battleId;
				currentOpinions = new ArrayList<>();
				currentDto = new BalanceGameReturnDto(battleId, title, currentOpinions, startDate, endDate);
				dtoResults.add(currentDto);
			}

			currentOpinions.add(new VoteOpinionDto(opinion, voteCount));
		}

		return dtoResults;
	}

	@Override
	public void deleteBalanceGame(Long id) {
		battleRepository.deleteById(id);
	}
}
