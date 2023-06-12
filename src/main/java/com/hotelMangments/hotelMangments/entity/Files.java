package com.hotelMangments.hotelMangments.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "files")
@Data
public class Files {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String fileName;

	private String fileType;


	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Lob
	private byte[] data;

	public Files() {

	}
	public Files(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}
}
