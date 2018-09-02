package com.agoda.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.agoda.constants.Constants;
import com.agoda.model.RateLimiter;

@Component
public class RateLimitingInterceptor extends HandlerInterceptorAdapter {

	@Value("${rate.limit.enabled}")
	private boolean enabled;

	@Value("${city.hit.limit}")
	private int cityHitLimit;
	@Value("${city.hit.duration}")
	private int cityHitDuration;
	@Value("${city.suspension.duration}")
	private int citySuspensionDuration;

	@Value("${hotel.hit.limit}")
	private int hotelHitLimit;
	@Value("${hotel.hit.duration}")
	private int hotelHitDuration;
	@Value("${hotel.suspension.duration}")
	private int hotelSuspensionDuration;

	private ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean res = true;
		if (enabled) {
			String uri = request.getRequestURI();
			String cat = uri.substring(Constants.BASE_URL.length() + 2);
			cat = cat.substring(0, cat.indexOf("/"));
			int maxPermits = Constants.DEFAULT_HIT_LIMIT;
			int suspensionDuration = Constants.DEFAULT_SUSPENSION_LIMIT;
			int hitDuration = Constants.DEFAULT_HIT_DURATION;
			if (Constants.CITY_URL.equalsIgnoreCase(cat)) {
				maxPermits = cityHitLimit;
				suspensionDuration = citySuspensionDuration;
				hitDuration = cityHitDuration;
			} else if (Constants.HOTEL_URL.equalsIgnoreCase(cat)) {
				maxPermits = hotelHitLimit;
				suspensionDuration = hotelSuspensionDuration;
				hitDuration = hotelHitDuration;
			}
			RateLimiter limiter = getRateLimiter(cat, maxPermits, suspensionDuration, hitDuration);
			boolean allowRequest = limiter.rateCheck();
			if (!allowRequest) {
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			}
			response.addHeader("RateLimit Exceeded", String.valueOf(maxPermits));
			return allowRequest;
		}
		return res;
	}

	private RateLimiter getRateLimiter(String id, int maxPermits, long suspensionDuration, int hitDuration) {
		if (limiters.containsKey(id)) {
			return limiters.get(id);
		} else {
			synchronized (id.intern()) {
				// double-checked locking to avoid multiple-reinitializations
				if (limiters.containsKey(id)) {
					return limiters.get(id);
				}
				RateLimiter rateLimiter = new RateLimiter(maxPermits, suspensionDuration, hitDuration);
				limiters.put(id, rateLimiter);
				return rateLimiter;
			}
		}
	}

	@PreDestroy
	public void destroy() {
		// loop and finalize all limiters
	}
}