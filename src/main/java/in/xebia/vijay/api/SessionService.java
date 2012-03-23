package in.xebia.vijay.api;

import javax.servlet.http.HttpSession;

public interface SessionService {

	<T> void  set(String key, T value, HttpSession session);
	<T> T get(String key, HttpSession session);
}
