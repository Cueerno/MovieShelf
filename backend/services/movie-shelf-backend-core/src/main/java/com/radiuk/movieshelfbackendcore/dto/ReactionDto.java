package com.radiuk.movieshelfbackendcore.dto;

import com.radiuk.movieshelfbackendcore.model.Reaction;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto {

    private Reaction.ReactionType reactionType;
}
