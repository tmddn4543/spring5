package com.study04.www.service;

import java.util.HashMap;

public interface IView<T1> {
	public T1 getView(HashMap<String, Object> param);
}
