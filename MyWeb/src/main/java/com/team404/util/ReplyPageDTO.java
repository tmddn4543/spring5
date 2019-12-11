package com.team404.util;

import java.util.ArrayList;

import com.team404.command.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyPageDTO {
	private ArrayList<ReplyDTO> list;
	private int replyCount ;
}
