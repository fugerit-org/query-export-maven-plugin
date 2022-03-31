package org.fugerit.java.query.export.maven;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.query.export.catalog.QueryConfig;
import org.fugerit.java.query.export.catalog.QueryConfigCatalog;
import org.fugerit.java.query.export.facade.format.QueryExportHandlerXLSBase;
import org.fugerit.java.query.export.tool.QueryExportToolMain;

@Mojo( name = "generate")
public class MojoGenerate extends AbstractMojo {
	
    @Parameter(property = "configPath", required = true )
    private String configPath;
    
    @Parameter(property = "idCatalog", required = true )
    private String idCatalog;
    
    @Parameter(property = "dbConfig", required = true )
    private String dbConfig;
    
    private void addIfNotEmpty( Properties props, String key, String value ) {
    	if ( StringUtils.isNotEmpty( value ) ) {
    		props.setProperty( key , value );
    	}
    }
    
    public void execute() throws MojoExecutionException {
        try {
        	FileInputStream fis = new FileInputStream( new File( this.configPath ) );
        	try {
        		QueryConfigCatalog catalog = new QueryConfigCatalog();
        		catalog = (QueryConfigCatalog)QueryConfigCatalog.load( fis , catalog );
        		this.getLog().info( "keys : "+catalog.getIdSet() );
        		ListMapStringKey<QueryConfig> queryConfig = catalog.getListMap( this.idCatalog );
        		for ( QueryConfig current : queryConfig ) {
        			Properties props = new Properties();
        			props.setProperty( QueryExportToolMain.ARG_DB_CONFIG , this.dbConfig );
                	this.addIfNotEmpty(props, QueryExportToolMain.ARG_QUERY_SQL, current.getSql() );
                	this.addIfNotEmpty(props, QueryExportToolMain.ARG_OUTPUT_FILE, current.getOutputFile() );
                	this.addIfNotEmpty(props, QueryExportToolMain.ARG_CSV_SEPARATOR, current.getCsvSeparator() );
                	this.addIfNotEmpty(props, QueryExportHandlerXLSBase.ARG_XLS_RESIZE, current.getXlsResize() );
                	this.addIfNotEmpty(props, QueryExportToolMain.ARG_OUTPUT_FORMAT, current.getOutputFormat() );
                	this.addIfNotEmpty(props, QueryExportToolMain.ARG_CREATE_PATH, current.getCreatePath() );
                	getLog().info( "using parameters -> "+props );
                	QueryExportToolMain.worker( props );
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        		throw new MojoExecutionException( "Error generating code : "+e, e );	
        	} finally {
        		fis.close();
			}
        	
        } catch (Exception e) {
        	throw new MojoExecutionException( "Error generating code : "+e, e );
        }
    }

}