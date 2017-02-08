/*
 * FileController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 SYPE. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.model.CdnUploadBean;
import kr.co.starfield.model.ImgFile;
import kr.co.starfield.service.FileService;

/**
 *  파일업로드 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest/{bcn_cd}/file")
public class FileRestController {

	@Value("${file.img.path}")
	String imgPath;
	@Value("${cdn.img.path}")
	String cdnImgPath;
		
	@Autowired
	FileService fileService;
	
	private static final Logger ll = LoggerFactory.getLogger(FileRestController.class);
	
	@RequestMapping(value = "/images"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8")
	public ImgFile imgFileUpload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile multipartFile
    		, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException, Exception {
		
		CdnUploadBean bean = new CdnUploadBean();
		ImgFile imgFile = new ImgFile();
		
		File file = null;
		
		try {
			
			String ext = null;
			String contentType = multipartFile.getContentType();
			String fileSeq = fileService.getImgSeq();
			file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String todayPath = String.format("%04d%02d%02d", year, month, day);
			
			String imgDir = Paths.get(imgPath, "normal", todayPath).toString();
			String imgUrl = Paths.get(cdnImgPath, "normal", todayPath).toString();
			
			ll.info("imgDir is {}", imgDir);
			ll.info("imgUrl is {}", imgUrl);
			
			imgDir = imgDir.replace("\\", "/");
			
			if(!multipartFile.getContentType().contains("image/")) {
				
				throw new BaseException(ErrorCode.File.MIME_TYPE_NOT_MATCH_BY_IMG);
				
			} else {
				
				ext = contentType.split("/")[1];
			}
		
			bean.dir = imgDir;
			bean.ext = ext;
			bean.seq = fileSeq;
			bean.filename = multipartFile.getName();
			bean.file = file;
			
			fileService.upload(bean);
			
			imgFile.img_seq = fileSeq;
			imgFile.bcn_cd = bcn_cd;
			imgFile.img_pys_loc = Paths.get(imgDir, (fileSeq + "." + ext)).toString();
			imgFile.img_uri = Paths.get(imgUrl, (fileSeq + "." + ext)).toString();
			imgFile.img_thmb = " ";
			imgFile.img_thmb_uri = " ";
			
			ll.info("return imgFile info {}", imgFile);
			
		} catch(BaseException bErr) {
			throw bErr;
		} catch(Exception err) {
			
			ll.error("ERROR MSG IS {}", err.getMessage());
			System.out.println("err>>>"+ err.toString());
			throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD);
			
		} finally {
			
			if(file != null && file.isFile()) {
				file.delete();
			}
		}
		
		fileService.regImgFile(imgFile.toVo());
		
		return imgFile;
	}
	
	@RequestMapping(value = "/files"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8")
	public ImgFile genFileUpload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile multipartFile
    		, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException, Exception {
		
		CdnUploadBean bean = new CdnUploadBean();
		ImgFile imgFile = new ImgFile();
		FileOutputStream fos1 = null;
		File file = null;
		
		try {
			
			String ext = null;
			String contentType = multipartFile.getContentType();
			String fileSeq = fileService.getImgSeq();
			System.out.println("multipartFile.getOriginalFilename()" + multipartFile.getOriginalFilename());
			System.out.println("multipartFile.getName()"+multipartFile.getName());
			System.out.println("multipartFile.getName()"+multipartFile);
			file = new File(multipartFile.getOriginalFilename());
			
			//multipartFile.transferTo(file);
			
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String todayPath = String.format("%04d%02d%02d", year, month, day);
			
			String imgDir = Paths.get(imgPath, "normal", todayPath).toString();
			String imgUrl = Paths.get(cdnImgPath, "normal", todayPath).toString();
			
			ll.info("imgDir is {}", imgDir);
			ll.info("imgUrl is {}", imgUrl);
			
			imgDir = imgDir.replace("\\", "/");
			
			//ext = multipartFile.getOriginalFilename().split(".")[1];
			String of=multipartFile.getOriginalFilename();
			ext = of.substring(of.lastIndexOf(".")+1,of.length());
			System.out.println("ext>>>"+ext);
			bean.dir = imgDir;
			bean.ext = ext;
			bean.seq = fileSeq;
			bean.filename = multipartFile.getName();
			String fullFileName=fileSeq+"."+ext;
			bean.file = file;
			File dir = new File(imgDir);
			dir.mkdirs();
			dir.setWritable(true);
			
			fos1 = new FileOutputStream(imgUrl+"/"+fullFileName);
			//fos1.write(file.getBytes());
			fos1.write(multipartFile.getBytes());
			fos1.flush();
			fos1.close();
			//fileService.upload(bean);
			
			imgFile.img_seq = fileSeq;
			imgFile.bcn_cd = bcn_cd;
			imgFile.img_pys_loc = Paths.get(imgDir, (fileSeq + "." + ext)).toString();
			imgFile.img_uri = Paths.get(imgUrl, (fileSeq + "." + ext)).toString();
			imgFile.img_thmb = " ";
			imgFile.img_thmb_uri = " ";
			
			ll.info("return imgFile info {}", imgFile);
			
		} catch(BaseException bErr) {
			throw bErr;
		} catch(Exception err) {
			
			ll.error("ERROR MSG IS {}", err.getMessage());
			
			throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD);
			
		} finally {
			
			if(file != null && file.isFile()) {
				file.delete();
			}
		}
		
		fileService.regImgFile(imgFile.toVo());
		
		return imgFile;
	}

	/*@RequestMapping(value = "/images"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			)
    public ImgFile imgFileUpload(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile file
    		, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException, Exception {
		
		String contentType = file.getContentType();
		String ext = null;
		ImgFile rFile = null;
		FileOutputStream fos1 = null;
		
		try {
			
			if(!contentType.contains("image/")) {
			
				throw new BaseException(ErrorCode.File.MIME_TYPE_NOT_MATCH_BY_IMG);
				
			} else {
				
				ext = contentType.split("/")[1];
			}
			
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			int sec = cal.get(Calendar.SECOND);
			
			String todayPath = String.format("%04d%02d%02d", year, month, day);
			String imgDir = Paths.get(imgPath, todayPath).toString();
			String imgThumbDir = Paths.get(imgDir, imgThumbPath).toString();
			
			new File(imgDir).mkdirs();
			new File(imgThumbDir).mkdirs();
			
			String sNow = String.format("%04d%02d%02d%02d%02d%02d", year, month, day, hour, min, sec);
			String reImgName = sNow.concat(getMD5(file.getName(), 10)).concat(".").concat(ext);
			String thumbImgName = "thumb_".concat(reImgName);
			
			String imgUrl = Paths.get(imgDir, reImgName).toString();
			String imgThumbUrl = Paths.get(imgThumbDir, thumbImgName).toString();
			
			String cdnImgUrl = Paths.get(cdnImgPath, todayPath, reImgName).toString();
			String cdnThumbUrl = Paths.get(cdnImgPath, todayPath, imgThumbPath, thumbImgName).toString();
			
			BufferedImage bufferImg = null;
			BufferedImage bufferThumbImg = null;
			
			fos1 = new FileOutputStream(imgUrl);
			fos1.write(file.getBytes());
			fos1.flush();
			fos1.close();
			
			// 썸네일 이미지 생성
			bufferImg = ImageIO.read(file.getInputStream());
			bufferThumbImg = new BufferedImage(160,  160,  BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphic = bufferThumbImg.createGraphics();
			graphic.drawImage(bufferImg, 0, 0, 160, 160, null);
			ImageIO.write(bufferThumbImg, ext, new File(Paths.get(imgThumbDir, thumbImgName).toString()));
			
			ImgFileVo vo = new ImgFileVo();
			vo.bcn_cd = bcn_cd;
			vo.img_pys_loc = imgUrl;
			vo.img_thmb = imgThumbUrl;
			vo.img_uri = cdnImgUrl;
			vo.img_thmb_uri = cdnThumbUrl;
			
			// 이미지 DB 등록
			rFile = fileService.regImgFile(vo);
			
		} catch (FileNotFoundException e) {
			
			// 업로드 대상 파일을 찾을 수 없음.
			throw new BaseException(ErrorCode.File.NOT_FOUND_UPLOAD_TARGET);
			
		} catch (BaseException be) {
			
			// 소스 중간 발생된 BaseException 발생
			ll.info(be.getErrMsg());
			throw be;
			
		} catch (Exception e) {
			
			// 이미지 업로드 중 알수 없는 에러 발생
			ll.info(e.getMessage());
			throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD);
			
		} finally {
			
			if(fos1 != null) {
				try { fos1.close(); } catch (Exception e) {
					
					// 이미지 파일 자원 반 중 알수 없는 에러 발생
					throw new BaseException(ErrorCode.File.UN_KNOWN_EXCEPTION_WHEN_TRUN_IMG_FILE_RESOURCE);
				}
			}
		}
		
		return rFile;
    }*/
	
//	@RequestMapping(value = "/FileUpload", method = {RequestMethod.POST, RequestMethod.GET}, produces = {"text/plain"})
//    public @ResponseBody String FileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
//		String contentType = file.getContentType();
//		String ext = null;
//		ImgFileUploadResult result = new ImgFileUploadResult();
//		FileOutputStream fos1 = null;
//		
//		try {
//		
//			if(!contentType.contains("image/")) { 
//				return "{\"code\":-4,\"desc\":\"Not an image file.\"}";
//			
//			} else { 
//				ext = contentType.split("/")[1];
//			}
//			
//	        Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);
//			int month = cal.get(Calendar.MONTH) + 1;
//			int day = cal.get(Calendar.DAY_OF_MONTH);
//			int hour = cal.get(Calendar.HOUR_OF_DAY);
//			int min = cal.get(Calendar.MINUTE);
//			int sec = cal.get(Calendar.SECOND);
//			
//			String todayPath = String.format("%04d%02d%02d/", year, month, day);
//			String imgDir = imgPath + todayPath;
//			String imgThumbnailDir = imgThumbnailPath + todayPath;
//			
//			new File(imgDir).mkdirs();
//			new File(imgThumbnailDir).mkdirs();
//			
//			String sNow = String.format("%04d%02d%02d%02d%02d%02d", year, month, day, hour, min, sec);
//			String originImgName = sNow + getMD5(file.getName(), 10) + "." + ext;
//			String thumbnailImgName = "thumb"+originImgName;
//			
//			String orignUrl = originUrl_prefix + todayPath + originImgName;
//			String thumbnailUrl = thumbnailUrl_prefix + todayPath + thumbnailImgName;
//			
//			BufferedImage buffer_original_image = null;
//			BufferedImage buffer_thumbnail_image = null;
//		
//			fos1 = new FileOutputStream(imgDir + originImgName);
//			fos1.write(file.getBytes());
//			fos1.flush();
//			fos1.close();
//			
//			// 썸네일 이미지 생성
//			buffer_original_image = ImageIO.read(file.getInputStream());
//			buffer_thumbnail_image = new BufferedImage(160,  160,  BufferedImage.TYPE_3BYTE_BGR);
//			Graphics2D graphic = buffer_thumbnail_image.createGraphics();
//			graphic.drawImage(buffer_original_image, 0, 0, 160, 160, null);
//			ImageIO.write(buffer_thumbnail_image, "jpeg", new File(imgThumbnailDir + thumbnailImgName));
//			
//			result.file_name = originImgName;
//			result.file_storage_path = imgDir;
//			result.file_url = orignUrl;
//			
//			result.thumbnail_name = thumbnailImgName;
//			result.thumbnail_storage_path = imgThumbnailDir;
//			result.thumbnail_url = thumbnailUrl;
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			result.code = -97;
//			result.desc = e.getMessage() + " file not found";
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			result.code = -98;
//			result.desc = e.getMessage() + " IOException";
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.code = -99;
//			result.desc = e.getMessage() + " Exception";
//			
//		} finally {
//			if(fos1 != null) {
//				try { fos1.close(); } catch (IOException e) {}
//			}
//		}
//		
//		ObjectMapper jsonMapper = new ObjectMapper();
//		String json = "{\"code\":-3,\"desc\":\"json parse error\"}";
//		
//		try {
//			json = jsonMapper.writeValueAsString(result);
//			
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//       
//		ll.info("json : " + json);
//        return json;
//    }
//	
//	private String getMD5(String str, int len) {
//		String MD5 = "";
//		
//		try{
//			
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(str.getBytes());
//			byte byteData[] = md.digest();
//			StringBuffer sb = new StringBuffer();
//		
//			for(int i = 0 ; i < byteData.length ; i++){
//				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
//			}
//			
//			MD5 = sb.toString();
//		
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			MD5 = null;
//		}
//		
//		return MD5.substring(0,len);
//	}
	
//	private String getRename(String str) {
//	
//		return null;
//	}
//	
//	@RequestMapping(value = "/imgFileDelete", method = RequestMethod.POST, headers = { "content-type=application/json" }, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public SimpleResult imgFileDelete(@RequestBody ImgFile imgFile, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		response.setHeader("Cache-Control", "no-cache");
//		
//		SimpleResult result = new SimpleResult();
//		result.code = 0;
//		result.desc = "삭제 완료되었습니다.";
//		
//		try {
//			
//			File f1 = new File(imgFile.file_storage_path + imgFile.file_name);
//			File f2 = new File(imgFile.thumbnail_storage_path + imgFile.thumbnail_name);
//			
//			if(f1.isFile() && f2.isFile()) {
//				f1.delete();
//				f2.delete();
//				
//			} else {
//				result.code = -2;
//				result.desc = "파일 삭제중 문제발생";
//			}
//			
//		} catch (Exception e) {
//			result.code = -1;
//			result.desc = "파일 삭제중 문제발생";
//		}
//		
//		return result;
//	}
	
}
