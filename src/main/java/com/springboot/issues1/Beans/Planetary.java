package com.springboot.issues1.Beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Planetary")
@Table(name = "planet_details")
@ApiModel(value = "Planetary Class", description = "Contains Details of Planetary")
public class Planetary implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "s.n")
	@ApiModelProperty(notes = "Primary Key (Identifier) of Planetary Class")
	private Long id;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column(name = "creation_date")
	@ApiModelProperty(notes = "Creation Date Of The Planetory Class")
	private LocalDateTime creationDate;

	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column(name = "modification_date")
	@ApiModelProperty(notes = "Modification Date Of Planetory Class")
	private LocalDateTime modificationDate;

	@Column(name = "date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(notes = "Date of The Planetary Class")
	private Date date;

	@Column(name = "explanation")
	@Length(max = 10485760)
	@ApiModelProperty(notes = "Explanation Of the Planetary Class")
	private String explanation;

	@Column(name = "hdurl")
	@ApiModelProperty(notes = "HD URL of the Planetary CLass")
	private String hdUrl;

	@Column(name = "media_type")
	@ApiModelProperty(notes = "Media Type of The Planetary Class")
	private String mediaType;

	@Column(name = "service_version")
	@ApiModelProperty(notes = "Service Version of The Planetary Class")
	private String serviceVersion;

	@Column(name = "title")
	@ApiModelProperty(notes = "Title Of the Planetary Class")
	private String title;

	@Column(name = "url")
	@ApiModelProperty(notes = "Url Of the Planetary Class")
	private String url;

	public Planetary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Planetary(Long id, LocalDateTime creationDate, LocalDateTime modificationDate, Date date, String explanation,
			String hdUrl, String mediaType, String serviceVersion, String title, String url) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.date = date;
		this.explanation = explanation;
		this.hdUrl = hdUrl;
		this.mediaType = mediaType;
		this.serviceVersion = serviceVersion;
		this.title = title;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getHdUrl() {
		return hdUrl;
	}

	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((explanation == null) ? 0 : explanation.hashCode());
		result = prime * result + ((hdUrl == null) ? 0 : hdUrl.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mediaType == null) ? 0 : mediaType.hashCode());
		result = prime * result + ((modificationDate == null) ? 0 : modificationDate.hashCode());
		result = prime * result + ((serviceVersion == null) ? 0 : serviceVersion.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planetary other = (Planetary) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (explanation == null) {
			if (other.explanation != null)
				return false;
		} else if (!explanation.equals(other.explanation))
			return false;
		if (hdUrl == null) {
			if (other.hdUrl != null)
				return false;
		} else if (!hdUrl.equals(other.hdUrl))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mediaType == null) {
			if (other.mediaType != null)
				return false;
		} else if (!mediaType.equals(other.mediaType))
			return false;
		if (modificationDate == null) {
			if (other.modificationDate != null)
				return false;
		} else if (!modificationDate.equals(other.modificationDate))
			return false;
		if (serviceVersion == null) {
			if (other.serviceVersion != null)
				return false;
		} else if (!serviceVersion.equals(other.serviceVersion))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Planetary [id=" + id + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate
				+ ", date=" + date + ", explanation=" + explanation + ", hdUrl=" + hdUrl + ", mediaType=" + mediaType
				+ ", serviceVersion=" + serviceVersion + ", title=" + title + ", url=" + url + "]";
	}

}
