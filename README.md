# query-export-maven-plugin

Maven plugin for auto generating query-export


Add maven plugin configuration : 

			<plugin>
				<groupId>org.fugerit.java</groupId>
				<artifactId>query-export-maven-plugin</artifactId>
				<version>X.X.X</version>	
				<configuration>
					<configPath>path-to/query-export-config.xml</configPath>
					<idCatalog>sample</idCatalog>		
					<dbConfig>path-to/db-sample.properties</dbConfig>	
				</configuration>							
				<executions>
					<execution>
						<id>openapi</id>
						<goals>
							<goal>generate</goal>
						</goals>
					</execution>		
				</executions>
				<dependencies>
					<dependency>
					    <groupId>jdnc-driver</groupId>
					    <artifactId>dependancy</artifactId>
					    <version>version</version>					
					</dependency>
				</dependencies>				
			</plugin>	
			
And sample query-exprt-config :

<query-catalog-catalog>

	<query-catalog id="sample-catalog">
		<!-- 1 - dec tipo evento -->
		<query id="sample_csv"
			sql="SELECT * SAMPLE"
			outputFile="target/sample/sample.csv"
			outputFormat="csv"
			createPath="1"
		/>	
		<query id="dec_tipo_evento_xlsx"
			sql="SELECT * SAMPLE"
			outputFile="target/sample/sample.xslx"
			outputFormat="xlsx"
			xlsResize="1"
			createPath="1"
		/>					
	</query-catalog>
	

</query-catalog-catalog>


Finally run : 

mvn query-export:generate