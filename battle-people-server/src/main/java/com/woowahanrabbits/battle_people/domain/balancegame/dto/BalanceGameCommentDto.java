package com.woowahanrabbits.battle_people.domain.balancegame.dto;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @deprecated
 * avoid to use this class for @RequestBody mapping in controllers.
 *
 * Using this class in request bodies can lead to unexpected behavior and should be avoided.
 *
 * Please use {@link AddBalanceGameCommentRequest} instead
 *
 * Example usage:
 * <pre>
 *     // Do not use:
 *     @PostMapping("/someEndpoint")
 *     public void handleRequest(@RequestBody BalanceGameCommentDto dto) {
 *         // Deprecated logic
 *     }
 *
 *     // Instead use:
 *     @PostMapping("/someEndpoint")
 *     public void handleRequest(@RequestBody AddBalanceGameCommentRequest dto) {
 *         // Updated logic
 *     }
 * </pre>
 */

@Getter
@NoArgsConstructor
@Deprecated
public class BalanceGameCommentDto {
	private Long id;
	private Long battleBoardId;
	@Setter
	private User user;
	private String content;
	private Date registDate;
}
