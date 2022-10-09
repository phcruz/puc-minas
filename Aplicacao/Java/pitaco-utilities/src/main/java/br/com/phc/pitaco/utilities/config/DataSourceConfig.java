package br.com.phc.pitaco.utilities.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import br.com.phc.pitaco.utilities.Constantes;

@ConditionalOnProperty(name = Constantes.ENABLE_DATASOURCE, havingValue = "true", matchIfMissing = false)
@Component
public class DataSourceConfig {

	@Autowired
	private HikariPropertiesConfig poolProperties;

	@Bean
	public DataSource getDataSource() {

		HikariConfig config = new HikariConfig();
		config.setPoolName(poolProperties.getPoolName());
		config.setConnectionTestQuery(poolProperties.getConnectionTestQuery());
		config.setMaximumPoolSize(poolProperties.getMaximumPoolSize());
		config.setDriverClassName(poolProperties.getDriverClassName());
		config.setJdbcUrl(poolProperties.getJdbcUrl());
		config.setUsername(poolProperties.getUsername());
		config.setPassword(poolProperties.getPassword());
		config.setConnectionTimeout(poolProperties.getConnectionTimeout());
		config.setIdleTimeout(poolProperties.getIdleTimeout());
		config.setMaxLifetime(poolProperties.getMaxLifetime());
		config.setMinimumIdle(poolProperties.getMinimumIdle());
		config.setInitializationFailTimeout(poolProperties.getInitializationFailTimeout());
		config.setIsolateInternalQueries(poolProperties.isIsolateInternalQueries());
		config.setValidationTimeout(poolProperties.getValidationTimeout());

		config.addDataSourceProperty("cachePrepStmts", poolProperties.isCachePrepStmts());
		config.addDataSourceProperty("prepStmtCacheSize", poolProperties.getPrepStmtCacheSize());
		config.addDataSourceProperty("prepStmtCacheSqlLimit", poolProperties.getPrepStmtCacheSqlLimit());
		config.addDataSourceProperty("useServerPrepStmts", poolProperties.isUseServerPrepStmts());
		config.addDataSourceProperty("useLocalSessionState", poolProperties.isUseLocalSessionState());
		config.addDataSourceProperty("rewriteBatchedStatements", poolProperties.isRewriteBatchedStatements());
		config.addDataSourceProperty("cacheResultSetMetadata", poolProperties.isCacheResultSetMetadata());
		config.addDataSourceProperty("cacheServerConfiguration", poolProperties.isCacheServerConfiguration());
		config.addDataSourceProperty("elideSetAutoCommits", poolProperties.isElideSetAutoCommits());
		config.addDataSourceProperty("maintainTimeStats", poolProperties.isMaintainTimeStats());

		return new HikariDataSource(config);
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
}