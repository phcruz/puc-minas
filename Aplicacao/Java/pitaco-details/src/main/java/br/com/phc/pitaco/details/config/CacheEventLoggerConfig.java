package br.com.phc.pitaco.details.config;

import java.util.Date;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import br.com.phc.pitaco.utilities.util.UtilData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheEventLoggerConfig implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		log.info(String.format("Cache: %s: %s -> %s", cacheEvent.getType().name(), cacheEvent.getKey(), this.criaDataHora()));
	}

	private String criaDataHora() {
		return UtilData.formatarDataString(new Date());
	}
}
