package com.andis.score.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ScoreDto {

    private String keyword;

    private int score;
}
