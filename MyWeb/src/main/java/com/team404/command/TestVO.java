package com.team404.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //초기화생성자
@NoArgsConstructor //기본생성자
public class TestVO {
	private int age;
	private String name;
	private String id;
}
