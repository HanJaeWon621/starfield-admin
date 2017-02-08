package kr.co.starfield.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.FileMapper;
import kr.co.starfield.model.CdnUploadBean;
import kr.co.starfield.model.ImgFile;
import kr.co.starfield.model.vo.ImgFileVo;

@Service
public class FileService {
	
	private Logger ll = LoggerFactory.getLogger(FileService.class);
		
	@Value("${file.img.upload.host1}")
	String imgUploadHost1;
	@Value("${file.img.upload.host2}")
	String imgUploadHost2;
	@Value("${file.img.upload.uri}")
	String imgUploadUri;
	
	@Value("${file.img.upload.mode}")
	String imgUploadMode;

	@Autowired
	FileMapper fileMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	public ImgFile regImgFile(ImgFileVo vo) throws BaseException {
		
		fileMapper.regImgFile(vo);
		
		ll.info("after reg image vo is {}", vo);
		
		return vo.toModel();
	}
	
	public String getImgSeq() throws BaseException {
		
		String fileSeq = fileMapper.getImgSeq();
		
		ll.info("file seq is {}", fileSeq);
		
		return fileSeq;
	}
	
	public void upload(CdnUploadBean bean) throws Exception {
		
		ll.info("cdn upload bean is {}", bean);
		
		PostMethod post1 = new PostMethod("http://" + imgUploadHost1 + imgUploadUri);
		PostMethod post2 = new PostMethod("http://" + imgUploadHost2 + imgUploadUri);
		
		HttpClient client1 = new HttpClient();
		HttpClient client2 = new HttpClient();
		 
		int statusCode;
		int statusCode1;
		int statusCode2;
		
		try {
			
			List<Part> parts = new ArrayList<Part>();
			parts.add(new StringPart("dir", bean.dir));
			parts.add(new StringPart("ext", bean.ext));
			parts.add(new StringPart("seq", bean.seq));

			File file = bean.file;
			parts.add(new FilePart("cdn-file", URLEncoder.encode(bean.filename, "utf-8"), file));
			
			post1.setRequestEntity(
				new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), post1.getParams())
			);
			
			client1.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
			statusCode1 = client1.executeMethod(post1); //POST 1
			
			if(imgUploadMode.equals("opr")) {
				
				post2.setRequestEntity(
					new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), post2.getParams())
				);
				
				client2.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
				statusCode2 = client2.executeMethod(post2); //POST 2
				
			} else {
				statusCode2 = 200;
			}
			
			statusCode = (statusCode1 == 200 && statusCode2 == 200) ? 200 : (statusCode1 != 200 ? statusCode1 : statusCode2);
			
			switch(statusCode) {
			case 200 :
				ll.info("response1 msg is {}", post1.getResponseBodyAsString());
				
				if(imgUploadMode.equals("opr")) {
					ll.info("response2 msg is {}", post2.getResponseBodyAsString());
				}
				break;
				
			default :
				
				ll.info("status code is {}", statusCode);
				ll.info("response1 msg is {}", post1.getResponseBodyAsString());
				
				if(imgUploadMode.equals("opr")) {
					ll.info("response2 msg is {}", post2.getResponseBodyAsString());
				}
				
				throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD);
			}
			 
		} catch (Exception err) {
			
			throw err;
			
		} finally{
			
			post1.releaseConnection();
			post2.releaseConnection();
		}
	}
	
	public void uploadWithFilePath(CdnUploadBean bean) throws Exception {
		
		ll.info("cdn upload bean is {}", bean);
		
		PostMethod post1 = new PostMethod("http://" + imgUploadHost1 + imgUploadUri);
		PostMethod post2 = new PostMethod("http://" + imgUploadHost2 + imgUploadUri);
		
		HttpClient client1 = new HttpClient();
		HttpClient client2 = new HttpClient();
		 
		int statusCode;
		int statusCode1;
		int statusCode2;
		
		File file = null;
		
		try {
			
			List<Part> parts = new ArrayList<Part>();
			parts.add(new StringPart("dir", bean.dir));
			parts.add(new StringPart("ext", bean.ext));
			parts.add(new StringPart("seq", bean.seq));
			
			ll.info("bean srcPath is {}", bean.srcPath);

			file = new File(bean.srcPath);
			parts.add(new FilePart("cdn-file", URLEncoder.encode(bean.filename, "utf-8"), file));
			
			post1.setRequestEntity(
				new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), post1.getParams())
			);
			
			client1.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
			statusCode1 = client1.executeMethod(post1); //POST 1
			
			if(imgUploadMode.equals("opr")) {
				
				post2.setRequestEntity(
					new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), post2.getParams())
				);
				
				client2.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
				statusCode2 = client2.executeMethod(post2); //POST 2
				
			} else {
				statusCode2 = 200;
			}
			
			statusCode = (statusCode1 == 200 && statusCode2 == 200) ? 200 : (statusCode1 != 200 ? statusCode1 : statusCode2);
			
			switch(statusCode) {
			case 200 :
				
				ll.info("response1 msg is {}", post1.getResponseBodyAsString());
				
				if(imgUploadMode.equals("opr")) {
					ll.info("response2 msg is {}", post2.getResponseBodyAsString());
				}
				
				break;
				
			default :
				
				ll.info("status code is {}", statusCode);
				ll.info("response1 msg is {}", post1.getResponseBodyAsString());
				
				if(imgUploadMode.equals("opr")) {
					ll.info("response2 msg is {}", post2.getResponseBodyAsString());
				}
				
				throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD);
			}
			 
		} catch (Exception err) {
			
			throw err;
			
		} finally{
			
			post1.releaseConnection();
			post2.releaseConnection();
			
			if(file != null && file.isFile()) {
				file.delete();
			}
		}
	}

}
