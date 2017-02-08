package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.ImgFile;

public class ImgFileVo extends BaseVo {
	
	public String img_seq;
	public String bcn_cd;
	public String img_pys_loc;
	public String img_uri;
	public String img_thmb;
	public String img_thmb_uri;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public ImgFile toModel() {
		
		ImgFile file = new ImgFile();
		
		file.img_seq = this.img_seq;
		file.bcn_cd = this.bcn_cd;
		file.img_pys_loc = this.img_pys_loc;
		file.img_uri = this.img_uri;
		file.img_thmb = this.img_thmb;
		file.img_thmb_uri = this.img_thmb_uri;
		file.reg_dttm = this.reg_dttm;
		file.mod_dttm = this.mod_dttm;
		file.reg_usr = this.reg_usr;
		file.mod_usr = this.mod_usr;
		
		return file;
	}
	
	public ImgFile toModelForView() {
		
		ImgFile file = new ImgFile();
		
		file.img_seq = this.img_seq;
		file.bcn_cd = this.bcn_cd;
		file.img_pys_loc = this.img_pys_loc;
		file.img_uri = this.img_uri;
		file.img_thmb = this.img_thmb;
		file.img_thmb_uri = this.img_thmb_uri;
		
		return file;
	}

}
