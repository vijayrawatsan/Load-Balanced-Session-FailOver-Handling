package in.xebia.vijay.impl;

import in.xebia.vijay.api.SessionService;

import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpSession;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private MemcachedClient memcachedClient;
	
	private static int EXPIRY = 24 * 60 * 60;
	@Override
	public <T> void set(String key, T value, HttpSession session) {
		try {
			session.setAttribute(key, value);
			memcachedClient.set(key,EXPIRY,value);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, HttpSession session) {
		try {
			if(session.getAttribute(key)!=null)
				return (T) session.getAttribute(key);
			else if(memcachedClient.get(key)!=null){
				String[] keyPart = key.split("|");
				String newKey = session.getId()+"|"+keyPart[1];
				session.setAttribute(newKey, memcachedClient.get(key));
				memcachedClient.set(newKey, EXPIRY, memcachedClient.get(key));
				memcachedClient.delete(key);
				return memcachedClient.get(newKey);
			}
			else
				return null;
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
