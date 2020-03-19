package com.nautestech.www.service;

import java.util.HashMap;
import java.util.List;

public interface Select<T1> {
	public List<?> getView(HashMap<String, Object>param);
}
