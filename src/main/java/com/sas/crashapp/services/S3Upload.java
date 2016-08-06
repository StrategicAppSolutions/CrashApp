package com.sas.crashapp.services;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3Client;


public class S3Upload {
	
	String bucketName;
	String keyName;
	String path;
	Properties prop;
	
	public S3Upload(){
		this.prop=getProperties("AwsCredentials.properties");
		this.bucketName=prop.getProperty("bucketname");
		this.keyName=UUID.randomUUID().toString();
	}
	public String getKey(){
		return keyName;
	}
	public String getPath(){
		return path;
	}
	
	public String getBucket(){
		return bucketName;
	}
    public boolean fileUploader(FileItem item) throws IOException {
    	Properties prop=getProperties("AwsCredentials.properties");
    	AWSCredentials credentials = new BasicAWSCredentials(prop.getProperty("accessKey"),prop.getProperty("secretKey"));
        AmazonS3Client s3client = new AmazonS3Client(credentials);
        	try {
        		  ByteArrayInputStream bis = new ByteArrayInputStream(item.get());
        		  ObjectMetadata omd = new ObjectMetadata();
                  omd.setContentType(item.getContentType());
                  omd.setContentLength(item.getSize());
                  omd.setHeader("filename", item.getName());
        		  s3client.putObject(new PutObjectRequest(bucketName, keyName, bis,omd).withCannedAcl(CannedAccessControlList.PublicRead));
                  this.path=s3client.getResourceUrl(bucketName,keyName);
                  return true;
                
            } catch (AmazonServiceException ase) {
               System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was "
                    + "rejected with an error response for some reason.");

               System.out.println("Error Message:    " + ase.getMessage());
               System.out.println("HTTP Status Code: " + ase.getStatusCode());
               System.out.println("AWS Error Code:   " + ase.getErrorCode());
               System.out.println("Error Type:       " + ase.getErrorType());
               System.out.println("Request ID:       " + ase.getRequestId());

               return false;
            } catch (AmazonClientException ace) {
               System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while "
                    + "trying to communicate with S3, such as not being able to access the network.");

               return false;
             }catch (Exception e) {
            	 e.printStackTrace();
                 return false;
           }
        }
    private Properties getProperties(String propfile){
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = this.getClass().getClassLoader().getResourceAsStream(propfile);
			prop.load(input);
			return prop;
		}catch(FileNotFoundException e){
			//logger.error("getProperties config.properties file not found" +e.getMessage());
		}catch(IOException e){
			//logger.error("getProperties config.properties file reading error" +e.getMessage());
		}
		return prop;
	}
    }
   
