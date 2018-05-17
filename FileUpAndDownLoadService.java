package com.rest.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/*
 * This  Service was developed as a part of file upload and download service
 * Fujitsu Computers Inc . This is a skeleton code full business logic 
 * was removed to due Non disclosure reason. 
 * 
 */

@Path("/file")
public class FileUpAndDownLoadService {

 
    private static final String FOLDER_PATH = "C:\\Users\\Documents\\test\\";
     
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
   
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(@FormDataParam("file") InputStream fis,
                    @FormDataParam("file") FormDataContentDisposition fdcd) {
  
        OutputStream outpuStream = null;
       
        
        String fileName = fdcd.getFileName();


        System.out.println("File Name: " + fdcd.getFileName());
        
        
        String filePath = FOLDER_PATH + fileName;
         
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            
            System.out.println("File before write ouput  " + filePath );
            
            
            outpuStream = new FileOutputStream(new File(filePath));
            while ((read = fis.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch(IOException iox){
            iox.printStackTrace();
        } finally {
            if(outpuStream != null){
                try{outpuStream.close();} catch(Exception ex){}
            }
        }
        return "File Uploaded  Successfully Using Restful Service !!";
    }
    @GET
    @Path("/urlDownload")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response downloadImageFile() {
 
        // set file (and path) to be download
        File file = new File("C:/Users/Documents/test/testimage.jpg");
 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"testimage.jpg\"");
        return responseBuilder.build();
    }
 
    @GET
    @Path("/download")
    @Produces({"image/png", "image/jpg", "image/gif"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response downloadFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd 
    		) {
 
        // set file (and path) to be download
    	
        String fileName = fdcd.getFileName();

        System.out.println("File Name display before   " );

        System.out.println("File Name: " + fdcd.getFileName());

        System.out.println("File Name display after  " );
        
        File file = new File(fileName);

        System.out.println("File Name: " + file);

        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename="+file);
        return responseBuilder.build();
    }
    
    
    
    
}
