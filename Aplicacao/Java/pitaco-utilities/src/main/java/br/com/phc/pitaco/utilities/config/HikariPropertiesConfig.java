package br.com.phc.pitaco.utilities.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("pitaco.datasource")
public class HikariPropertiesConfig {

	private String jdbcUrl;
	private String username;
	private String password;
	private Integer maximumPoolSize;
	private String poolName;
	private String connectionTestQuery;
	private String driverClassName;

	private Integer connectionTimeout;
	private Integer idleTimeout;
	private Integer maxLifetime;
	private Integer minimumIdle;
	private Integer initializationFailTimeout;
	private boolean isolateInternalQueries;
	private Integer validationTimeout;

	private boolean cachePrepStmts;
	private Integer prepStmtCacheSize;
	private Integer prepStmtCacheSqlLimit;
	private boolean useServerPrepStmts;
	private boolean useLocalSessionState;
	private boolean rewriteBatchedStatements;
	private boolean cacheResultSetMetadata;
	private boolean cacheServerConfiguration;
	private boolean elideSetAutoCommits;
	private boolean maintainTimeStats;
}
