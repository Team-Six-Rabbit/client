package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

@Service
public class BalanceGameServiceImpl implements BalanceGameService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final BattleRepository battleRepository;
	private final BalanceGameRepository balanceGameRepository;

	public BalanceGameServiceImpl(VoteInfoRepository voteInfoRepository, VoteOpinionRepository voteOpinionRepository,
		BattleRepository battleRepository, BalanceGameRepository balanceGameRepository) {
		this.voteInfoRepository = voteInfoRepository;
		this.voteOpinionRepository = voteOpinionRepository;
		this.battleRepository = battleRepository;
		this.balanceGameRepository = balanceGameRepository;
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
	public Page<BalanceGameReturnDto> getBalanceGameByConditions(Integer category, int status, int page, User user) {
		Pageable pageable = PageRequest.of(page, 12);
		List<Object[]> list = null;
		//category가 있는지 체크
		if (category == null) {
			list = voteInfoRepository.findAllByStatus(status);
		} else {
			list = voteInfoRepository.findAllByCategoryAndStatus(category, status);
		}
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
			int categoryId = ((Number)result[6]).intValue();
			int currentStatus = ((Number)result[7]).intValue();

			if (!battleId.equals(currentBattleId)) {
				currentBattleId = battleId;
				currentOpinions = new ArrayList<>();
				currentDto = new BalanceGameReturnDto(battleId, title, currentOpinions, startDate, endDate, categoryId,
					currentStatus);
				dtoResults.add(currentDto);
			}

			currentOpinions.add(new VoteOpinionDto(opinion, voteCount));
		}

		Page<BalanceGameReturnDto> pages = new PageImpl<>(dtoResults, pageable, dtoResults.size());

		return pages;
	}

	@Override
	public void deleteBalanceGame(Long id) {
		battleRepository.deleteById(id);
	}
}
